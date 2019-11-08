package app.screens.documents.documentsListView;

import app.models.Document;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

public class DocumentCellFactory implements Callback<ListView<Document>, ListCell<Document>> {

  @Override
  public ListCell<Document> call(ListView<Document> param) {
    return new DocumentCellController();
  }

}
