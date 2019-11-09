package app.screens.main;

import app.screens.singleWorkloadReport.SingleReportController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class MainController {
  @FXML
  HBox mainHBox;
  @FXML
  Button btnServants;

  @FXML
  public void initialize() throws IOException {
    setSceneOnPane("/screens.documents/DocumentsScreen.fxml");
  }

  public void setSceneOnPane(String path) throws IOException {
    Node fxml = FXMLLoader.load(getClass().getResource(path));
    mainHBox.getChildren().add(fxml);
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
