package org.test.rest.beans;

import org.test.model.Category;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import static org.test.utils.ApplicationStrings.CATEGORY;

@XmlRootElement(name=CATEGORY)
@XmlAccessorType(XmlAccessType.FIELD)
public class CategoryBean {


    public String uri;
    public Long id;
    public String name;
    public Boolean referenced;
    public RecordBean record = new RecordBean();

    public CategoryBean() {}

    public CategoryBean(String uri, Category category, Boolean referenced) {
        this.uri = uri;
        this.id = category.getId();
        this.name = category.getName();
        this.referenced = referenced;
        this.record.created = category.getRecord().getCreated();
        this.record.lastUpdate = category.getRecord().getLastUpdate();
    }
}
