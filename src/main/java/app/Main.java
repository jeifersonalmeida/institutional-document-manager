package app;

import app.DAO.PublicServantDAO;
import app.models.PublicServant;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

  @Override
  public void start(Stage primaryStage) throws Exception {
    Parent root = FXMLLoader.load(getClass().getResource("/views/screens/documents/Documents.fxml"));
    primaryStage.setTitle("Institutional Document Manager");
    primaryStage.setScene(new Scene(root));
    primaryStage.setMaximized(true);
    primaryStage.show();

    PublicServant publicServant = new PublicServant();
    publicServant.setName("jeiferson");
    PublicServantDAO publicServantDAO = new PublicServantDAO();
    publicServantDAO.save(publicServant);
    System.out.println(publicServantDAO.findAll());
  }

  public static void main(String[] args) {
    launch(args);
  }

}
