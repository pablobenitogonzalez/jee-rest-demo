package org.test.rest.beans;

import javax.xml.bind.annotation.*;
import java.util.Date;

import static org.test.utils.ApplicationStrings.LAST_UPDATE;
import static org.test.utils.ApplicationStrings.RECORD;

@XmlRootElement(name=RECORD)
@XmlAccessorType(XmlAccessType.FIELD)
public class RecordBean {

    @XmlSchemaType(name = "dateTime")
    public Date created = new Date();

    @XmlSchemaType(name = "dateTime")
    @XmlElement(name=LAST_UPDATE)
    public Date lastUpdate = new Date();

    public RecordBean() {}

}
