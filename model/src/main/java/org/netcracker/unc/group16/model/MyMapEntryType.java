package org.netcracker.unc.group16.model;

import javax.xml.bind.annotation.XmlAttribute;

/**
 * Created by Ivan on 25.11.2015.
 */
public class MyMapEntryType {

    private Integer key;
    private Task value;

    @XmlAttribute
    public Integer getKey() {
        return key;
    }

    public void setKey(Integer key) {
        this.key = key;
    }

 //   @XmlAttribute
    public Task getValue() {
        return value;
    }

    public void setValue(Task value) {
        this.value = value;
    }
}
