package app.models.Ordinance;

import app.models.Document.Document;
import app.models.PublicServant.PublicServant;

import javax.persistence.*;
import javax.print.Doc;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Entity
public class Ordinance extends Document {
  private double workload;
  private String startingDate;
  private String finishingDate;
  @Enumerated(EnumType.STRING)
  private OrdinanceType type;
  @ManyToMany
  private List<PublicServant> publicServants = new ArrayList<>();
  private Long idOrdinanceToRevoke;

  public Ordinance() {}

  public Ordinance(
      String startingDate,
      String finishingDate,
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

  public String getStartingDate() {
    return startingDate;
  }

  public void setStartingDate(String startingDate) {
    this.startingDate = startingDate;
  }

  public String getFinishingDate() {
    return finishingDate;
  }

  public void setFinishingDate(String finishingDate) {
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

  public boolean hasPublicServant(PublicServant servant){
    for (PublicServant publicServant : publicServants) {
      if (publicServant.getId() == servant.getId()) {
        return true;
      }
    }
    return false;
  }

  public long getIdOrdinanceToRevoke() {
    return idOrdinanceToRevoke;
  }

  public void setIdOrdinanceToRevoke(long idOrdinanceToRevoke) {
    this.idOrdinanceToRevoke = idOrdinanceToRevoke;
  }

  @Override
  public String toString() {
    return getSubject();
  }
}