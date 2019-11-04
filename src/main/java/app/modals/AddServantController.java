package app.modals;

import app.models.PublicServant;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddServantController {
  @FXML
  private Button buttonAdd, buttonSave, buttonRemove;

  @FXML
  private TextField textFieldRecord, textFieldName;

  @FXML
  public void initialize() {
    buttonAdd.managedProperty().bind(buttonAdd.visibleProperty());
    buttonSave.managedProperty().bind(buttonSave.visibleProperty());
    buttonRemove.managedProperty().bind(buttonRemove.visibleProperty());
  }

  @FXML
  public void buttonAdd_click() {
    Stage stage = (Stage) buttonAdd.getScene().getWindow();
    stage.close();
  }

  @FXML
  public void buttonSave_click() {
    Stage stage = (Stage) buttonAdd.getScene().getWindow();
    stage.close();
  }

  @FXML
  public void buttonRemove_click() {
    Stage stage = (Stage) buttonAdd.getScene().getWindow();
    stage.close();
  }

  public void setServant(PublicServant servant) {
    buttonAdd.setVisible(false);
    buttonSave.setVisible(true);
    buttonRemove.setVisible(true);

    textFieldRecord.setText(servant.getId());
    textFieldName.setText(servant.getName());
  }
}
