package bzh.tibus29.spring.metrik.handler;

import bzh.tibus29.spring.metrik.TraceMode;
import bzh.tibus29.spring.metrik.config.SpringMetrikProperties;
import bzh.tibus29.spring.metrik.services.ComplexBean;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.Map;

import static bzh.tibus29.spring.metrik.handler.DefaultMetrikHandler.STATUS_KO;
import static bzh.tibus29.spring.metrik.handler.DefaultMetrikHandler.STATUS_OK;
import static java.util.Collections.*;
import static org.junit.Assert.assertEquals;

@SpringBootTest
@RunWith(SpringRunner.class)
public class DefaultMetrikHandlerTest {

    @SpringBootApplication
    static class TestApplication {}

    @Inject
    private SpringMetrikProperties properties;

    private DefaultMetrikHandler handler;

    @Before
    public void before() {
        this.handler = new DefaultMetrikHandler(this.properties);
    }

    @Test
    public void testGetObjectProperty() throws Exception {
        final ComplexBean bean = new ComplexBean("bar", 4);

        assertEquals("4", handler.getObjectProperty(bean, "bar", null));
        assertEquals("'bar'", handler.getObjectProperty(bean, "foo", null));
        assertEquals("???", handler.getObjectProperty(bean, "_", "???"));
    }

    @Test
    public void testFormatResult() {
        final ComplexBean bean = new ComplexBean("hello", 2);

        assertEquals("", handler.formatResult(TraceMode.MANUAL, false, bean, emptyList()));
        assertEquals(bean.toString(), handler.formatResult(TraceMode.MANUAL, true, bean, emptyList()));
        assertEquals(bean.toString(), handler.formatResult(TraceMode.AUTO, false, bean, emptyList()));

        assertEquals("foo='hello'", handler.formatResult(TraceMode.MANUAL, false, bean, singletonList("foo")));
        assertEquals("foo='hello'", handler.formatResult(TraceMode.AUTO, false, bean, singletonList("foo")));

        assertEquals("unknown=???", handler.formatResult(TraceMode.MANUAL, false, bean, singletonList("unknown")));
        assertEquals("unknown=???", handler.formatResult(TraceMode.AUTO, false, bean, singletonList("unknown")));
    }

    @Test
    public void testFormatStatus() {
        assertEquals(STATUS_OK, handler.formatStatus(null));
        assertEquals(STATUS_KO, handler.formatStatus(new RuntimeException()));
    }

    @Test
    public void testFormatParams() {
        final ComplexBean bean = new ComplexBean("hello", 2);

        final Map<String, Object> methodParams = new HashMap<>();
        methodParams.put("foo", bean);
        methodParams.put("bar", 3);

        assertEquals("", handler.formatParams(TraceMode.MANUAL, methodParams, emptyList()));
        assertEquals("bar=3,foo=" + bean.toString(), handler.formatParams(TraceMode.AUTO, methodParams, emptyList()));

        assertEquals("bar=3,foo=" + bean.toString(), handler.formatParams(TraceMode.MANUAL, methodParams, singletonList("*")));
        assertEquals("bar=3,foo=" + bean.toString(), handler.formatParams(TraceMode.AUTO, methodParams, singletonList("*")));

        assertEquals("unknown=???", handler.formatParams(TraceMode.MANUAL, methodParams, singletonList("unknown")));
        assertEquals("unknown=???", handler.formatParams(TraceMode.AUTO, methodParams, singletonList("unknown")));

        assertEquals("foo.unknown=???", handler.formatParams(TraceMode.MANUAL, methodParams, singletonList("foo.unknown")));
        assertEquals("foo.unknown=???", handler.formatParams(TraceMode.AUTO, methodParams, singletonList("foo.unknown")));
    }
}