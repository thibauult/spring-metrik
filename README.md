# Spring Metrik
[![Build Status](https://travis-ci.org/tibus29/spring-metrik.svg?branch=master)](https://travis-ci.org/tibus29/spring-metrik)
[![CodeCov](https://codecov.io/gh/tibus29/spring-metrik/branch/master/graph/badge.svg)](https://codecov.io/gh/tibus29/spring-metrik)
[![Maintainability](https://api.codeclimate.com/v1/badges/b734eaa102d962ee179c/maintainability)](https://codeclimate.com/github/tibus29/spring-metrik/maintainability)
[![Dependency Status](https://www.versioneye.com/user/projects/5a91a8d20fb24f05aebb6f8d/badge.svg?style=flat-square)](https://www.versioneye.com/user/projects/5a91a8d20fb24f05aebb6f8d)
[![Quality Gate](https://sonarcloud.io/api/badges/gate?key=io.github.tibus29:spring-metrik)](https://sonarcloud.io/dashboard/index/io.github.tibus29:spring-metrik)

`Metrik` is lightweight Spring library to monitor your services execution. 

## Installation
`@Metrik` release is published on the [Maven Central](http://search.maven.org/#search%7Cga%7C1%7Cg%3Aio.github.tibus29%20a%3Aspring-metrik), 
so you can install it from your preferred dependency management tool : `Gradle` or `Maven`.

### Maven
Include the following lines to your `pom.xml` dependencies : 
```xml
<dependencies>
    <dependency>
      <groupId>io.github.tibus29</groupId>
      <artifactId>spring-metrik</artifactId>
      <version>1.1.0</version>
    </dependency>
</dependencies>
```

### Gradle
Include the following line to your `build.gradle` dependencies : 
```groovy
compile 'io.github.tibus29:spring-metrik:1.1.0'
```

## Usage
Using the library is very simple, you simply have to annotate the bean you want to monitor with`@Metrik` :
```java
@Service
@Metrik(value = "MY_SERVICE", traceMode = TraceMode.AUTO) // custom metrik group, TraceMode.AUTO will output all params and result
public class MyService {
    
    public String sayHelloTo(String name) { 
        return "Hello, " + name + " !"; 
    }
    
    @Metrik(params = { "username" }) // prevent for logging clear password into logs !
    public boolean authenticate(String username, String password) {
        return true;
    }
    
    @Metrik(enabled = false)
    public void doSomething() {
        log.info("I do something...");   
    }
}

@RestController
@RequestMapping("/")
public class MyController {
    
    @Inject
    MyService myService;
    
    class MyBean {
        private String action = "Please clone me !";
    }
    
    @GetMapping
    @Metrik(resultFields = { "toto" }) // the annotation can be placed on a single method
    public MyBean index() {
        log.info(this.myService.sayHelloTo("Foo"));
        log.info("Authentication : {} !", this.myService.authenticate("foo@bar.com", "password") ? "success" : "failure");
        this.myService.doSomething();
        return new MyBean();
    }
}
```
Output : 
```text
MY_SERVICE|sayHelloTo|21|OK|[name='Foo']|['Hello, Foo !']
Hello, Foo !
MY_SERVICE|authenticate|125|OK|[username='foo@bar.com']|[true]
Authentication : success !
I do something...
MyController|index|163|OK|[]|[action='Please clone me !']
```