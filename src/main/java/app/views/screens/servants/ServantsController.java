package app.views.screens.servants;

import app.models.PublicServant.PublicServant;
import app.models.PublicServant.PublicServantDAO;
import app.views.modals.addServant.AddServantController;
import app.views.modals.generalWorkloadReport.GeneralReportController;
import app.views.modals.singleWorkloadReport.SingleReportController;
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
    private ObservableList<PublicServant> publicServants = FXCollections.observableArrayList(new PublicServantDAO().findAll());

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

    @FXML
    private void initialize() {
        fill();
    }

    @FXML
    private void fill() {
        tableColumnRecord.setCellValueFactory(new PropertyValueFactory<>("record"));
        tableColumnName.setCellValueFactory(new PropertyValueFactory<>("name"));

        tableViewServants.setItems(publicServants);
        tableViewServants.setPlaceholder(new Label("Nenhum servidor registrado"));

    }

    @FXML
    public void buttonAddNew_click() throws IOException {
        Stage dialog = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/modals/addServant/AddServantModal.fxml"));
        Parent root = loader.load();

        AddServantController controller = loader.getController();
        controller.setParentController(this);
        dialog.setTitle("Adição de servidor");
        dialog.setScene(new Scene(root));
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.setResizable(false);
        dialog.showAndWait();
    }

    @FXML
    public void buttonEdit_click() throws IOException {
        PublicServant servant = tableViewServants.getSelectionModel().getSelectedItem();

        if (servant != null) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/modals/addServant/AddServantModal.fxml"));
            Parent root = loader.load();

            AddServantController controller = loader.getController();
            controller.setServant(servant);
            controller.setParentController(this);

            Stage dialog = new Stage();
            dialog.setTitle(servant.getName());
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
            dialog.setTitle(servant.getName());
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
        Stage dialog = new Stage();
        dialog.setTitle("Relatório de carga horária");
        Scene scene = new Scene(root);
        dialog.setScene(scene);
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.setResizable(false);
        dialog.showAndWait();
    }

    public void fillList(){
        PublicServantDAO dao = new PublicServantDAO();
        publicServants = FXCollections.observableArrayList(dao.findAll());

        tableViewServants.setItems(publicServants);

        this.tableViewServants.refresh();
    }

    @FXML
    public void filter(){
        String filterString = this.textFieldSearchbar.getText();
//        System.out.println(filterString);

        if(!filterString.equals("")){
            this.tableViewServants.setItems(this.publicServants.filtered(p-> p.getRecord().contains(filterString) || p.getName().contains(filterString)));
            this.tableViewServants.refresh();

            return;
        }
        this.tableViewServants.setItems(this.publicServants);
        this.tableViewServants.refresh();

    }
}
