package app.views.modals.generalWorkloadReport;

import app.models.PublicServant.PublicServant;
import app.models.PublicServant.PublicServantDAO;
import com.itextpdf.text.*;
import com.itextpdf.text.Font;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Stream;

public class GeneralReportController {
    @FXML
    private TableView<PublicServant> tableViewServants;

    @FXML
    private TableColumn<PublicServant, String> tableColumnName;

    @FXML
    private TableColumn<Double, String> tableColumnWorkload;

    @FXML
    private Button buttonPrint;

    private ObservableList<PublicServant> publicServants = FXCollections.observableArrayList(new PublicServantDAO().findAll());

    @FXML
    private void initialize() {
        tableColumnName.setCellValueFactory(new PropertyValueFactory<>("name"));
        tableColumnWorkload.setCellValueFactory(new PropertyValueFactory<>("totalWorkload"));
        tableViewServants.setPlaceholder(new Label("Nenhum servidor registrado"));
        this.tableViewServants.setItems(publicServants);
    }

    @FXML
    private void buttonPrint_click() throws IOException, DocumentException {
        Stage stage = new Stage();
        Scene scene = new Scene(new AnchorPane(), 10, 10);

        scene.setCursor(Cursor.WAIT);
        stage.setResizable(false);
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Salvar Pdf");
        stage.setScene(scene);
//        stage.show();
        File selectedDirectory = directoryChooser.showDialog(stage);

        String source = createPdf(selectedDirectory.getAbsolutePath());
        openFile(source);
        scene.setCursor(Cursor.DEFAULT);
        stage.close();
    }

    private String createPdf(String baseDirectory) throws FileNotFoundException, DocumentException {
        String location = baseDirectory + generateFileName();

        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream(location));

        document.open();

        Font font = FontFactory.getFont(FontFactory.TIMES, 16, BaseColor.BLACK);
        Chunk chunk = new Chunk("Relatório Geral", font);

        Phrase phrase = new Phrase();
        phrase.add(chunk);
        Paragraph paragraph = new Paragraph();
        paragraph.add(phrase);
        paragraph.setAlignment(Element.TITLE);

        document.add(paragraph);
        document.add(Chunk.NEWLINE);

        PdfPTable table = new PdfPTable(2);
        addTableHeader(table);
        addRows(table);

        document.add(table);
        document.addCreationDate();
        document.close();

        return location;

    }

    private String generateFileName(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");

        return "generalReport_" + formatter.format(LocalDateTime.now()) + ".pdf";
    }

    private void openFile(String location){
        if (Desktop.isDesktopSupported()) {
            try {
                File myFile = new File(location);
                Desktop.getDesktop().open(myFile);
            } catch (IOException ex) {
                // no application registered for PDFs
                System.out.println("Damm!");
            }
        }
    }

    private void addTableHeader(PdfPTable table) {
        Stream.of("Servidor", "Carga horária")
            .forEach(columnTitle -> {
                PdfPCell header = new PdfPCell();
                header.setBackgroundColor(BaseColor.LIGHT_GRAY);
//                header.setBorderWidth(2);
                header.setPhrase(new Phrase(columnTitle));
                table.addCell(header);
            });
    }

    private void addRows(PdfPTable table) {
        for (PublicServant publicServant : publicServants) {
            table.addCell(publicServant.getName());
            table.addCell(String.valueOf(publicServant.getTotalWorkload()));
        }
    }

}
