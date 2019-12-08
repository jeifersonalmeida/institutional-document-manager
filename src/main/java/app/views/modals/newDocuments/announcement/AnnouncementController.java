package app.views.modals.newDocuments.announcement;

import app.models.Announcement.Announcement;
import app.models.Announcement.AnnouncementDAO;
import app.models.Document.Status;
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

  private File file;

  private AnnouncementDAO announcementDAO = new AnnouncementDAO();
  private boolean isToViewOnly;

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
    if (file != null) {
      try {
        announcement.setFilePath(saveFile(file).toString());
      } catch (IOException ioe) {
        ioe.printStackTrace();
      }
    }
    announcementDAO.save(announcement);

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
    announcement.setPublicationDate(new Date().toString());
    announcement.setSubject(tfSubject.getText());
    announcement.setDescription(tfDescription.getText());
    announcement.setStatus(Status.NOT_PUBLISHED);

    return announcement;
  }

  private Path saveFile(File file) throws IOException {
    return Files.copy(file.toPath(), new File(file.getName()).toPath());
  }
}
