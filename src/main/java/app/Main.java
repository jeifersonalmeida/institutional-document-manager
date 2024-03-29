package app;

import app.models.utils.DateTransformer;
import app.models.utils.DocumentType;
import app.models.utils.PDFCopier;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Date;

public class Main extends Application {

  @Override
  public void start(Stage primaryStage) throws Exception {
    Parent root = FXMLLoader.load(getClass().getResource("/views/screens/documents/Documents.fxml"));
    primaryStage.setTitle("Institutional Document Manager");
    primaryStage.setScene(new Scene(root));
    primaryStage.setMaximized(true);
    primaryStage.show();

  }

  public static void main(String[] args) {
    launch(args);
  }

}
