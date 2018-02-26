package bzh.tibus29.spring.metrik;

import bzh.tibus29.spring.metrik.services.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.inject.Inject;

import static org.junit.Assert.assertEquals;

@SpringBootTest
@RunWith(SpringRunner.class)
public class SpringMetrikTests {

    @SpringBootApplication
    static class TestApplication {}

    @Inject
    private MetrikOnEachMethodService metrikOnEachMethod;

    @Inject
    private MetrikOnClassService metrikOnClass;

    @Inject
    private MetrikOnClassWithoutModeService metrikOnClassWithoutMode;

    @Inject
    private MetrikWithManualModeService metrikWithManualModeService;

	@Test
	public void metrikOnEachMethods() {
        this.metrikOnEachMethod.doSomething();
        this.metrikOnEachMethod.sayHello("John");
        this.metrikOnEachMethod.add(1, 3);

        final ComplexBean bean = new ComplexBean();
        bean.setFoo("hello");
        bean.setBar(123);
        this.metrikOnEachMethod.processComplexBean(bean);
	}

    @Test
    public void metrikOnClass() {
        this.metrikOnClass.doSomething();
        this.metrikOnClass.sayHello("Paul");
        this.metrikOnClass.add(1, 3);
    }

    @Test(expected = RuntimeException.class)
    public void metrikOnClassWithFailingMethod() {
        this.metrikOnClass.failingMethod();
    }

    @Test
    public void metrikOnClassWithoutMode() {
        this.metrikOnClassWithoutMode.doSomething();
    }

    @Test(expected = RuntimeException.class)
    public void metrikMethodThatFailed() {
        this.metrikOnEachMethod.failingMethod();
    }

    @Test
    public void metrikWithManualModeService() {
	    this.metrikWithManualModeService.sayHelloTo("Foo");
    }

    @Test
    public void checkMetrikModeValues() {
        assertEquals(Metrik.TimeMode.NULL, Metrik.TimeMode.valueOf("NULL"));
        assertEquals(Metrik.TimeMode.NANO, Metrik.TimeMode.valueOf("NANO"));
        assertEquals(Metrik.TimeMode.MILLIS, Metrik.TimeMode.valueOf("MILLIS"));
    }
}
