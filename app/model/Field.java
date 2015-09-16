package model;

import util.tableUtil.ColumnSettings;

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

    @ColumnSettings
    private String label;
    @ColumnSettings
    private Boolean required;
    @ColumnSettings
    private Boolean active;
    @ColumnSettings
    private FieldType type;
    @ElementCollection
    private List<String> variants = new LinkedList<>();

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Boolean getRequired() {
        return required;
    }

    public void setRequired(Boolean required) {
        this.required = required;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public FieldType getType() {
        return type;
    }

    public void setType(FieldType type) {
        this.type = type;
    }

    public List<String> getVariants() {
        return variants;
    }

    public void setVariants(List<String> variants) {
        this.variants = variants;
    }
}
