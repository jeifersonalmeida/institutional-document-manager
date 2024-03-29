package app.models.Document;

import app.models.utils.DateTransformer;

import javax.persistence.*;
import java.text.ParseException;
import java.util.Date;

@MappedSuperclass
public abstract class Document {

  @Id
  @GeneratedValue
  private long id;
  private String number;
  private String publicationDate;
  private String subject;
  @Enumerated(EnumType.STRING)
  private Status status;
  private String filePath;

  public long getId() {
    return id;
  }

  public String getNumber() {
      return number;
  }

  public void setNumber(String number) {
      this.number = number;
  }

  public String getPublicationDate() {
      return publicationDate;
  }

  public void setPublicationDate(String publicationDate) {
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

  public String getPublicationDateFormatted() {
      return DateTransformer.StringFormatter(this.publicationDate);
  }

}
