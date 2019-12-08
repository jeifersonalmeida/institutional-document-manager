package app.views.modals.newDocuments.announcement;

import app.models.Announcement.Announcement;
import app.models.Announcement.AnnouncementDAO;
import app.models.Document.Status;
import app.models.utils.DateTransformer;
import app.models.utils.DocumentType;
import app.models.utils.PDFCopier;
import app.views.modals.newDocuments.DocumentController;
import app.views.screens.documents.DocumentsController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Date;

public class AnnouncementController extends DocumentController {

  @FXML
  private TextField tfNumber, tfSubject, tfDescription;
  @FXML
  private Button btChooseFile, btSave, btPublish;

  private AnnouncementDAO announcementDAO = new AnnouncementDAO();

  private boolean isToEdit = false;

  private DocumentsController documentsController;

  private String path = "";

  public AnnouncementController() {
  }

  @FXML
  public void initialize() {
  }

  public void setFields(Announcement announcement) {
    if (announcement != null) {
      tfNumber.setText(announcement.getNumber());
      tfSubject.setText(announcement.getSubject());
      tfDescription.setText(announcement.getDescription());
      path = announcement.getFilePath();
      this.isToEdit = true;
    }
  }

  public void setIsToViewOnly() {
    tfNumber.setDisable(true);
    tfSubject.setDisable(true);
    tfDescription.setDisable(true);
    btChooseFile.setVisible(false);
    btSave.setVisible(false);
    btPublish.setVisible(false);
  }

  @FXML
  private void btChooseFile() throws IOException {
    String path = PDFCopier.copyPDF(DocumentType.ORDINANCE);
    this.path = path;
    btChooseFile.setText(path);
  }

  @FXML
  private void btSaveHandler() {
    saveAnnouncement(null);
  }

  @FXML
  private void btPublishHandler() {
    Announcement announcement = getAnnouncement();
    announcement.setStatus(Status.PUBLISHED);
    saveAnnouncement(announcement);
  }

  private void saveAnnouncement(Announcement announcement) {
    if (!checkFields()) {
      return;
    }

    if (announcement == null) {
      announcement = getAnnouncement();
    }
    if (!isToEdit) {
      announcementDAO.save(announcement);
    } {
      announcementDAO.edit(announcement);
    }

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
      || tfDescription.getText().isEmpty()) {
      Alert alert = new Alert(Alert.AlertType.ERROR);
      alert.setTitle("Campos Vazios!");
      alert.setHeaderText("Por favor, preencha todos os campos para continuar.");
      alert.showAndWait();
      return false;
    }
    return true;
  }

  private Announcement getAnnouncement() {
    Announcement announcement = new Announcement();
    announcement.setNumber(tfNumber.getText());
    announcement.setPublicationDate(DateTransformer.dateToString(new Date()));
    announcement.setSubject(tfSubject.getText());
    announcement.setDescription(tfDescription.getText());
    announcement.setStatus(Status.NOT_PUBLISHED);
    announcement.setFilePath(path);

    return announcement;
  }

  public void setDocumentsController(DocumentsController documentsController) {
    this.documentsController = documentsController;
  }
}
