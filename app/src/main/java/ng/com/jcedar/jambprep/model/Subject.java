package ng.com.jcedar.jambprep.model;

/**
 * Created by oluwafemi.bamisaye on 2/13/2016.
 */
public class Subject {
    private String name;
    private Boolean box;
    private int id;


    public Subject(String name, boolean box, int id){
        this.name = name;
        this.box = box;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getBox() {
        return box;
    }

    public void setBox(Boolean box) {
        this.box = box;
    }
}
