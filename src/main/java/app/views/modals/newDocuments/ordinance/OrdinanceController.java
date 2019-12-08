package app.views.modals.newDocuments.ordinance;

import app.models.Document.Status;
import app.models.Ordinance.Ordinance;
import app.models.Ordinance.OrdinanceDAO;
import app.models.Ordinance.OrdinanceType;
import app.models.PublicServant.PublicServant;
import app.models.utils.DateTransformer;
import app.models.utils.DocumentType;
import app.models.utils.PDFCopier;
import app.views.modals.newDocuments.DocumentController;
import app.views.modals.newDocuments.ordinance.selectPublicServants.SelectPublicServantsController;
import app.views.screens.documents.DocumentsController;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OrdinanceController extends DocumentController {

  @FXML
  private TextField tfNumber, tfSubject, tfWorkLoad;
  @FXML
  private DatePicker dpInitialValidity, dpFinalValidity;
  @FXML
  private CheckBox cbOrdinanceRevoke;
  @FXML
  private ChoiceBox<Ordinance> cbSelectOrdinanceRevoke;
  @FXML
  private ChoiceBox<OrdinanceType> cbSelectType;
  @FXML
  private ListView<PublicServant> lvPublicServants;
  @FXML
  private Button btSelectServants, btChooseFile, btSave, btPublish;

  private OrdinanceDAO ordinanceDAO = new OrdinanceDAO();

  private List<PublicServant> publicServants = new ArrayList<>();

  private DocumentsController documentsController;

  private String path = "";

  public OrdinanceController() {

  }

  @FXML
  public void initialize() {
    cbSelectType.setItems(FXCollections.observableArrayList(OrdinanceType.values()));
  }

  public void setFields(Ordinance ordinance) {
    if (ordinance != null) {
      tfNumber.setText(ordinance.getNumber());
      tfSubject.setText(ordinance.getSubject());
      tfWorkLoad.setText(String.valueOf(ordinance.getWorkload()));
      cbSelectType.setValue(ordinance.getType());

//      dpInitialValidity.setValue();
//      dpFinalValidity.setValue();

      if (ordinance.getIdOrdinanceToRevoke() != null) {
        Ordinance ordinanceToRevoke = ordinanceDAO.find(ordinance.getIdOrdinanceToRevoke());
        if (ordinanceToRevoke != null) {
          cbOrdinanceRevoke.setSelected(true);
          cbSelectOrdinanceRevoke.setValue(ordinanceToRevoke);
        } else {
          cbOrdinanceRevoke.setSelected(false);
        }
      }

      List<PublicServant> publicServants = new ArrayList<>();
      ordinance.getPublicServants().forEachRemaining(o -> {
        publicServants.add(o);
      });
      lvPublicServants.setItems(FXCollections.observableArrayList(publicServants));

      if (ordinance.getFilePath() != null) {
        File file = new File(ordinance.getFilePath());
        if (file.exists()) {
          btChooseFile.setText(file.getName());
        }
      }
    }
  }

  public void setIsToViewOnly() {
    tfNumber.setDisable(true);
    tfSubject.setDisable(true);
    tfWorkLoad.setDisable(true);
    cbSelectType.setDisable(true);
    dpInitialValidity.setDisable(true);
    dpFinalValidity.setDisable(true);
    cbOrdinanceRevoke.setDisable(true);
    cbSelectOrdinanceRevoke.setDisable(true);
    btSelectServants.setVisible(false);
    btChooseFile.setVisible(false);
    btSave.setVisible(false);
    btPublish.setVisible(false);
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
  private void btChooseFile() throws IOException {
    String path = PDFCopier.copyPDF(DocumentType.ORDINANCE);
    this.path = path;
    btChooseFile.setText(path);
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

    ordinanceDAO.save(ordinance);

    updateController();
    closeModal();
  }

  private void updateController() {
    if (this.documentsController != null) {
      this.documentsController.refresh();
    }
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
    ordinance.setPublicationDate(DateTransformer.dateToString(new Date()));
    ordinance.setSubject(tfSubject.getText());
    ordinance.setWorkload(Double.parseDouble(tfWorkLoad.getText()));
    ordinance.setType(cbSelectType.getSelectionModel().getSelectedItem());
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
    ordinance.setFilePath(path);

    return ordinance;
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

  public void setDocumentsController(DocumentsController documentsController) {
    this.documentsController = documentsController;
  }
}
