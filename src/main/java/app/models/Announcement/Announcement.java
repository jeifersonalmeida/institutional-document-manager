package app.models.Announcement;

import app.models.Document.Document;

import javax.persistence.Entity;

@Entity
public class Announcement extends Document {

  private String description;

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

}
