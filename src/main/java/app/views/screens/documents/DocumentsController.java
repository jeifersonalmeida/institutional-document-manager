package app.views.screens.documents;

import app.models.Announcement.AnnouncementDAO;
import app.models.Document.Document;
import app.models.Ordinance.Ordinance;
import app.models.Ordinance.OrdinanceDAO;
import app.models.Ordinance.OrdinanceType;
import app.models.TeachingProject.TeachingProjectDAO;
import app.views.screens.documents.documentsListView.DocumentCellController;
import com.sun.javafx.tk.FileChooserType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class DocumentsController implements Initializable {

  @FXML
  private Button bttNewDocument, btnServants, btnImportCSV, btnExportCSV;
  @FXML
  private ListView<Document> documentsListView;
  @FXML
  private TextField tfSearchDocuments;

  private List<Document> documents = new ArrayList<>();
  private ObservableList<Document> documentsObservable = FXCollections.observableArrayList();

  private AnnouncementDAO announcementDAO = new AnnouncementDAO();
  private OrdinanceDAO ordinanceDAO = new OrdinanceDAO();
  private TeachingProjectDAO teachingProjectDAO = new TeachingProjectDAO();

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    documentsListView.setCellFactory(x -> new DocumentCellController());

    documentsListView.setItems(this.documentsObservable);
    refresh();

    tfSearchDocuments.textProperty().addListener((observable, oldValue, newValue) -> {
      filterDocuments(newValue);
    });
  }

  public void getDocuments() {
    documents.clear();
    documents.addAll(announcementDAO.findAll());
    documents.addAll(ordinanceDAO.findAll());
    documents.addAll(teachingProjectDAO.findAll());
  }

  public void refresh() {
    getDocuments();
    filterDocuments(tfSearchDocuments.getText());
    documentsListView.refresh();
  }

  private void filterDocuments(String value) {
    documentsObservable.clear();
    documents.forEach(d -> {
      if (d.getSubject().contains(value)) {
        documentsObservable.add(d);
      }
    });
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

  @FXML
  public void btnImportCSV_click() throws IOException {
    Stage stage = new Stage();
    Scene scene = new Scene(new AnchorPane(), 10, 10);

//    scene.setCursor(Cursor.WAIT);
//    stage.setResizable(false);
    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("CSV");
    fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Arquivos CSV",
        "*.csv", "*.xlsx"));
    stage.setScene(scene);
//        stage.show();
    File selectedCSV = fileChooser.showOpenDialog(stage);

    FileInputStream fileInputStream = new FileInputStream(selectedCSV);

    XSSFWorkbook workbook = new XSSFWorkbook(fileInputStream);

    Iterator<Sheet> sheetIterator = workbook.sheetIterator();

    List<Ordinance> ordinanceList = new ArrayList<>();

    while (sheetIterator.hasNext()){

      XSSFSheet sheet = (XSSFSheet) sheetIterator.next();

      Iterator<Row> rowIterator = sheet.iterator();

      while (rowIterator.hasNext()) {
        try {
          String name = rowIterator.next().getCell(0).getStringCellValue();
          String description = rowIterator.next().getCell(0).getStringCellValue();

          String ordinanceNumber = name.substring(12, name.indexOf(',')).trim();
          String publicationDate = name.split(", DE ")[1];

          Ordinance ordinance = new Ordinance();
          ordinance.setNumber(ordinanceNumber);
          ordinance.setPublicationDate(publicationDate);
          ordinance.setSubject(description);
          ordinance.setType(OrdinanceType.ORDINANCE);
          if(description.contains("comiss")) ordinance.setType(OrdinanceType.COMMISSION);

//          System.out.println(ordinance.getNumber());
//          System.out.println(ordinance.getPublicationDate());
//          System.out.println(ordinance.getSubject());

          ordinanceList.add(ordinance);

        }
        catch (Exception e) {
          continue;
        }
      }
    }
    workbook.close();
    fileInputStream.close();

    new OrdinanceDAO().saveAll(ordinanceList);

    refresh();
  }

  @FXML
  public void btnExportCSV_click(){

  }

}
