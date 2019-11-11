package app.modals.manageDocument;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class ManageDocumentController implements Initializable {

  @FXML
  private ComboBox<String> selectDocumentType;
  @FXML
  private VBox ctnServants;
  @FXML
  private ListView<String> listViewServants;

  private final String[] types = {
      "Comunicado",
      "Portaria",
      "Projeto Institucional",
      "Comissão",
  };

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    setupInterface();
  }

  private void setupInterface() {
    selectDocumentType.setItems(FXCollections.observableArrayList(types));
    selectDocumentType.setValue(selectDocumentType.getItems().get(0));
  }

  public void changeSelectHandler() {
      if (selectDocumentType.getValue() == "Comunicado" || selectDocumentType.getValue() == "Portaria") {
        ctnServants.setDisable(true);
      } else {
        ctnServants.setDisable(false);
      }
  }
}
