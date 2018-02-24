package bzh.tibus29.spring.metrik.timed;

import bzh.tibus29.spring.metrik.Timed;
import org.springframework.stereotype.Service;

@Timed
@Service
public class TimedOnClassWithoutModeService {

    @Timed
    public void doSomething() {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
