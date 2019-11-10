package app.models;

import java.util.Date;

public class Ordinance extends Document {

  private double workload;
  private Date startingDate;
  private Date finishingDate;
  private OrdinanceType type;

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

}