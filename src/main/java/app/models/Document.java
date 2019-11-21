package app.models;

import javax.persistence.*;
import java.util.Date;

@MappedSuperclass
public abstract class Document {

  @Id
  private String number;
  private Date publicationDate;
  private String subject;
  @Enumerated(EnumType.STRING)
  private Status status;
  private String filePath;

  public String getNumber() {
      return number;
  }

  public void setNumber(String number) {
      this.number = number;
  }

  public Date getPublicationDate() {
      return publicationDate;
  }

  public void setPublicationDate(Date publicationDate) {
      this.publicationDate = publicationDate;
  }

  public String getSubject() {
      return subject;
  }

  public void setSubject(String subject) {
      this.subject = subject;
  }

  public Status getStatus() {
      return status;
  }

  public void setStatus(Status status) {
      this.status = status;
  }

  public String getFilePath() {
      return filePath;
  }

  public void setFilePath(String filePath) {
      this.filePath = filePath;
  }

}
