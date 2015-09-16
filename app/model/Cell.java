package model;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

@Entity
public class Cell extends Base{
    @ManyToOne
    private Field field;
    @ElementCollection
    private List<String> values = new LinkedList<>();

    public Cell() {
    }

    public Cell(Field field, String... values) {
        this.field = field;
        this.values = Arrays.asList(values);
    }
}
