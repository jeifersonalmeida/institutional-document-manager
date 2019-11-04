package app.screens.main;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class MainController {
  @FXML
  HBox mainHBox;

  @FXML
  public void initialize() throws IOException {
    setSceneOnPane("/screens.servants/ServantsScreen.fxml");
  }

  public void setSceneOnPane(String path) throws IOException {
    Node fxml = FXMLLoader.load(getClass().getResource(path));
    mainHBox.getChildren().add(fxml);
  }
}
