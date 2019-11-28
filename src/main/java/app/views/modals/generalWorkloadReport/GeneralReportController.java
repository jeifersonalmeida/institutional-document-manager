package app.views.modals.generalWorkloadReport;

import app.models.PublicServant.PublicServant;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
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

    private ObservableList<PublicServant> servants = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        tableColumnName.setCellValueFactory(new PropertyValueFactory<>("name"));
        tableColumnWorkload.setCellValueFactory(new PropertyValueFactory<>("totalWorkload"));
    }

    public void setServants(ObservableList<PublicServant> servants) {
        this.servants = servants;
        this.tableViewServants.setItems(servants);
    }
}
