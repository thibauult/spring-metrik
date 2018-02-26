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
### Basic usage
To monitor a method execution, simply add the `@Metrik` : 
```java
@Service
public class MyService {
    
    @Metrik
    public void doSomething() { log.info("Hello, World !"); }
}

// or globally 

@Metrik
@Service
public class MyService {
    
    public void doSomething() { log.info("Hello, World !"); }
}
```
Output : 
```text
Hello, World !
MyService|doSomething|14|OK|[]|[]
```
### Dealing with parameters and result
By default, `@Metrik` will output all method parameters : 
```java
@Service
public class MyService {
    
    @Metrik
    public int add(int a, int b) { return a + b; }
}
```
Output : 
```text
MyService|add|9|OK|[a=1,b=2]|[3]
```
Of course you can change this behaviour by setting which parameters will be logged : 
```java
@Service
public class MyService {
    
    @Metrik(params = { "a" })
    public int add(int a, int b) { return a + b; }
}
```
Output : 
```text
MyService|add|9|OK|[a=1]|[3]
```
And it also works with result and parameters fields : 
```java
@Service
public class MyService {
    
    class Foo {
        private int bar = 10;
        // getter and setter
    }
    
    @Metrik(params = { "foo.bar" }, resultFields = { "bar" })
    public Foo process(Foo foo) { 
        foo.setBar(foo.getBar() * 2);
        return foo;
    }
}
```
Output :
```text
MyService|process|108|OK|[foo.bar=10]|[bar=20]
```
### Disable @Metrik
`@Metrik` can be disabled on particular methods : 
```java
@Metrik
@Service
public class MyService {
    
    @Metrik(enabled = false)
    public void methodA() {
        log.info("I'm in method A");
    }
    
    public void methodB() {
        log.info("I'm in method B");
    }
}
```
Output :
```text
I'm in method A
I'm in method B
MyService|methodB|12|OK|[]|[]
```