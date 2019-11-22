package app.views.modals.chooseDocumentType;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ChooseDocumentTypeController implements Initializable {

  @FXML
  private ChoiceBox<String> sltChooseDocumentType;

  private ObservableList<String> avaiableTypes = FXCollections.observableArrayList();

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    avaiableTypes.addAll(
        "Comunicado",
        "Portaria",
        "Projeto de Ensino");
    sltChooseDocumentType.setItems(avaiableTypes);
    sltChooseDocumentType.setValue("Comunicado");
  }

  @FXML
  private void bttNextHandler() throws IOException {
    Parent newDocument = null;
    Stage newDocumentStage = new Stage();

    switch (sltChooseDocumentType.getSelectionModel().getSelectedItem()) {
      case "Comunicado":
        newDocument = FXMLLoader.load(
            getClass().getResource("/views/modals/newDocuments/announcement/Announcement.fxml"));
        newDocumentStage.setTitle("Novo Comunicado");
        break;
      case "Portaria":
        newDocument = FXMLLoader.load(
            getClass().getResource("/views/modals/newDocuments/ordinance/Ordinance.fxml"));
        newDocumentStage.setTitle("Nova Portaria");
        break;
      case "Projeto de Ensino":
        newDocument = FXMLLoader.load(
            getClass().getResource("/views/modals/newDocuments/teachingProject/TeachingProject.fxml"));
        newDocumentStage.setTitle("Novo Projeto de Ensino");
        break;
    }

    if(newDocument == null) {
      return;
    }

    Stage stage = (Stage) sltChooseDocumentType.getScene().getWindow();
    stage.close();

    newDocumentStage.setScene(new Scene(newDocument));
    newDocumentStage.initModality(Modality.APPLICATION_MODAL);
    newDocumentStage.setResizable(false);
    newDocumentStage.showAndWait();
  }

}
