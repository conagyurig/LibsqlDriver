package org.conagyurig.protocol.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum ArgumentType {
    @JsonProperty("null") NULL,
    @JsonProperty("integer") INTEGER,
    @JsonProperty("float") FLOAT,
    @JsonProperty("text") TEXT,
    @JsonProperty("blob") BLOB
}
