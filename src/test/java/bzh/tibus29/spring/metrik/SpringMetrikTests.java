package bzh.tibus29.spring.metrik;

import bzh.tibus29.spring.metrik.timed.TimedOnClassService;
import bzh.tibus29.spring.metrik.timed.TimedOnClassWithoutModeService;
import bzh.tibus29.spring.metrik.timed.TimedOnEachMethodService;
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
    private TimedOnEachMethodService timedOnEachMethodService;

    @Inject
    private TimedOnClassService timedOnClassService;

    @Inject
    private TimedOnClassWithoutModeService timedOnClassWithoutModeService;

	@Test
	public void timedOnEachMethods() {
        this.timedOnEachMethodService.doSomething();
        this.timedOnEachMethodService.sayHello("Thibault");
        this.timedOnEachMethodService.add(1, 3);
	}

    @Test
    public void timedOnClass() {
        this.timedOnClassService.doSomething();
        this.timedOnClassService.sayHello("Thibault");
        this.timedOnClassService.add(1, 3);
    }

    @Test
    public void timedOnClassWithoutMode() {
        this.timedOnClassWithoutModeService.doSomething();
    }

    @Test(expected = RuntimeException.class)
    public void timedMethodThatFailed() {
        this.timedOnEachMethodService.failingMethod();
    }

    @Test
    public void checkTimedModeValues() {
        assertEquals(Timed.Mode.NULL, Timed.Mode.valueOf("NULL"));
        assertEquals(Timed.Mode.NANO, Timed.Mode.valueOf("NANO"));
        assertEquals(Timed.Mode.MILLIS, Timed.Mode.valueOf("MILLIS"));
    }

	@SpringBootApplication
	static class TestApplication {}
}
