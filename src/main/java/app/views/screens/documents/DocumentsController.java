package app.views.screens.documents;

import app.models.Announcement.AnnouncementDAO;
import app.models.Document.Document;
import app.models.Ordinance.OrdinanceDAO;
import app.models.TeachingProject.TeachingProjectDAO;
import app.views.screens.documents.documentsListView.DocumentCellController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class DocumentsController implements Initializable {

  @FXML
  private Button bttNewDocument, btnServants;
  @FXML
  private ListView<Document> documentsListView;

  private ObservableList<Document> documents = FXCollections.observableArrayList();

  private AnnouncementDAO announcementDAO = new AnnouncementDAO();
  private OrdinanceDAO ordinanceDAO = new OrdinanceDAO();
  private TeachingProjectDAO teachingProjectDAO = new TeachingProjectDAO();

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    documentsListView.setCellFactory(x -> new DocumentCellController());

    documentsListView.setItems(this.documents);
    refresh();
  }

  public void refresh() {
    documents.clear();
    documents.addAll(announcementDAO.findAll());
    documents.addAll(ordinanceDAO.findAll());
    documents.addAll(teachingProjectDAO.findAll());
    documentsListView.refresh();
  }

  @FXML
  public void btnServants_click() throws IOException {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/modals/servants/ServantsScreen.fxml"));
    Parent root = loader.load();

    Stage dialog = new Stage();
    dialog.setTitle("Servants");
    dialog.setScene(new Scene(root));
    dialog.initModality(Modality.APPLICATION_MODAL);
    dialog.setResizable(false);
    dialog.showAndWait();
  }

  public void bttNewDocument() throws IOException {
    VBox newDocumentModal = (VBox) FXMLLoader.load(
        getClass().getResource("/views/modals/chooseDocumentType/ChooseDocumentType.fxml"));
    Stage stage = new Stage();
    stage.setTitle("New Document");
    stage.setScene(new Scene(newDocumentModal));
    stage.initModality(Modality.APPLICATION_MODAL);
    stage.setResizable(false);
    stage.showAndWait();
  }

}
