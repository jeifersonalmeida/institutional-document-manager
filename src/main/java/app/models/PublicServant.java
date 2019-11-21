package app.models;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class PublicServant {

    @Id
    private String id;
    private String name;

    public PublicServant(String id, String name) {
        this.setId(id);
        this.setName(name);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
