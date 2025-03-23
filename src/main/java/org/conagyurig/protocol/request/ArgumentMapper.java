package org.conagyurig.protocol.request;

import java.util.Base64;

public class ArgumentMapper {
    public static Argument convertJavaObjToArgument(Object obj) {
        if (obj == null) return new Argument(ArgumentType.NULL, null);
        else if (obj instanceof String s) return new Argument(ArgumentType.TEXT, s);
        else if (obj instanceof Integer i) return new Argument(ArgumentType.INTEGER, i.toString());
        else if (obj instanceof Long l) return new Argument(ArgumentType.INTEGER, l.toString());
        else if (obj instanceof Float f) return new Argument(ArgumentType.FLOAT, f.toString());
        else if (obj instanceof Double d) return new Argument(ArgumentType.FLOAT, d.toString());
        else if (obj instanceof byte[] b) {
            String encoded = Base64.getEncoder().encodeToString(b);
            return new Argument(ArgumentType.BLOB, encoded);
        }
        throw new IllegalArgumentException("Unsupported argument type: " + obj.getClass().getName());
    }
}
