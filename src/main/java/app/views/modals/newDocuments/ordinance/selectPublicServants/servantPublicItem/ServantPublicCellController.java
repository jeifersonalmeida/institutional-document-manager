package app.views.modals.newDocuments.ordinance.selectPublicServants.servantPublicItem;

import app.models.PublicServant.PublicServant;
import app.views.modals.newDocuments.ordinance.selectPublicServants.PublicServantInterface;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;

import java.io.IOException;

public class ServantPublicCellController extends ListCell<PublicServantInterface> {

  @FXML
  private Label servantName;

  public ServantPublicCellController() {
    loadFXML();
  }

  private void loadFXML() {
    try {
      FXMLLoader loader = new FXMLLoader(
          getClass().getResource("/views/modals/newDocuments/ordinance/selectPublicServants/publicServantItem/PublicServantItem.fxml"));
      loader.setController(this);
      loader.setRoot(this);
      loader.load();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @FXML
  private void toggleServant() {
    getItem().setChecked(!getItem().isChecked());
  }

  @Override
  protected void updateItem(PublicServantInterface item, boolean empty) {
    super.updateItem(item, empty);
    if (empty) {
      setText(null);
      setContentDisplay(ContentDisplay.TEXT_ONLY);
    } else {
      servantName.setText(item.getPublicServant().getName());
      setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
    }
  }
}
