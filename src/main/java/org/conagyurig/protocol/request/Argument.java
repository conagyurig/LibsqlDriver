package org.conagyurig.protocol.request;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Argument {
    public ArgumentType type;
    public String value;
    public String base64;
    public Argument(ArgumentType type, String value) {
        this.type = type;
        if (type.equals(ArgumentType.BLOB)) {
            this.base64 = value;
        } else {
            this.value = value;
        }
    }
}
