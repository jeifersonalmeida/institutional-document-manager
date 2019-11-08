package app.screens.documents;

import app.models.Announcement;
import app.models.Document;
import app.models.ServantActivity;
import app.screens.documents.documentsListView.DocumentCellFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class DocumentsController implements Initializable {

  @FXML
  Button btnServants;
  @FXML
  private ListView<Document> documentsListView;

  private ObservableList<Document> documents = FXCollections.observableArrayList();

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    documentsListView.setCellFactory(new DocumentCellFactory());
    documentsListView.setItems(documents);

    Document announcement = new ServantActivity();
    announcement.setSubject("Curso de inglês");
    documents.add(announcement);

    announcement = new Announcement();
    announcement.setSubject("Novo acesso ao IFSP");
    documents.add(announcement);

    announcement = new Announcement();
    announcement.setSubject("Novas regras para biblioteca");
    documents.add(announcement);

    announcement = new Announcement();
    announcement.setSubject("Dia 8 não havera aula");
    documents.add(announcement);
  }

  @FXML
  public void btnServants_click() throws IOException {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/screens.servants/ServantsScreen.fxml"));
    Parent root = loader.load();

    Stage dialog = new Stage();
    dialog.setTitle("Servants");
    dialog.setScene(new Scene(root));
    dialog.initModality(Modality.APPLICATION_MODAL);
    dialog.setResizable(false);
    dialog.showAndWait();
  }

}
