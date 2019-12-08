package app.views.screens.documents;

import app.models.Announcement.Announcement;
import app.models.Announcement.AnnouncementDAO;
import app.models.Document.Document;
import app.models.Document.Status;
import app.models.Ordinance.Ordinance;
import app.models.Ordinance.OrdinanceDAO;
import app.models.Ordinance.OrdinanceType;
import app.models.TeachingProject.TeachingProject;
import app.models.TeachingProject.TeachingProjectDAO;
import app.models.utils.filter.DocumentTypeFilter;
import app.models.utils.filter.DocumentOrderFilter;
import app.views.modals.documentChooserCSV.DocumentChooserCSVController;
import app.views.screens.documents.documentsListView.DocumentCellController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.awt.*;
import java.io.*;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.ArrayList;
import java.util.List;

public class DocumentsController implements Initializable {

  @FXML
  private Button bttNewDocument, btnServants, btnImportCSV, btnExportCSV;
  @FXML
  private ListView<Document> documentsListView;
  @FXML
  private TextField tfSearchDocuments;
  @FXML
  private ComboBox<String> cbDocumentType, cbOrder;

  private List<Document> documents = new ArrayList<>();
  private ObservableList<Document> documentsObservable = FXCollections.observableArrayList();

  private AnnouncementDAO announcementDAO = new AnnouncementDAO();
  private OrdinanceDAO ordinanceDAO = new OrdinanceDAO();
  private TeachingProjectDAO teachingProjectDAO = new TeachingProjectDAO();
  private String csvType;

  private Map<String, DocumentTypeFilter> typeFilters = new HashMap<>();
  private Map<String, DocumentOrderFilter> orderFilters = new HashMap<>();

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    documentsListView.setCellFactory(x -> new DocumentCellController());

    typeFilters.put("Todos", DocumentTypeFilter.ALL);
    typeFilters.put("Portarias", DocumentTypeFilter.ORDINANCE);
    typeFilters.put("Comunicados", DocumentTypeFilter.ANNOUNCEMENT);
    typeFilters.put("Comissões", DocumentTypeFilter.COMMISSION);
    typeFilters.put("Projetos Institucionais", DocumentTypeFilter.INSTITUTIONAL_PROJECT);
    typeFilters.put("Projetos de Ensino", DocumentTypeFilter.TEACHING_PROJECT);

    documentsListView.setItems(this.documentsObservable);
    refresh();

    ObservableList<String> filterList = FXCollections.observableArrayList(typeFilters.keySet());

    cbDocumentType.setItems(filterList);
    cbDocumentType.setValue(filterList.get(4));

    orderFilters.put("Mais recente primeiro", DocumentOrderFilter.MOST_RECENT);
    orderFilters.put("Mais antigo primeiro", DocumentOrderFilter.LEAST_RECENT);

    filterList = FXCollections.observableArrayList(orderFilters.keySet());

