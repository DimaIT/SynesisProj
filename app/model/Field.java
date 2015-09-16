package model;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import java.util.LinkedList;
import java.util.List;

@Entity
public class Field extends Base {
    enum FieldType {
        LineText,
        TextArea,
        Radiobutton,
        Select,
        Checkbox;
    }

    private String label;
    private Boolean required;
    private Boolean active;
    private FieldType type;
    @ElementCollection
    private List<String> variants = new LinkedList<>();
}
