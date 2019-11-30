package app.views.modals.newDocuments.announcement;

import app.models.Announcement.Announcement;
import app.models.Announcement.AnnouncementDAO;
import app.models.Document.Status;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.Date;

public class AnnouncementController {

  @FXML
  private TextField tfNumber, tfSubject, tfDescription;
  @FXML
  private Button btChooseFile, btSave, btPublish;

  private AnnouncementDAO announcementDAO = new AnnouncementDAO();

  public AnnouncementController() {

  }

  @FXML
  private void btSaveHandler() {
    Announcement announcement = getAnnoucement();
    announcementDAO.save(announcement);

    ((Stage) btSave.getScene().getWindow()).close();
  }

  @FXML
  private void btPublishHandler() {
  }

  private Announcement getAnnoucement() {
    Announcement announcement = new Announcement();
    announcement.setNumber(tfNumber.getText());
    announcement.setPublicationDate(new Date().toString());
    announcement.setSubject(tfSubject.getText());
    announcement.setStatus(Status.NOT_PUBLISHED);
    announcement.setFilePath("");
    return announcement;
  }
}
