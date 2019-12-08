package app.views.modals.documentChooserCSV;

import app.views.screens.documents.DocumentsController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.input.InputEvent;
import javafx.stage.Stage;

public class DocumentChooserCSVController {
  @FXML
  private Button btnNext;
  @FXML
  private ChoiceBox cbType;

  private ObservableList<String> types = FXCollections.observableArrayList();

  private DocumentsController parentController;

  private Stage thisStage;

  @FXML
  private void initialize(){
    types.addAll("Portaria", "Comunicado", "Comissão", "Projeto institucional");
    cbType.setItems(types);
    cbType.setValue(types.get(0));
  }

  @FXML
  private void btnNext_click(/*InputEvent e*/){
    parentController.setCsvType((String) cbType.getSelectionModel().getSelectedItem());
//    final Node source = (Node) e.getSource();
//    final Stage stage = (Stage) source.getScene().getWindow();
//    stage.close();
    thisStage.close();
  }

  public DocumentsController getParentController() {
    return parentController;
  }

  public void setParentController(DocumentsController parentController) {
    this.parentController = parentController;
  }

  public void setThisStage(Stage thisStage) {
    this.thisStage = thisStage;
  }
}
