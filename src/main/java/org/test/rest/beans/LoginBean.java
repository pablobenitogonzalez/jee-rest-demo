package org.test.rest.beans;

import org.test.model.Login;
import org.test.model.Role;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import static org.test.utils.ApplicationStrings.LOGIN;

@XmlRootElement(name=LOGIN)
@XmlAccessorType(XmlAccessType.FIELD)
public class LoginBean {

    public Long id;
    public String email;
    public String password;
    public Role role;
    public RecordBean record = new RecordBean();

    public LoginBean() {}

    public LoginBean(Login login) {
        this.id = login.getId();
        this.email = login.getEmail();
        this.password = login.getPassword();
        this.role = login.getRole();
        this.record.created = login.getRecord().getCreated();
        this.record.lastUpdate = login.getRecord().getLastUpdate();
    }
}