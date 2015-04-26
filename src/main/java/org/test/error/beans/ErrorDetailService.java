package org.test.error.beans;

public class ErrorDetailService extends GenericErrorDetail {
    private String entity;
    private String property;

    public ErrorDetailService(String entity, String property, String message) {
        super(message);
        this.entity = entity;
        this.property = property;
    }

    public String getEntity() {
        return entity;
    }

    public void setEntity(String entity) {
        this.entity = entity;
    }

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    @Override
    public String toString() {
        return "ErrorDetailService{" +
                "message='" + super.getMessage() + '\'' +
                ", entity='" + entity + '\'' +
                ", property='" + property + '\'' +
                '}';
    }
}
