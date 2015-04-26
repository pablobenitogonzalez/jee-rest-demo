package org.test.rest.beans;

import org.test.model.Login;
import org.test.model.Role;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import static org.test.utils.ApplicationStrings.AUTH_ACCESS;

@XmlRootElement(name=AUTH_ACCESS)
@XmlAccessorType(XmlAccessType.FIELD)
public class AuthAccessBean {

    public Long id;
    public String email;
    public Role role;
    public RecordBean record = new RecordBean();

    public AuthAccessBean() {}

    public AuthAccessBean(Login login) {
        this.id = login.getId();
        this.email = login.getEmail();
        this.role = login.getRole();
        this.record.created = login.getRecord().getCreated();
        this.record.lastUpdate = login.getRecord().getLastUpdate();
    }
}