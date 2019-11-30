package app.views.modals.generalWorkloadReport;

import app.models.PublicServant.PublicServant;
import app.models.PublicServant.PublicServantDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class GeneralReportController {
    @FXML
    private TableView<PublicServant> tableViewServants;

    @FXML
    private TableColumn<PublicServant, String> tableColumnName;

    @FXML
    private TableColumn<Double, String> tableColumnWorkload;

    @FXML
    private Button buttonPrint;

    @FXML
    private void initialize() {
        tableColumnName.setCellValueFactory(new PropertyValueFactory<>("name"));
        tableColumnWorkload.setCellValueFactory(new PropertyValueFactory<>("totalWorkload"));
        tableViewServants.setPlaceholder(new Label("Nenhum servidor registrado"));
        this.tableViewServants.setItems(FXCollections.observableArrayList(new PublicServantDAO().findAll()));
    }
}
