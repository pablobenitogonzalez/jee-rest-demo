package org.test.error.beans;

public class ErrorDetailAuth extends GenericErrorDetail {
    private String email;
    private String password;

    public ErrorDetailAuth(String message, String email, String password) {
        super(message);
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
        this.password = password;
    }

    @Override
    public String toString() {
        return "ErrorDetailAuth{" +
                "message='" + super.getMessage() + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
