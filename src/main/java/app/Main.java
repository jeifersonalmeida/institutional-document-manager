package app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

  @Override
  public void start(Stage primaryStage) throws Exception {
    Parent root = FXMLLoader.load(getClass().getResource("/screens/documents/DocumentsScreen.fxml"));
    primaryStage.setTitle("Institutional Document Manager");
    primaryStage.setScene(new Scene(root));
    primaryStage.setMaximized(true);
    primaryStage.show();
  }

  public static void main(String[] args) {
    launch(args);
  }

}
