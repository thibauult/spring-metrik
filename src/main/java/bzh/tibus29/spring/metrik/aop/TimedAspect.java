package bzh.tibus29.spring.metrik.aop;

import bzh.tibus29.spring.metrik.ExecutionContext;
import bzh.tibus29.spring.metrik.Timed;
import bzh.tibus29.spring.metrik.TimedMetrikHandler;
import bzh.tibus29.spring.metrik.TimedWrapper;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.Assert;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

@Aspect
public class TimedAspect {

    /** The Logger */
    private static final Logger log = LoggerFactory.getLogger(TimedAspect.class);

    private final TimedMetrikHandler handler;

    public TimedAspect(TimedMetrikHandler handler) {
        Assert.notNull(handler, "Handler is mandatory");
        this.handler = handler;
    }

    @Around("execution(@bzh.tibus29.spring.metrik.Timed public * *(..)) && @annotation(timedOnMethod)")
    public Object timedOnMethod(ProceedingJoinPoint pjp, Timed timedOnMethod) throws Throwable {
        final Timed timedOnClass = AnnotationUtils.findAnnotation(pjp.getTarget().getClass(), Timed.class);
        final TimedWrapper wrapper = this.buildTimedWrapper(timedOnClass, timedOnMethod);
        return this.proceed(pjp, wrapper);
    }

    @Around("within(@bzh.tibus29.spring.metrik.Timed *)")
    public Object timedOnClass(ProceedingJoinPoint pjp) throws Throwable {
        final Timed timedOnClass = AnnotationUtils.findAnnotation(pjp.getTarget().getClass(), Timed.class);
        final Timed timedOnMethod = AnnotationUtils.findAnnotation(this.getMethod(pjp), Timed.class);

        if(timedOnMethod == null) {
            return this.proceed(pjp, new TimedWrapper(timedOnClass));
        }

        return pjp.proceed();
    }

    protected Object proceed(ProceedingJoinPoint pjp, TimedWrapper timed) throws Throwable {
        long t0 = current(timed.getMode());
        Object result = null;
        Throwable exception = null;

        try {
            result = pjp.proceed();
        } catch (Throwable throwable) {
            exception = throwable;
        }

        final long duration = current(timed.getMode()) - t0;
        final MethodSignature methodSignature = (MethodSignature) pjp.getSignature();

        if(timed.isDefaultValue()) {
            timed.setValue(pjp.getTarget().getClass().getSimpleName());
        }

        final ExecutionContext context = new ExecutionContext();
        context.setDuration(duration);
        context.setException(exception);
        context.setParams(this.params(methodSignature, pjp.getArgs()));
        context.setResult(result);
        context.setAnnotation(timed);
        context.setMethodName(this.getMethod(pjp).getName());

        try {
            this.handler.handleMetric(context);
        } catch (Exception ex) {
            log.error("Something went wrong when handling timed metric, cause : ", ex);
        }

        if(exception != null) {
            throw exception;
        }

        return result;
    }

    protected TimedWrapper buildTimedWrapper(Timed timedOnClass, Timed timedOnMethod) {

        if(timedOnClass == null) {
            return new TimedWrapper(timedOnMethod);
        }

        final TimedWrapper wrapper = new TimedWrapper(timedOnClass);

        if(!timedOnMethod.value().equals("_")) {
            wrapper.setValue(timedOnMethod.value());
        }

        if(timedOnMethod.mode() == Timed.Mode.NULL) {
            if(timedOnClass.mode() != Timed.Mode.NULL) {
                wrapper.setMethod(timedOnClass.method());
            }
            else {
                wrapper.setMode(Timed.Mode.MILLIS);
            }
        } else {
            wrapper.setMode(timedOnMethod.mode());
        }

        wrapper.setEnabled(timedOnMethod.enabled());

        return wrapper;
    }

    protected long current(Timed.Mode mode) {
        return mode == Timed.Mode.MILLIS ? System.currentTimeMillis() : System.nanoTime();
    }

    protected Map<String, Object> params(MethodSignature methodSignature, Object[] args) {

        final Map<String, Object> params = new HashMap<>();

        for (int i = 0; i < args.length; i++) {
            params.put(methodSignature.getParameterNames()[i], args[i]);
        }

        return params;
    }

    protected Method getMethod(ProceedingJoinPoint pjp) {
        return ((MethodSignature) pjp.getSignature()).getMethod();
    }
}
