package bzh.tibus29.spring.metrik.timed;

import bzh.tibus29.spring.metrik.Metrik;
import org.springframework.stereotype.Service;

@Service
@Metrik(mode = Metrik.Mode.MILLIS)
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
    @Metrik(value = "TITI", mode = Metrik.Mode.NANO)
    public int add(int a, int b) {
        return super.add(a, b);
    }

    @Override
    @Metrik
    public void failingMethod() {
        super.failingMethod();
    }
}
