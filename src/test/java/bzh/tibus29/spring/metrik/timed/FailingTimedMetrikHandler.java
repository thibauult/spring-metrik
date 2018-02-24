package bzh.tibus29.spring.metrik.timed;

import bzh.tibus29.spring.metrik.ExecutionContext;
import bzh.tibus29.spring.metrik.TimedMetrikHandler;

public class FailingTimedMetrikHandler implements TimedMetrikHandler {

    @Override
    public void handleMetric(ExecutionContext context) {
        throw new RuntimeException("Something went wrong...");
    }
}