    cbOrder.setItems(filterList);
    cbOrder.setValue(filterList.get(1));
    cbOrder_action();

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
    fileChooser.setTitle("XLSX");
    fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Arquivos XLSX",
        "*.xlsx"));
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
          ordinance.setStatus(Status.PUBLISHED);
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
  public void btnExportCSV_click() throws IOException {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/modals/documentChooserCSV/DocumentChooserCSV.fxml"));
    Parent root = loader.load();

    DocumentChooserCSVController controller = loader.getController();
    controller.setParentController(this);

    Stage dialog = new Stage();
    dialog.setTitle("Tipo");
    dialog.setScene(new Scene(root));
    dialog.initModality(Modality.APPLICATION_MODAL);
    dialog.setResizable(false);
    controller.setThisStage(dialog);
    dialog.showAndWait();

    Stage stage = new Stage();
    Scene scene = new Scene(new AnchorPane(), 10, 10);

    scene.setCursor(Cursor.WAIT);
    stage.setResizable(false);
    DirectoryChooser directoryChooser = new DirectoryChooser();
    directoryChooser.setTitle("Salvar CSV");
    stage.setScene(scene);
//        stage.show();
    File selectedDirectory = directoryChooser.showDialog(stage);

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");

    String path = selectedDirectory + "/IDM_CSV_" + csvType + "_" + formatter.format(LocalDateTime.now()) + ".csv";

    CSVPrinter csvPrinter = new CSVPrinter(new FileWriter(path), CSVFormat.EXCEL.withHeader("Número", "Descrição", "Data publicação", "Status"));
    List<Document> documents = new ArrayList<>();

    documents.addAll(new OrdinanceDAO().findAll());
    documents.addAll(new AnnouncementDAO().findAll());

    for(Document document : documents){
      System.out.println(csvType);
      switch (csvType){
        case "Portaria":
          if(!(document instanceof Ordinance)){
            continue;
          }
          if(((Ordinance) document).getType() != OrdinanceType.ORDINANCE){
            continue;
          }
          break;
        case "Comunicado":
          if(!(document instanceof Announcement)){
            continue;
          }
          break;
        case "Comissão":
          if(!(document instanceof Ordinance)){
            continue;
          }
          if(((Ordinance) document).getType() != OrdinanceType.COMMISSION){
            continue;
          }
          break;
        case "Projeto institucional":
          if(!(document instanceof Ordinance)){
            continue;
          }
          if(((Ordinance) document).getType() != OrdinanceType.INSTITUTIONAL_PROJECT){
            continue;
          }
          break;
      }

      String number = document.getNumber();
      String subject = document.getSubject();
      String pub = document.getPublicationDate();
      String status = document.getStatus().toString();

      csvPrinter.printRecord(number, subject, pub, status);

    }

    csvPrinter.flush();

    openFile(path);

  }

  private void openFile(String location){
    if (Desktop.isDesktopSupported()) {
      try {
        File myFile = new File(location);
        Desktop.getDesktop().open(myFile);
      } catch (IOException ex) {
        // no application registered
        System.out.println("Damm!");
      }
    }
  }

  public void setCsvType(String csvType) {
    this.csvType = csvType;
  }

  @FXML
  public void cbDocumentType_action(){
    this.documentsListView.setItems(this.documentsObservable.filtered(d -> checkDocumentType(d, typeFilters.get(cbDocumentType.getValue()))));
    this.documentsListView.refresh();
  }

  private boolean checkDocumentType(Document document, DocumentTypeFilter filter){
    switch (filter){
      case ORDINANCE:
        if(document instanceof Ordinance){
          if(((Ordinance)document).getType() == OrdinanceType.ORDINANCE){
            return true;
          }
          return false;
        }
        return false;
      case ANNOUNCEMENT:
        if(document instanceof Announcement){
          return true;
        }
        return false;
      case COMMISSION:
        if(document instanceof Ordinance){
          if(((Ordinance)document).getType() == OrdinanceType.COMMISSION){
            return true;
          }
          return false;
        }
        return false;
      case INSTITUTIONAL_PROJECT:
        if(document instanceof Ordinance){
          if(((Ordinance)document).getType() == OrdinanceType.INSTITUTIONAL_PROJECT){
            return true;
          }
          return false;
        }
        return false;
      case TEACHING_PROJECT:
        if(document instanceof TeachingProject){
          return true;
        }
        return false;
      case ALL:
        return true;
    }
    return false;
  }

  @FXML
  public void cbOrder_action(){
    Comparator<Document> comparator = Comparator.comparing(Document::getPublicationDateFormatted, Comparator.naturalOrder());

    switch (orderFilters.get(cbOrder.getValue())){
      case MOST_RECENT:
        comparator = comparator.reversed();
    }

    documentsObservable.sort(comparator);
    documentsListView.refresh();
  }

//  @FXML
//  public void filter(){
//    String filterString = this.textFieldSearchbar.getText();
//    System.out.println(filterString);
//
//    if(!filterString.equals("")){
//      this.tableViewServants.setItems(this.publicServants.filtered(p-> p.getRecord().contains(filterString) || p.getName().contains(filterString)));
//      this.tableViewServants.refresh();
//
//      return;
//    }
//    this.tableViewServants.setItems(this.publicServants);
//    this.tableViewServants.refresh();
//
//  }
}
