package app.models.PublicServant;

import app.models.Ordinance.Ordinance;
import app.models.Ordinance.OrdinanceDAO;

import javax.persistence.*;
import java.util.List;

@Entity
public class PublicServant {

    @Id
    @GeneratedValue
    private long id;

    @Column
    private String record;

    @Column
    private String name;

    public PublicServant(String record, String name) {
        this.setRecord(record);
        this.setName(name);
    }

    public PublicServant() {

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getRecord() {
        return record;
    }

    public void setRecord(String id) {
        this.record = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getTotalWorkload() {
        List<Ordinance> ordinances = new OrdinanceDAO().findAll();

        double total = 0.0;

        for (Ordinance ordinance : ordinances) {
            total += ordinance.getWorkload();
        }

        return total;
    }

    @Override
    public String toString() {
        return getName();
    }
}
