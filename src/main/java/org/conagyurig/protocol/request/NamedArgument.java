package org.conagyurig.protocol.request;

public class NamedArgument {
    public String name;
    public Argument value;

    public NamedArgument(String name, Argument value) {
        this.name = name;
        this.value = value;
    }
}
