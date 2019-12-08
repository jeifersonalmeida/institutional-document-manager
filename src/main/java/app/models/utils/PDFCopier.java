package app.models.utils;

import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public abstract class PDFCopier {
  private static final String baseDirectory = new JFileChooser().getFileSystemView().getDefaultDirectory() + "/IDM/";
  private static DocumentType type;

  private static String copyFile(String source, String destination) throws IOException {
    String file = destination + "/" + generateFileName();

    Files.copy(new File(source).toPath(), new File(file).toPath(), StandardCopyOption.REPLACE_EXISTING);
    return file;
  }

  private static String generateFileName(){
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");

    return type.toString()+ "_" + formatter.format(LocalDateTime.now()) + ".pdf";
  }

  public static String copyPDF(DocumentType documentType) throws IOException {
    type = documentType;
    Stage stage = new Stage();
    Scene scene = new Scene(new AnchorPane(), 10, 10);

    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("PDF");
    fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Arquivos PDF",
        "*.pdf"));
    stage.setScene(scene);

    File filePath = fileChooser.showOpenDialog(stage);

    String destination = baseDirectory + documentType.toString().substring(0, 1) + documentType.toString().substring(1).toLowerCase();

    checkDirectories(destination);

    return copyFile(filePath.getAbsolutePath(), destination);

  }

  private static void checkDirectories(String path){
    File checkDirectory = new File(path);
    if(! checkDirectory.exists()){
      checkDirectory.mkdirs();
    }
  }
}
