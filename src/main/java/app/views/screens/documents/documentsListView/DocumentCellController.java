package app.views.screens.documents.documentsListView;

import app.models.Announcement.Announcement;
import app.models.Document.Document;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

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
  private ImageView view;

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
      if (document.getFilePath() != "") {
        status.setText("Pendente de documento!");
      }

      setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
    }
  }
}
