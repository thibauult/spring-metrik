package bzh.tibus29.spring.metrik.services;

import bzh.tibus29.spring.metrik.Metrik;
import bzh.tibus29.spring.metrik.TraceMode;
import org.springframework.stereotype.Service;

@Service
@Metrik(timeMode = Metrik.TimeMode.MILLIS, traceMode = TraceMode.AUTO)
public class MetrikOnClassService extends MetrikTestBusiness {

    @Override
    public void doSomething() {
        super.doSomething();
    }

    @Override
    @Metrik(enabled = false)
    public String sayHello(String to) {
        return super.sayHello(to);
    }

    @Override
    @Metrik(value = "TITI", timeMode = Metrik.TimeMode.NANO, traceMode = TraceMode.AUTO)
    public int add(int a, int b) {
        return super.add(a, b);
    }

    @Override
    @Metrik
    public void failingMethod() {
        super.failingMethod();
    }
}
