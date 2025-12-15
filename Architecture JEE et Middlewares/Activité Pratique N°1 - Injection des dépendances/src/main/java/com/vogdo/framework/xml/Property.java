package com.vogdo.framework.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;

@XmlAccessorType(XmlAccessType.FIELD)
public class Property {
    @XmlAttribute
    private String name;
    @XmlAttribute
    private String ref;

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getRef() { return ref; }
    public void setRef(String ref) { this.ref = ref; }
}