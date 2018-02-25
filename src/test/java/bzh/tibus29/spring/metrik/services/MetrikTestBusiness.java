package bzh.tibus29.spring.metrik.services;

import java.util.concurrent.Callable;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.awaitility.Awaitility.await;

public class MetrikTestBusiness {

    void doSomething() {
        await().atMost(5, SECONDS).until(blaBlaBla());
    }

    private Callable<Boolean> blaBlaBla() {
        return () -> true;
    }

    String sayHello(String to) {
        return "Hello, " + to + " !";
    }

    int add(int a, int b) {
        return a + b;
    }

    public void failingMethod() {
        throw new RuntimeException();
    }
}
