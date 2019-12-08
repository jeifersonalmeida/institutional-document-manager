package app.views.screens.documents.documentsListView;

import app.models.Announcement.Announcement;
import app.models.Announcement.AnnouncementDAO;
import app.models.Document.Document;
import app.models.Document.Status;
import app.models.Ordinance.Ordinance;
import app.models.Ordinance.OrdinanceDAO;
import app.models.TeachingProject.TeachingProject;
import app.models.TeachingProject.TeachingProjectDAO;
import app.views.modals.newDocuments.announcement.AnnouncementController;
import app.views.modals.newDocuments.ordinance.OrdinanceController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.awt.*;
import java.io.File;
import java.io.IOException;

public class DocumentCellController extends ListCell<Document> {

  @FXML
  private ImageView type;
  @FXML
  private Label subject;
  @FXML
  private Label publishDate;
  @FXML
  private Label status;
  @FXML
  private ImageView view, remove, publish;

  public DocumentCellController() {
    loadFXML();
  }

  private void loadFXML() {
    try {
      FXMLLoader loader = new FXMLLoader(
          getClass().getResource("/views/screens/documents/documentsListView/DocumentCell.fxml"));
      loader.setController(this);
      loader.setRoot(this);
      loader.load();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  protected void updateItem(Document document, boolean empty) {
    super.updateItem(document, empty);
    if (empty) {
        setText(null);
        setContentDisplay(ContentDisplay.TEXT_ONLY);
    } else {
      if (document instanceof Announcement) {
        type.setImage(new Image("/assets/icons/megaphone.png"));
      } else {
        type.setImage(new Image("/assets/icons/picket-fence.png"));
      }
      subject.setText(document.getSubject());

      publishDate.setText("Publicado em: " + document.getPublicationDate());
      if (document.getFilePath() != null && !document.getFilePath().isEmpty()) {
        status.setText("Documento enviado!");
        view.setDisable(false);
        view.setVisible(true);
      } else {
        status.setText("Pendente de documento!");
        view.setDisable(true);
        view.setVisible(false);
      }

      if (document.getStatus() == Status.PUBLISHED) {
        remove.setDisable(true);
        remove.setVisible(false);
        publish.setImage(new Image("/assets/icons/tick.png"));
      }

      setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
    }
  }

  @FXML
  private void publishDocument() {
    getItem().setStatus(Status.PUBLISHED);
    updateItem(getItem(), false);
  }

  @FXML
  private void removeDocument() {
    Document document = getItem();
    if (document.getStatus() == Status.PUBLISHED) {
      return;
    } else if (document instanceof Announcement) {
      AnnouncementDAO announcementDAO = new AnnouncementDAO();
      announcementDAO.delete(document.getId());
    } else if (document instanceof Ordinance) {
      OrdinanceDAO ordinanceDAO = new OrdinanceDAO();
      ordinanceDAO.delete(document.getId());
    } else if (document instanceof TeachingProject) {
      TeachingProjectDAO teachingProjectDAO = new TeachingProjectDAO();
      teachingProjectDAO.delete(document.getId());
    }
  }

  @FXML
  private void viewDocumentFile() throws IOException {
    File file = new File(getItem().getFilePath());
    if (file != null) {
      if(!Desktop.isDesktopSupported()) {
        System.out.println("Desktop is not supported");
        return;
      }
      Desktop desktop = Desktop.getDesktop();
      if(file.exists()) {
        desktop.open(file);
      } else {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Arquivo não encontrado!");
        alert.setContentText("O caminho do arquivo está incorreto ou o arquivo foi removido de seu diretorio!");
        alert.showAndWait();
      }
    }
  }

  @FXML
  private void viewDocument() throws IOException {
    Document document = getItem();
    Parent parent = null;
    Stage stage = new Stage();
    if (document instanceof Announcement) {
      FXMLLoader loader = new FXMLLoader(
          getClass().getResource("/views/modals/newDocuments/announcement/Announcement.fxml"));
      parent = loader.load();
      AnnouncementController announcementController = loader.getController();
      announcementController.setFields((Announcement) document);
      announcementController.setIsToViewOnly();
      stage.setTitle("Editar comunicado");
    } else if (document instanceof Ordinance) {
      FXMLLoader loader = new FXMLLoader(
          getClass().getResource("/views/modals/newDocuments/ordinance/Ordinance.fxml"));
      parent = loader.load();
      OrdinanceController ordinanceController = loader.getController();
      ordinanceController.setFields((Ordinance) document);
      ordinanceController.setIsToViewOnly();
      stage.setTitle("Editar portaria");
    }

    if (parent == null) {
      return;
    }

    stage.setScene(new Scene(parent));
    stage.initModality(Modality.APPLICATION_MODAL);
    stage.setResizable(false);
    stage.showAndWait();
  }

  @FXML
  private void editDocument() throws IOException {
    Document document = getItem();
    Parent parent = null;
    Stage stage = new Stage();
    if (document instanceof Announcement) {
      FXMLLoader loader = new FXMLLoader(
          getClass().getResource("/views/modals/newDocuments/announcement/Announcement.fxml"));
      parent = loader.load();
      AnnouncementController announcementController = loader.getController();
      announcementController.setFields((Announcement) document);
      stage.setTitle("Editar comunicado");
    } else if (document instanceof Ordinance) {
      FXMLLoader loader = new FXMLLoader(
          getClass().getResource("/views/modals/newDocuments/ordinance/Ordinance.fxml"));
      parent = loader.load();
      OrdinanceController ordinanceController = loader.getController();
      ordinanceController.setFields((Ordinance) document);
      stage.setTitle("Editar portaria");
    }

    if (parent == null) {
      return;
    }

    stage.setScene(new Scene(parent));
    stage.initModality(Modality.APPLICATION_MODAL);
    stage.setResizable(false);
    stage.showAndWait();
  }
}
