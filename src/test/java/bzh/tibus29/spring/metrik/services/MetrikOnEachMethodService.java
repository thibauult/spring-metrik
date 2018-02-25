package bzh.tibus29.spring.metrik.services;

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

    @Metrik(params = { "complexBean.bar" }, resultFields = { "foo" })
    public ComplexBean processComplexBean(ComplexBean complexBean) {
        complexBean.setFoo(String.valueOf(complexBean.getBar()));
        return complexBean;
    }
}
