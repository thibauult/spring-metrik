# Spring Metrik
[![Build Status](https://travis-ci.org/tibus29/spring-metrik.svg?branch=master)](https://travis-ci.org/tibus29/spring-metrik)
[![codecov](https://codecov.io/gh/tibus29/spring-metrik/branch/master/graph/badge.svg)](https://codecov.io/gh/tibus29/spring-metrik)
[![Maintainability](https://api.codeclimate.com/v1/badges/b734eaa102d962ee179c/maintainability)](https://codeclimate.com/github/tibus29/spring-metrik/maintainability)
[![Dependency Status](https://www.versioneye.com/user/projects/5a91a8d20fb24f05aebb6f8d/badge.svg?style=flat-square)](https://www.versioneye.com/user/projects/5a91a8d20fb24f05aebb6f8d)
[![Quality Gate](https://sonarcloud.io/api/badges/gate?key=io.github.tibus29:spring-metrik)](https://sonarcloud.io/dashboard/index/io.github.tibus29:spring-metrik)

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