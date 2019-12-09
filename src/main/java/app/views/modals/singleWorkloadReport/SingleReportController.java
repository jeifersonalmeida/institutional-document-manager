package app.views.modals.singleWorkloadReport;

import app.models.Ordinance.Ordinance;
import app.models.Ordinance.OrdinanceDAO;
import app.models.Ordinance.OrdinanceType;
import app.models.PublicServant.PublicServant;
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
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.stream.Stream;

public class SingleReportController {
    @FXML
    private TextField textFieldName, textFieldRecord, textFieldTotal;

    @FXML
    private TableView<Ordinance> tableViewOrdinances;

    @FXML
    private TableColumn<Ordinance, String> tableColumnOrdinance, tableColumnWorkload;

    @FXML
    private Button buttonPrint;

    private ObservableList<Ordinance> ordinances = FXCollections.observableArrayList(new OrdinanceDAO().findAll());

    private PublicServant servant;

    private ObservableList<Ordinance> loadValues() {
        ordinances.add(new Ordinance(Calendar.getInstance().getTime().toString(), Calendar.getInstance().getTime().toString(), 1.0, OrdinanceType.ORDINANCE, "PORTARIA Nº SCL.0001/2019, DE 07 DE JANEIRO DE 2019.\n" +
                "Dispõe sobre substituição regulamentar de servidor - DAA"));
        ordinances.add(new Ordinance(Calendar.getInstance().getTime().toString(), Calendar.getInstance().getTime().toString(), 4.0, OrdinanceType.ORDINANCE, "PORTARIA Nº SCL.0002/2019, DE 07 DE JANEIRO DE 2019.\n" +
                "Dispõe sobre substituição regulamentar de servidor - DRG"));
        ordinances.add(new Ordinance(Calendar.getInstance().getTime().toString(), Calendar.getInstance().getTime().toString(), 20.0, OrdinanceType.ORDINANCE, "PORTARIA Nº SCL.0003/2019, DE 10 DE JANEIRO DE 2019\n" +
                "Dispõe sobre substituição regulamentar de servidor - DAA"));
        ordinances.add(new Ordinance(Calendar.getInstance().getTime().toString(), Calendar.getInstance().getTime().toString(), 35.0, OrdinanceType.ORDINANCE, "PORTARIA Nº SCL.0004/2019, DE 10 DE JANEIRO DE 2019\n" +
                "Designa a comissão de matrícula para o ano de 2019 do Cãmpus São Carlos do IFSP"));
        ordinances.add(new Ordinance(Calendar.getInstance().getTime().toString(), Calendar.getInstance().getTime().toString(), 17.5, OrdinanceType.ORDINANCE, "PORTARIA Nº SCL.0005/2019, DE 31 DE JANEIRO DE 2019\n" +
                "Altera a Portaria 04/2019, que designa a comissão de matrícula para o ano de 2019 do Cãmpus São Carlos do IFSP"));
        ordinances.add(new Ordinance(Calendar.getInstance().getTime().toString(), Calendar.getInstance().getTime().toString(), 13.0, OrdinanceType.ORDINANCE, "PORTARIA Nº SCL.0006/2019, DE 01 DE FEVEREIRO DE 2019\n" +
                "Dispõe sobre substituição regulamentar de servidor - CAE"));
        ordinances.add(new Ordinance(Calendar.getInstance().getTime().toString(), Calendar.getInstance().getTime().toString(), 2.0, OrdinanceType.ORDINANCE, "PORTARIA Nº SCL.0014/2019, DE 22 DE FEVEREIRO DE 2019\n" +
                "Dispõe sobre substituição regulamentar de servidor - CAE"));
        ordinances.add(new Ordinance(Calendar.getInstance().getTime().toString(), Calendar.getInstance().getTime().toString(), 12.0, OrdinanceType.ORDINANCE, "PORTARIA Nº SCL.0015/2019, DE 25 DE FEVEREIRO DE 2019\n" +
                "Dispõe sobre substituição regulamentar de servidor - DRG"));

        return ordinances;
    }

    private void fill() {
        tableColumnOrdinance.setCellValueFactory(new PropertyValueFactory<>("subject"));
        tableColumnWorkload.setCellValueFactory(new PropertyValueFactory<>("workload"));

        tableViewOrdinances.setItems(ordinances.filtered(o -> o.hasPublicServant(servant)));
        tableViewOrdinances.setPlaceholder(new Label("Nenhuma Portaria registrada"));

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

        String source = createPdf();
        String newFile = copyFile(source, selectedDirectory.getAbsolutePath());
        openFile(newFile);
        scene.setCursor(Cursor.DEFAULT);
        stage.close();
    }

    private String createPdf() throws FileNotFoundException, DocumentException {
        String baseDirectory = "./generated/singleReports/";
        String location = baseDirectory + generateFileName();

        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream(location));

        document.open();

        Font font = FontFactory.getFont(FontFactory.TIMES, 16, BaseColor.BLACK);

        Paragraph title = new Paragraph(new Phrase(new Chunk("Relatório - " + servant.getRecord(), font)));
        title.add(new Phrase(Chunk.NEWLINE));
        title.add(new Phrase(new Chunk(servant.getName(), font)));
        title.setAlignment(Element.TITLE);
        document.add(title);

        document.add(Chunk.NEWLINE);

        PdfPTable table = new PdfPTable(2);
        addTableHeader(table);
        addRows(table);

        document.add(table);
        document.addCreationDate();

        Paragraph total = new Paragraph(new Phrase(new Chunk("Total", font)));
        total.add(new Phrase(Chunk.NEWLINE));
        total.add(new Phrase(new Chunk(getTotalWorkload() + "hs", font)));
        total.setAlignment(Element.ALIGN_RIGHT);
        document.add(total);

        document.close();

        return location;

    }

    private String generateFileName(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");

        return "singleReport_" + servant.getRecord() + "_" + formatter.format(LocalDateTime.now()) + ".pdf";
    }

    private String copyFile(String source, String destination) throws IOException {
        String file = destination + "/" + generateFileName();

        Files.copy(new File(source).toPath(), new File(file).toPath(), StandardCopyOption.REPLACE_EXISTING);
        return file;
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
        Stream.of("Portaria", "Carga horária")
            .forEach(columnTitle -> {
                PdfPCell header = new PdfPCell();
                header.setBackgroundColor(BaseColor.LIGHT_GRAY);
//                header.setBorderWidth(2);
                header.setPhrase(new Phrase(columnTitle));
                table.addCell(header);
            });
    }

    private void addRows(PdfPTable table) {
        for (Ordinance ordinance : ordinances) {
            table.addCell(ordinance.getSubject());
            table.addCell(String.valueOf(ordinance.getWorkload()));
        }
    }

    public void setServant(PublicServant servant) {
        this.servant = servant;

        ordinances = ordinances.filtered(o -> o.hasPublicServant(servant));

        textFieldTotal.setText(String.valueOf(getTotalWorkload()));

        textFieldRecord.setText(this.servant.getRecord());
        textFieldName.setText(this.servant.getName());

        fill();
    }

    private double getTotalWorkload(){
        double total = 0.0;

        for (Ordinance ordinance : ordinances) {
            total += ordinance.getWorkload();
        }

        return total;
    }
}
