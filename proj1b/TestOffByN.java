import org.junit.Test;
import static org.junit.Assert.*;


public class TestOffByN {

    @Test
    public void TestEqualChars(){
        OffByN offBy5 = new OffByN(5);
        assertTrue(offBy5.equalChars('a', 'f'));
        assertFalse(offBy5.equalChars('f', 'h'));
    }
}
