package org.test.rest.beans;

import org.test.model.Subcategory;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import static org.test.utils.ApplicationStrings.SUBCATEGORY;

@XmlRootElement(name=SUBCATEGORY)
@XmlAccessorType(XmlAccessType.FIELD)
public class SubcategoryBean {


    public String uri;
    public Long id;
    public String name;
    public Long category;
    public RecordBean record = new RecordBean();

    public SubcategoryBean() {}

    public SubcategoryBean(String uri, Subcategory subcategory, Long category) {
        this.uri = uri;
        this.id = subcategory.getId();
        this.name = subcategory.getName();
        this.category = category;
        this.record.created = subcategory.getRecord().getCreated();
        this.record.lastUpdate = subcategory.getRecord().getLastUpdate();
    }
}
