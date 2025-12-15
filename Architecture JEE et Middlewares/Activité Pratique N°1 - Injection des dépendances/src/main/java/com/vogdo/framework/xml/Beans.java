package com.vogdo.framework.xml;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "beans")
@XmlAccessorType(XmlAccessType.FIELD)
public class Beans {
    @XmlElement(name = "bean")
    private List<Bean> beans = new ArrayList<>();

    public List<Bean> getBeans() { return beans; }
    public void setBeans(List<Bean> beans) { this.beans = beans; }
}