package app.views.modals.newDocuments.ordinance.selectPublicServants;

import app.models.PublicServant.PublicServant;

public class PublicServantInterface {

  private PublicServant publicServant;
  private boolean checked;

  public PublicServant getPublicServant() {
    return publicServant;
  }

  public void setPublicServant(PublicServant publicServant) {
    this.publicServant = publicServant;
  }

  public boolean isChecked() {
    return checked;
  }

  public void setChecked(boolean checked) {
    this.checked = checked;
  }
}
