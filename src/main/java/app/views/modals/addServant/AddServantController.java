package app.views.modals.addServant;

import app.models.PublicServant;
import app.views.screens.servants.ServantsController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddServantController {
  @FXML
  private Button buttonAdd, buttonSave, buttonRemove;

  @FXML
  private TextField textFieldRecord, textFieldName;

  private PublicServant servant;
  private ServantsController parentController;

  @FXML
  public void initialize() {
    buttonAdd.managedProperty().bind(buttonAdd.visibleProperty());
    buttonSave.managedProperty().bind(buttonSave.visibleProperty());
    buttonRemove.managedProperty().bind(buttonRemove.visibleProperty());
  }

  @FXML
  public void buttonAdd_click() {
    if(!(this.textFieldName.getText().equals("") || this.textFieldRecord.getText().equals(""))){
      this.parentController.addPublicServant(new PublicServant(this.textFieldRecord.getText(), this.textFieldName.getText()));
    }
    Stage stage = (Stage) buttonAdd.getScene().getWindow();
    stage.close();
  }

  @FXML
  public void buttonSave_click() {
    if (!(textFieldRecord.getText().equals("") || textFieldName.getText().equals(""))) {
      this.servant.setId(textFieldRecord.getText());
      this.servant.setName(textFieldName.getText());
    }

    this.parentController.refreshList();
    Stage stage = (Stage) buttonAdd.getScene().getWindow();
    stage.close();
  }

  @FXML
  public void buttonRemove_click() {
    this.parentController.removePublicServant(this.textFieldRecord.getText());
    Stage stage = (Stage) buttonAdd.getScene().getWindow();
    stage.close();
  }

  public void setServant(PublicServant servant) {
    buttonAdd.setVisible(false);
    buttonSave.setVisible(true);
    buttonRemove.setVisible(true);

    textFieldRecord.setText(servant.getId());
    textFieldName.setText(servant.getName());

    this.servant = servant;
  }

  public void setParentController(ServantsController parentController) {
    this.parentController = parentController;
  }
}
