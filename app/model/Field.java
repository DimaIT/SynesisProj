package model;

import util.tableUtil.ColumnSettings;
import util.tableUtil.TableSettings;

import javax.persistence.CascadeType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.LinkedList;
import java.util.List;

@Entity
@TableSettings("table.fields")
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
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Cell> cells;

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

    public List<Cell> getCells() {
        return cells;
    }

    public void setCells(List<Cell> cells) {
        this.cells = cells;
    }
}
