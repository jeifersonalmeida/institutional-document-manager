package app.views.screens.servants;

import app.views.modals.newDocuments.addServant.AddServantController;
import app.models.PublicServant;
import app.views.screens.generalWorkloadReport.GeneralReportController;
import app.views.screens.singleWorkloadReport.SingleReportController;
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

    private String addServantPath = "/views/modals/addServant/AddServantModal.fxml";

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
        FXMLLoader loader = new FXMLLoader(getClass().getResource(addServantPath));
        Parent root = loader.load();

        AddServantController controller = loader.getController();
        controller.setParentController(this);
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
            controller.setParentController(this);

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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/modals/singleWorkloadReport/SingleReport.fxml"));
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
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/modals/generalWorkloadReport/GeneralReport.fxml"));
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

    public void refreshList(){
        this.tableViewServants.refresh();
    }

    public void addPublicServant(PublicServant publicServant){
        this.publicServants.add(publicServant);
        this.refreshList();
    }

    public void removePublicServant(PublicServant publicServant){
        this.publicServants.remove(publicServant);
        this.refreshList();
    }

    public void removePublicServant(String id){
        this.publicServants.removeIf(p -> p.getId().equals(id));
        this.refreshList();
    }

    @FXML
    public void filter(){
        String filterString = this.textFieldSearchbar.getText();
        System.out.println(filterString);

        if(!filterString.equals("")){
            this.tableViewServants.setItems(this.publicServants.filtered(p-> p.getId().contains(filterString) || p.getName().contains(filterString)));
            this.refreshList();
            return;
        }
        this.tableViewServants.setItems(this.publicServants);
    }
}
