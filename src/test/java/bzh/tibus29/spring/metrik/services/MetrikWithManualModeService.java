package bzh.tibus29.spring.metrik.services;

import bzh.tibus29.spring.metrik.Metrik;
import bzh.tibus29.spring.metrik.TraceMode;
import org.springframework.stereotype.Service;

@Service
@Metrik(traceMode = TraceMode.MANUAL)
public class MetrikWithManualModeService extends MetrikTestBusiness {

    @Metrik(traceResult = true, params = TraceMode.ALL)
    public String sayHelloTo(String name) {
        return this.sayHello(name);
    }
}
