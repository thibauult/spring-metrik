package bzh.tibus29.spring.metrik.timed;

import bzh.tibus29.spring.metrik.Timed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class TimedOnEachMethodService {

    private static final Logger log = LoggerFactory.getLogger(TimedOnEachMethodService.class);

    @Timed(method = "toto")
    public void doSomething() {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Timed("TEST")
    public void sayHello(String to) {
        log.info("Hello {} !", to);
    }

    @Timed(mode = Timed.Mode.NANO)
    public int add(int a, int b) {
        return a + b;
    }

    @Timed
    public void failingMethod() {
        throw new RuntimeException();
    }
}
