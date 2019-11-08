package app.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class ServantActivity extends Ordinance {

  private List<PublicServant> publicServants = new ArrayList<>();

  public ServantActivity() {}

  public ServantActivity(
      Date startingDate,
      Date finishingDate,
      double workload,
      OrdinanceType type,
      String subject) {
    super(startingDate, finishingDate, workload, type, subject);
  }

  public Iterator<PublicServant> getPublicServants() {
    return publicServants.iterator();
  }

  public void addPublicServant(PublicServant publicServant) {
    this.publicServants.add(publicServant);
  }

}
