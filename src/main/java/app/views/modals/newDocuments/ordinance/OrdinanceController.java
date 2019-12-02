package app.views.modals.newDocuments.ordinance;

import app.models.Announcement.Announcement;
import app.models.Document.Status;
import app.models.Ordinance.Ordinance;
import app.models.Ordinance.OrdinanceDAO;
import app.models.PublicServant.PublicServant;
import app.models.PublicServant.PublicServantDAO;
import app.views.modals.newDocuments.DocumentController;
import app.views.screens.documents.DocumentsController;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OrdinanceController extends DocumentController {

  @FXML
  private TextField tfNumber, tfSubject, tfWorkLoad;
  @FXML
  private DatePicker dpInitialValidity, dpFinalValidity;
  @FXML
  private ChoiceBox<String> cbSelectOrdinanceRevoke;
  @FXML
  private Button btChooseFile, btSave, btPublish;

  private File file;

  private OrdinanceDAO ordinanceDAO = new OrdinanceDAO();

  public OrdinanceController() {

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
    ordinance.addPublicServant(new PublicServantDAO().findAll().get(0));
    ordinance.setStatus(Status.NOT_PUBLISHED);

    return ordinance;
  }

  private Path saveFile(File file) throws IOException {
    return Files.copy(file.toPath(), new File(file.getName()).toPath());
  }

  @FXML
  private void revokeOrdinance() {
    List<String> subjectsOrdinances = new ArrayList();
    ordinanceDAO.findAll().forEach(ordinance -> {
      subjectsOrdinances.add(ordinance.getSubject());
    });
    cbSelectOrdinanceRevoke.setItems(FXCollections.observableArrayList(subjectsOrdinances));
    cbSelectOrdinanceRevoke.setDisable(!cbSelectOrdinanceRevoke.isDisable());
  }

}
