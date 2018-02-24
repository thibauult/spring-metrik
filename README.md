# Spring Metrik

Simple Spring library to monitor your services execution. 

## Usage

To monitor an execution, simply add the `@Timed` annotation on a service method : 

```java
@Service
public class MyService {
    
    private static final Logger log = LoggerFactory.getLogger(MyService.class);
    
    @Timed
    public void doSomething() {
        log.info("Hello, World !");
    }
}
```
or
```java
@Timed
@Service
public class MyService {
    
    private static final Logger log = LoggerFactory.getLogger(MyService.class);
    
    public void doSomething() {
        log.info("Hello, World !");
    }
}
```
Output in your logs : 
```text
Hello, World !
MyService|doSomething|14|OK|[]|[]
```