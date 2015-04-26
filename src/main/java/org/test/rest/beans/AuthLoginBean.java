package org.test.rest.beans;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import static org.test.utils.ApplicationStrings.AUTH_LOGIN;

@XmlRootElement(name=AUTH_LOGIN)
@XmlAccessorType(XmlAccessType.FIELD)
public class AuthLoginBean {

    public String email;
    public String password;

    public AuthLoginBean() {}

    public AuthLoginBean(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password =password;
    }
}
