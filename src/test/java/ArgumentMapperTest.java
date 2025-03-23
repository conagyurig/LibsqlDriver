import org.conagyurig.protocol.request.Argument;
import org.conagyurig.protocol.request.ArgumentMapper;
import org.conagyurig.protocol.request.ArgumentType;
import org.junit.jupiter.api.Test;

import java.util.Base64;

import static org.junit.jupiter.api.Assertions.*;

public class ArgumentMapperTest {


    @Test
    void testConvertStringToTextArgument() {
        Argument arg = ArgumentMapper.convertJavaObjToArgument("hello");

        assertEquals(ArgumentType.TEXT, arg.type);
        assertEquals("hello", arg.value);
        assertNull(arg.base64);
    }

    @Test
    void testConvertIntegerToIntegerArgument() {
        Argument arg = ArgumentMapper.convertJavaObjToArgument(42);

        assertEquals(ArgumentType.INTEGER, arg.type);
        assertEquals("42", arg.value);
        assertNull(arg.base64);
    }

    @Test
    void testConvertBlobToBase64() {
        byte[] blob = new byte[]{1, 2, 3};
        Argument arg = ArgumentMapper.convertJavaObjToArgument(blob);

        assertEquals(ArgumentType.BLOB, arg.type);
        assertNull(arg.value);
        assertEquals(Base64.getEncoder().encodeToString(blob), arg.base64);
    }

    @Test
    void testUnsupportedTypeThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            ArgumentMapper.convertJavaObjToArgument(new Object());
        });
    }
}
