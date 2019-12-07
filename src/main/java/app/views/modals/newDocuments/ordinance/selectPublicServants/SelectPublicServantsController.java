package app.views.modals.newDocuments.ordinance.selectPublicServants;

import app.models.Ordinance.Ordinance;
import app.models.PublicServant.PublicServant;
import app.models.PublicServant.PublicServantDAO;
import app.views.modals.newDocuments.ordinance.OrdinanceController;
import app.views.modals.newDocuments.ordinance.selectPublicServants.servantPublicItem.ServantPublicCellController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class SelectPublicServantsController implements Initializable {

  @FXML
  private ListView<PublicServantInterface> lvPublicServants;

  private PublicServantDAO publicServantDAO = new PublicServantDAO();
  private List<PublicServant> publicServants;

  private OrdinanceController ordinanceController;

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    lvPublicServants.setCellFactory(x -> new ServantPublicCellController());
    fill();
  }

  private void fill() {
    List<PublicServantInterface> publicServants = new ArrayList<>();
    publicServantDAO.findAll().forEach(ps -> {
      PublicServantInterface publicServantInterface = new PublicServantInterface();
      publicServantInterface.setPublicServant(ps);
      publicServants.add(publicServantInterface);
    });

//    if (publicServants != null) {
//      publicServants.forEach(ps -> {
//
//      });
//    }

    lvPublicServants.setItems(FXCollections.observableArrayList(publicServants));
    lvPublicServants.refresh();
  }

  public void setPublicServants(List<PublicServant> publicServants) {
    this.publicServants = publicServants;
  }

  public void setOrdinanceController(OrdinanceController ordinanceController) {
    this.ordinanceController = ordinanceController;
  }

  @FXML
  private void saveSelection() {
    if (publicServants != null) {
      publicServants.clear();
      ObservableList<PublicServantInterface> publicServantsInterface = lvPublicServants.getItems();
      publicServantsInterface.forEach(psi -> {
        if (psi.isChecked()) {
          publicServants.add(psi.getPublicServant());
        }
      });
      if (ordinanceController != null) {
        ordinanceController.updatePublicServants();
      }
    }

    ((Stage) lvPublicServants.getScene().getWindow()).close();
  }
}
