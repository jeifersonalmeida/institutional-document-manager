package app.views.modals.singleWorkloadReport;

import app.models.Ordinance.Ordinance;
import app.models.Ordinance.OrdinanceType;
import app.models.PublicServant.PublicServant;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.Calendar;

public class SingleReportController {
    @FXML
    private TextField textFieldName, textFieldRecord, textFieldTotal;

    @FXML
    private TableView<Ordinance> tableViewOrdinances;

    @FXML
    private TableColumn<Ordinance, String> tableColumnOrdinance, tableColumnWorkload;

    @FXML
    private Button buttonPrint;

    private ObservableList<Ordinance> ordinances = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        fill();
    }

    private ObservableList<Ordinance> loadValues() {
//        ordinances.add(new Ordinance(Calendar.getInstance().getTime(), Calendar.getInstance().getTime(), 1.0, OrdinanceType.ORDINANCE, "PORTARIA Nº SCL.0001/2019, DE 07 DE JANEIRO DE 2019.\n" +
//                "Dispõe sobre substituição regulamentar de servidor - DAA"));
//        ordinances.add(new Ordinance(Calendar.getInstance().getTime(), Calendar.getInstance().getTime(), 4.0, OrdinanceType.ORDINANCE, "PORTARIA Nº SCL.0002/2019, DE 07 DE JANEIRO DE 2019.\n" +
//                "Dispõe sobre substituição regulamentar de servidor - DRG"));
//        ordinances.add(new Ordinance(Calendar.getInstance().getTime(), Calendar.getInstance().getTime(), 20.0, OrdinanceType.ORDINANCE, "PORTARIA Nº SCL.0003/2019, DE 10 DE JANEIRO DE 2019\n" +
//                "Dispõe sobre substituição regulamentar de servidor - DAA"));
//        ordinances.add(new Ordinance(Calendar.getInstance().getTime(), Calendar.getInstance().getTime(), 35.0, OrdinanceType.ORDINANCE, "PORTARIA Nº SCL.0004/2019, DE 10 DE JANEIRO DE 2019\n" +
//                "Designa a comissão de matrícula para o ano de 2019 do Cãmpus São Carlos do IFSP"));
//        ordinances.add(new Ordinance(Calendar.getInstance().getTime(), Calendar.getInstance().getTime(), 17.5, OrdinanceType.ORDINANCE, "PORTARIA Nº SCL.0005/2019, DE 31 DE JANEIRO DE 2019\n" +
//                "Altera a Portaria 04/2019, que designa a comissão de matrícula para o ano de 2019 do Cãmpus São Carlos do IFSP"));
//        ordinances.add(new Ordinance(Calendar.getInstance().getTime(), Calendar.getInstance().getTime(), 13.0, OrdinanceType.ORDINANCE, "PORTARIA Nº SCL.0006/2019, DE 01 DE FEVEREIRO DE 2019\n" +
//                "Dispõe sobre substituição regulamentar de servidor - CAE"));
//        ordinances.add(new Ordinance(Calendar.getInstance().getTime(), Calendar.getInstance().getTime(), 2.0, OrdinanceType.ORDINANCE, "PORTARIA Nº SCL.0014/2019, DE 22 DE FEVEREIRO DE 2019\n" +
//                "Dispõe sobre substituição regulamentar de servidor - CAE"));
//        ordinances.add(new Ordinance(Calendar.getInstance().getTime(), Calendar.getInstance().getTime(), 12.0, OrdinanceType.ORDINANCE, "PORTARIA Nº SCL.0015/2019, DE 25 DE FEVEREIRO DE 2019\n" +
//                "Dispõe sobre substituição regulamentar de servidor - DRG"));

        return ordinances;
    }

    private void fill() {
        tableColumnOrdinance.setCellValueFactory(new PropertyValueFactory<>("subject"));
        tableColumnWorkload.setCellValueFactory(new PropertyValueFactory<>("workload"));

        tableViewOrdinances.setItems(loadValues());
        tableViewOrdinances.setPlaceholder(new Label("No Ordinances registered"));

    }

    public void setServant(PublicServant servant) {
        textFieldRecord.setText(servant.getRecord());
        textFieldName.setText(servant.getName());
    }
}
