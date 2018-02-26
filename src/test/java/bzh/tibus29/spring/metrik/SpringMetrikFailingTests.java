package bzh.tibus29.spring.metrik;

import bzh.tibus29.spring.metrik.handler.MetrikHandler;
import bzh.tibus29.spring.metrik.services.MetrikOnEachMethodService;
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
    private MetrikOnEachMethodService metrikOnEachMethodService;

	@Test
	public void timedOnEachMethods() {
        this.metrikOnEachMethodService.sayHello("tibus29");
	}

	@SpringBootApplication
	static class TestApplication {

	    @Bean
	    public MetrikHandler failingMetrikHandler() {
	        return context -> { throw new RuntimeException("Something went wrong..."); };
        }
    }
}
