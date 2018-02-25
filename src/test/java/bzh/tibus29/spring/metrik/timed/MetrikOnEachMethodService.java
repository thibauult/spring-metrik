package bzh.tibus29.spring.metrik.timed;

import bzh.tibus29.spring.metrik.Metrik;
import org.springframework.stereotype.Service;

@Service
public class MetrikOnEachMethodService extends MetrikTestBusiness {

    @Metrik(method = "toto")
    public void doSomething() {
        super.doSomething();
    }

    @Metrik("TEST")
    public String sayHello(String to) {
        return super.sayHello(to);
    }

    @Metrik(mode = Metrik.Mode.NANO)
    public int add(int a, int b) {
        return super.add(a, b);
    }

    @Metrik
    public void failingMethod() {
        super.failingMethod();
    }
}
