package app.models;

public class PublicServant {

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
