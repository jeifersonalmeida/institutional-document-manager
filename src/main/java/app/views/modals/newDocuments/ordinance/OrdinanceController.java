package app.views.modals.newDocuments.ordinance;

import app.models.Document.Status;
import app.models.Ordinance.Ordinance;
import app.models.Ordinance.OrdinanceDAO;
import app.models.PublicServant.PublicServant;
import app.views.modals.newDocuments.DocumentController;
import app.views.modals.newDocuments.ordinance.selectPublicServants.SelectPublicServantsController;
import app.views.screens.documents.DocumentsController;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class OrdinanceController extends DocumentController {

  @FXML
  private TextField tfNumber, tfSubject, tfWorkLoad;
  @FXML
  private DatePicker dpInitialValidity, dpFinalValidity;
  @FXML
  private ChoiceBox<Ordinance> cbSelectOrdinanceRevoke;
  @FXML
  private ListView<PublicServant> lvPublicServants;
  @FXML
  private Button btChooseFile, btSave, btPublish;

  private File file;

  private OrdinanceDAO ordinanceDAO = new OrdinanceDAO();

  private List<PublicServant> publicServants = new ArrayList<>();

  public OrdinanceController() {

  }

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
  }

  @FXML
  private void btSelectPublicServants() {
    FXMLLoader loader = new FXMLLoader(
        getClass().getResource("/views/modals/newDocuments/ordinance/selectPublicServants/SelectPublicServants.fxml"));
    Parent root = null;
    try {
      root = loader.load();
    } catch (IOException ioe) {
      ioe.printStackTrace();
    }
    SelectPublicServantsController spsc = loader.getController();
    spsc.setPublicServants(publicServants);
    spsc.setOrdinanceController(this);

    Stage stage = new Stage();
    stage.setScene(new Scene(root));
    stage.initModality(Modality.APPLICATION_MODAL);
    stage.setResizable(false);
    stage.showAndWait();
  }

  @FXML
  private void btChooseFile() {
    FileChooser fc = new FileChooser();
    fc.getExtensionFilters().addAll(
        new FileChooser.ExtensionFilter("PDF Files", "*.pdf"),
        new FileChooser.ExtensionFilter("DOC Files", "*.docx"));
    File file = fc.showOpenDialog(btChooseFile.getScene().getWindow());
    if (file != null) {
      this.file = file;
      btChooseFile.setText(file.getName());
    }
  }

  @FXML
  private void btSaveHandler() {
    saveOrdinance(null);
  }

  @FXML
  private void btPublishHandler() {
    Ordinance ordinance = getOrdinance();
    ordinance.setStatus(Status.PUBLISHED);
    saveOrdinance(ordinance);
  }

  private void saveOrdinance(Ordinance ordinance) {
    if (!checkFields()) {
      return;
    }

    if (ordinance == null) {
      ordinance = getOrdinance();
    }
    if (file != null) {
      try {
        ordinance.setFilePath(saveFile(file).toString());
      } catch (IOException ioe) {
        ioe.printStackTrace();
      }
    }
    ordinanceDAO.save(ordinance);

    updateController();
    closeModal();
  }

  private void updateController() {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/screens/documents/Documents.fxml"));
    try {
      loader.load();
    } catch (IOException ioe) {
      ioe.printStackTrace();
    }
    DocumentsController dc = loader.getController();
    dc.refresh();
  }

  private void closeModal() {
    ((Stage) btSave.getScene().getWindow()).close();
  }

  @Override
  protected boolean checkFields() {
    if (tfNumber.getText().isEmpty()
        || tfSubject.getText().isEmpty()
        || tfWorkLoad.getText().isEmpty()
        || dpInitialValidity.getValue() == null
        || dpInitialValidity.getValue() == null) {
      Alert alert = new Alert(Alert.AlertType.ERROR);
      alert.setTitle("Campos Vazios!");
      alert.setHeaderText("Por favor, preencha todos os campos para continuar.");
      alert.showAndWait();
      return false;
    }
    return true;
  }

  private Ordinance getOrdinance() {
    Ordinance ordinance = new Ordinance();
    ordinance.setNumber(tfNumber.getText());
    ordinance.setPublicationDate(new Date().toString());
    ordinance.setSubject(tfSubject.getText());
    ordinance.setWorkload(Double.parseDouble(tfWorkLoad.getText()));
    ordinance.setStartingDate(dpInitialValidity.getValue().toString());
    ordinance.setFinishingDate(dpInitialValidity.getValue().toString());

    Ordinance revokeOrdinance = cbSelectOrdinanceRevoke.getSelectionModel().getSelectedItem();
    if (revokeOrdinance != null) {
      ordinance.setIdOrdinanceToRevoke(revokeOrdinance.getId());
    }

    publicServants.forEach(ps -> {
      ordinance.addPublicServant(ps);
    });

    ordinance.setStatus(Status.NOT_PUBLISHED);

    return ordinance;
  }

  private Path saveFile(File file) throws IOException {
    return Files.copy(file.toPath(), new File(file.getName()).toPath());
  }

  @FXML
  private void revokeOrdinance() {
    cbSelectOrdinanceRevoke.setItems(FXCollections.observableArrayList(ordinanceDAO.findAll()));
    cbSelectOrdinanceRevoke.setDisable(!cbSelectOrdinanceRevoke.isDisable());
  }

  public void updatePublicServants() {
    lvPublicServants.setItems(FXCollections.observableArrayList(publicServants));
    lvPublicServants.refresh();
  }
}
