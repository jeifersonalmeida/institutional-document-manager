package app.models.PublicServant;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class PublicServant {

    @Id
    @GeneratedValue
    private long id;
    private String record;
    private String name;

    public PublicServant(String id, String name) {
        this.setRecord(id);
        this.setName(name);
    }

    public PublicServant() {

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
