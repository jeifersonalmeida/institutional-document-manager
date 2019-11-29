package app.models.PublicServant;

import javax.persistence.*;

@Entity
public class PublicServant {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
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
        return 0.0;
    }

}
