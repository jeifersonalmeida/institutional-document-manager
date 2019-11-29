package app.models.Ordinance;

import app.models.Document.Document;
import app.models.PublicServant.PublicServant;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

@Entity
public class Ordinance extends Document {
  private double workload;
  private Date startingDate;
  private Date finishingDate;

  @Enumerated(EnumType.STRING)
  private OrdinanceType type;

  @ManyToMany
  private List<PublicServant> publicServants = new ArrayList<>();

  public Ordinance() {}

  public Ordinance(
      Date startingDate,
      Date finishingDate,
      double workload,
      OrdinanceType type,
      String subject) {
    setStartingDate(startingDate);
    setFinishingDate(finishingDate);
    setType(type);
    setWorkload(workload);
    setSubject(subject);
  }

  public double getWorkload() {
    return workload;
  }

  public void setWorkload(double workload) {
    this.workload = workload;
  }

  public Date getStartingDate() {
    return startingDate;
  }

  public void setStartingDate(Date startingDate) {
    this.startingDate = startingDate;
  }

  public Date getFinishingDate() {
    return finishingDate;
  }

  public void setFinishingDate(Date finishingDate) {
    this.finishingDate = finishingDate;
  }

  public OrdinanceType getType() {
    return type;
  }

  public void setType(OrdinanceType type) {
    this.type = type;
  }

  public Iterator<PublicServant> getPublicServants() {
    return publicServants.iterator();
  }

  public void addPublicServant(PublicServant publicServant) {
    this.publicServants.add(publicServant);
  }

}