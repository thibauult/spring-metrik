package bzh.tibus29.spring.metrik.services;

public class ComplexBean {

    private String foo;
    private int bar;

    public ComplexBean() {
    }

    public ComplexBean(String foo, int bar) {
        this.foo = foo;
        this.bar = bar;
    }

    public String getFoo() {
        return foo;
    }

    public void setFoo(String foo) {
        this.foo = foo;
    }

    public int getBar() {
        return bar;
    }

    public void setBar(int bar) {
        this.bar = bar;
    }

    @Override
    public String toString() {
        return "ComplexBean{" +
                "foo='" + foo + '\'' +
                ", bar=" + bar +
                '}';
    }
}
