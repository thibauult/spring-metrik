package bzh.tibus29.spring.metrik.aop;

import bzh.tibus29.spring.metrik.TraceMode;
import bzh.tibus29.spring.metrik.handler.MetrikHandlerContext;
import bzh.tibus29.spring.metrik.Metrik;
import bzh.tibus29.spring.metrik.handler.MetrikHandler;
import bzh.tibus29.spring.metrik.handler.MetrikWrapper;
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
@SuppressWarnings({"squid:S1181", "squid:S00112"})
public class MetrikAspect {

    /** The Logger */
    private static final Logger log = LoggerFactory.getLogger(MetrikAspect.class);

    private final MetrikHandler handler;

    public MetrikAspect(MetrikHandler handler) {
        Assert.notNull(handler, "Handler is mandatory");
        this.handler = handler;
    }

    @Around("execution(@bzh.tibus29.spring.metrik.Metrik public * *(..)) && @annotation(metrikOnMethod)")
    public Object timedOnMethod(ProceedingJoinPoint pjp, Metrik metrikOnMethod) throws Throwable {
        final Metrik metrikOnClass = AnnotationUtils.findAnnotation(pjp.getTarget().getClass(), Metrik.class);
        final MetrikWrapper wrapper = this.buildMetrikWrapper(metrikOnClass, metrikOnMethod);
        return this.proceed(pjp, wrapper);
    }

    @Around("within(@bzh.tibus29.spring.metrik.Metrik *)")
    public Object timedOnClass(ProceedingJoinPoint pjp) throws Throwable {
        final Metrik metrikOnClass = AnnotationUtils.findAnnotation(pjp.getTarget().getClass(), Metrik.class);
        final Metrik metrikOnMethod = AnnotationUtils.findAnnotation(this.getMethod(pjp), Metrik.class);

        if(metrikOnMethod == null) {
            return this.proceed(pjp, new MetrikWrapper(metrikOnClass));
        }

        return pjp.proceed();
    }

    protected Object proceed(ProceedingJoinPoint pjp, MetrikWrapper metrik) throws Throwable {
        long t0 = current(metrik.getTimeMode());
        Object result = null;
        Throwable exception = null;

        try {
            result = pjp.proceed();
        } catch (Throwable throwable) {
            exception = throwable;
        }

        final long duration = current(metrik.getTimeMode()) - t0;
        final MethodSignature methodSignature = (MethodSignature) pjp.getSignature();

        if(metrik.isDefaultValue()) {
            metrik.setValue(pjp.getTarget().getClass().getSimpleName());
        }

        final MetrikHandlerContext context = new MetrikHandlerContext();
        context.setMetrik(metrik);
        context.setDuration(duration);
        context.setException(exception);
        context.setMethodParams(this.getMethodParams(methodSignature, pjp.getArgs()));
        context.setMethodResult(result);
        context.setMethodName(this.getMethod(pjp).getName());

        try {
            this.handler.handleMetrik(context);
        } catch (Exception ex) {
            log.error("Something went wrong when handling metrik, cause : ", ex);
        }

        if(exception != null) {
            throw exception;
        }

        return result;
    }

    protected MetrikWrapper buildMetrikWrapper(Metrik metrikOnClass, Metrik metrikOnMethod) {

        if(metrikOnClass == null) {
            return new MetrikWrapper(metrikOnMethod);
        }

        final MetrikWrapper wrapper = new MetrikWrapper(metrikOnClass);

        // value
        //
        if(!metrikOnMethod.value().equals("")) {
            wrapper.setValue(metrikOnMethod.value());
        }

        // time mode
        //
        if(metrikOnMethod.timeMode() == Metrik.TimeMode.NULL) {
            if(metrikOnClass.timeMode() != Metrik.TimeMode.NULL) {
                wrapper.setMethod(metrikOnClass.method());
            }
            else {
                wrapper.setTimeMode(Metrik.TimeMode.MILLIS);
            }
        } else {
            wrapper.setTimeMode(metrikOnMethod.timeMode());
        }

        // trace mode
        //
        if(metrikOnMethod.traceMode() != TraceMode.GLOBAL) {
            wrapper.setTraceMode(metrikOnMethod.traceMode());
        }

        // enabled, params and result fields
        //
        wrapper.setEnabled(metrikOnMethod.enabled());
        wrapper.setParams(metrikOnMethod.params());
        wrapper.setResultFields(metrikOnMethod.resultFields());
        wrapper.setTraceResult(metrikOnMethod.traceResult());

        return wrapper;
    }

    protected long current(Metrik.TimeMode timeMode) {
        return timeMode == Metrik.TimeMode.MILLIS ? System.currentTimeMillis() : System.nanoTime();
    }

    protected Map<String, Object> getMethodParams(MethodSignature methodSignature, Object[] args) {

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
