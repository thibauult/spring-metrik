package bzh.tibus29.spring.metrik.impl;

import bzh.tibus29.spring.metrik.services.ComplexBean;
import org.junit.Test;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class DefaultMetrikHandlerTest {

    @Test
    public void testGetObjectProperty() throws Exception {
        final DefaultMetrikHandler handler = new DefaultMetrikHandler("TEST");

        final ComplexBean bean = new ComplexBean();
        bean.setBar(4);
        bean.setFoo("bar");

        assertEquals("4", handler.getObjectProperty(bean, "bar", null));
        assertEquals("bar", handler.getObjectProperty(bean, "foo", null));
        assertEquals("???", handler.getObjectProperty(bean, "_", "???"));
    }

    @Test
    public void testFormatParams() {
        final DefaultMetrikHandler handler = new DefaultMetrikHandler("TEST");

        final ComplexBean bean = new ComplexBean("hello", 2);

        final Map<String, Object> methodParams = new HashMap<>();
        methodParams.put("foo", bean);
        methodParams.put("bar", 3);

        assertEquals("bar=3,foo=" + bean.toString(), handler.formatParams(methodParams, Collections.emptyList()));
        assertEquals("unknown=???", handler.formatParams(methodParams, Collections.singletonList("unknown")));
        assertEquals("foo.unknown=???", handler.formatParams(methodParams, Collections.singletonList("foo.unknown")));
    }
}