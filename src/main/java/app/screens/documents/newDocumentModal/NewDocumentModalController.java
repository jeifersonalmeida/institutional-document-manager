package app.screens.documents.newDocumentModal;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.VBox;

import javax.swing.*;
import java.net.URL;
import java.util.ResourceBundle;

public class NewDocumentModalController implements Initializable {

  @FXML
  private ComboBox<String> selectDocumentType;
  @FXML
  private VBox ctnServants, ctnIndex;
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
    System.out.println("Value: " + selectDocumentType.getValue());
    if (selectDocumentType.getValue() == "Comunicado") {
      ctnServants.setDisable(true);
      ctnIndex.setDisable(true);
    } else {
      if (selectDocumentType.getValue() == "Portaria") {
        ctnServants.setDisable(true);
      } else {
        ctnServants.setDisable(false);
      }
      ctnIndex.setDisable(false);
    }
  }
}
