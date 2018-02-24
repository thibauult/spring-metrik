package bzh.tibus29.spring.metrik.timed;

import bzh.tibus29.spring.metrik.Timed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Timed(mode = Timed.Mode.MILLIS)
@Service
public class TimedOnClassService {

    private static final Logger log = LoggerFactory.getLogger(TimedOnEachMethodService.class);

    public void doSomething() {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Timed(enabled = false)
    public void sayHello(String to) {
        log.info("Hello {} !", to);
    }

    @Timed(value = "TITI", mode = Timed.Mode.NANO)
    public int add(int a, int b) {
        return a + b;
    }
}
