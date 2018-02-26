package bzh.tibus29.spring.metrik;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TraceModeTest {

    @Test
    public void testValueOf() {
        assertEquals(TraceMode.GLOBAL, TraceMode.valueOf("GLOBAL"));
        assertEquals(TraceMode.AUTO, TraceMode.valueOf("AUTO"));
        assertEquals(TraceMode.MANUAL, TraceMode.valueOf("MANUAL"));
        assertEquals("*", TraceMode.ALL);
    }
}