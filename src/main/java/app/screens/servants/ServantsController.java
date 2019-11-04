package app.screens.servants;

import app.modals.AddServantController;
import app.models.PublicServant;
import app.screens.generalWorkloadReport.GeneralReportController;
import app.screens.singleWorkloadReport.SingleReportController;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class ServantsController {
    @FXML
    private ObservableList<PublicServant> publicServants = FXCollections.observableArrayList();

    @FXML
    private TextField textFieldSearchbar;

    @FXML
    private TableView<PublicServant> tableViewServants;

    @FXML
    private TableColumn<PublicServant, String> tableColumnRecord, tableColumnName;

    @FXML
    private TableColumn<PublicServant, Button> tableColumnAction;

    @FXML
    private Button buttonAddNew, buttonEdit, buttonSingleReport, buttonGeneralReport;

    private String addServantPath = "/modals.addServant/AddServantModal.fxml";

    @FXML
    private void initialize() {
        fill();
    }

    private ObservableList<PublicServant> loadValues() {
        publicServants.add(new PublicServant("SC3528874", "Thomas Shelby"));
        publicServants.add(new PublicServant("SC6248833", "Grace Shelby"));
        publicServants.add(new PublicServant("SC3990183", "Arthur Shelby"));
        publicServants.add(new PublicServant("SC7710223", "John Shelby"));
        publicServants.add(new PublicServant("SC9044345", "Michael Gray"));
        publicServants.add(new PublicServant("SC2566811", "Polly Gray"));
        publicServants.add(new PublicServant("SC9134104", "Lizzie Stark"));
        publicServants.add(new PublicServant("SC3404597", "Ada Thorne"));

        return publicServants;
    }

    @FXML
    private void fill() {
        tableColumnRecord.setCellValueFactory(new PropertyValueFactory<>("id"));
        tableColumnName.setCellValueFactory(new PropertyValueFactory<>("name"));

        tableViewServants.setItems(loadValues());
        tableViewServants.setPlaceholder(new Label("No Servants registered"));

    }

    @FXML
    public void buttonAddNew_click() throws IOException {
        Stage dialog = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource(addServantPath));
        dialog.setTitle("Add servant");
        dialog.setScene(new Scene(root));
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.setResizable(false);
        dialog.showAndWait();
    }

    @FXML
    public void buttonEdit_click() throws IOException {
        PublicServant servant = tableViewServants.getSelectionModel().getSelectedItem();

        if (servant != null) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(addServantPath));
            Parent root = loader.load();

            AddServantController controller = loader.getController();
            controller.setServant(servant);

            Stage dialog = new Stage();
            dialog.setTitle("Edit servant");
            dialog.setScene(new Scene(root));
            dialog.initModality(Modality.APPLICATION_MODAL);
            dialog.setResizable(false);
            dialog.showAndWait();
        }
    }

    @FXML
    public void buttonSingleReport_click() throws IOException {
        PublicServant servant = tableViewServants.getSelectionModel().getSelectedItem();

        if (servant != null) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/screens.singleWorkloadReport/SingleReport.fxml"));
            Parent root = loader.load();

            SingleReportController controller = loader.getController();
            controller.setServant(servant);

            Stage dialog = new Stage();
            dialog.setTitle("Workload report");
            dialog.setScene(new Scene(root));
            dialog.initModality(Modality.APPLICATION_MODAL);
            dialog.setResizable(false);
            dialog.showAndWait();
        }
    }

    @FXML
    public void buttonGeneralReport_click() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/screens.generalWorkloadReport/GeneralReport.fxml"));
        Parent root = loader.load();

        GeneralReportController controller = loader.getController();
        controller.setServants(publicServants);

        Stage dialog = new Stage();
        dialog.setTitle("Workload report");
        dialog.setScene(new Scene(root));
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.setResizable(false);
        dialog.showAndWait();
    }
}
