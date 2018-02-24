package bzh.tibus29.spring.metrik;

import bzh.tibus29.spring.metrik.timed.FailingTimedMetrikHandler;
import bzh.tibus29.spring.metrik.timed.TimedOnEachMethodService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import javax.inject.Inject;

@SpringBootTest
@RunWith(SpringRunner.class)
public class SpringMetrikFailingTests {

    @Inject
    private TimedOnEachMethodService timedOnEachMethodService;

	@Test
	public void timedOnEachMethods() {
        this.timedOnEachMethodService.sayHello("tibus29");
	}

	@SpringBootApplication
	static class TestConfiguration {

	    @Bean
	    public TimedMetrikHandler timedMetrikHandler() {
	        return new FailingTimedMetrikHandler();
        }
    }
}
