package com.vogdo.framework.xml;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
public class Bean {
    @XmlAttribute
    private String id;
    @XmlAttribute(name = "class")
    private String className;
    @XmlElement(name = "property")
    private List<Property> properties = new ArrayList<>();

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getClassName() { return className; }
    public void setClassName(String className) { this.className = className; }
    public List<Property> getProperties() { return properties; }
    public void setProperties(List<Property> properties) { this.properties = properties; }
}