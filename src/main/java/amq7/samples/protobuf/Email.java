package amq7.samples.protobuf;

import java.util.Date;

public class Email {

    private String to;
    private String body;
    private String cc;
    private String cco;
    private String subject;
    private Date dateTime;

    public Email() {
    }

    public String getCc() {
        return cc;
    }

    public void setCc(String cc) {
        this.cc = cc;
    }

    public String getCco() {
        return cco;
    }

    public void setCco(String cco) {
        this.cco = cco;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    public Email(String to, String body) {
        this.to = to;
        this.body = body;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    @Override
    public String toString() {
        return String.format("Email{subject=%s to=%s, body=%s}", getSubject(), getTo(), getBody());
    }

}
