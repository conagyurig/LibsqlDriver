package org.conagyurig.protocol.response;

public class Column {
    private String name;
    private String decltype;

    public Column() {}

    public Column(String name, String decltype) {
        this.name = name;
        this.decltype = decltype;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDecltype() {
        return decltype;
    }

    public void setDecltype(String decltype) {
        this.decltype = decltype;
    }
}
