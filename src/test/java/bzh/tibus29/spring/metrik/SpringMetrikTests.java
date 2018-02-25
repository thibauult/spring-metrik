package bzh.tibus29.spring.metrik;

import bzh.tibus29.spring.metrik.timed.MetrikOnClassService;
import bzh.tibus29.spring.metrik.timed.MetrikOnClassWithoutModeService;
import bzh.tibus29.spring.metrik.timed.MetrikOnEachMethodService;
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

    @Inject
    private MetrikOnEachMethodService metrikOnEachMethod;

    @Inject
    private MetrikOnClassService metrikOnClass;

    @Inject
    private MetrikOnClassWithoutModeService metrikOnClassWithoutMode;

	@Test
	public void metrikOnEachMethods() {
        this.metrikOnEachMethod.doSomething();
        this.metrikOnEachMethod.sayHello("John");
        this.metrikOnEachMethod.add(1, 3);
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
    public void checkMetrikModeValues() {
        assertEquals(Metrik.Mode.NULL, Metrik.Mode.valueOf("NULL"));
        assertEquals(Metrik.Mode.NANO, Metrik.Mode.valueOf("NANO"));
        assertEquals(Metrik.Mode.MILLIS, Metrik.Mode.valueOf("MILLIS"));
    }

	@SpringBootApplication
	static class TestApplication {}
}
