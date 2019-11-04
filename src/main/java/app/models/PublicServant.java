package app.models;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class PublicServant {

    private String id;
    private String name;

    private List<Ordinance> ordinances = new ArrayList<>();

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

    public Iterator<Ordinance> getOrdinances() {
        return ordinances.iterator();
    }

    public void addOrdinance(Ordinance ordinance) {
        ordinance.addPublicServant(this);
        this.ordinances.add(ordinance);
    }

    public double getTotalWorkload() {
        return 0.0;
    }

}
