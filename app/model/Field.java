package model;

import util.tableUtil.ColumnSettings;
import util.tableUtil.TableSettings;

import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;

/**
 * Field entity
 */
@Entity
@TableSettings("table.fields")
public class Field extends Base {

    @ColumnSettings
    @Column(unique = true)
    private String label = "";
    @ColumnSettings
    private Boolean required = false;
    @ColumnSettings
    private Boolean active = true;
    @ColumnSettings
    private FieldType type = FieldType.LineText;
    @ElementCollection
    private List<String> variants = new LinkedList<>();
    @OneToMany(mappedBy = "field",cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Cell> cells;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Field field = (Field) o;
        if (label != null ? !label.equals(field.label) : field.label != null) return false;
        return type == field.type;

    }

    @Override
    public int hashCode() {
        int result = label != null ? label.hashCode() : 0;
        result = 31 * result + (type != null ? type.hashCode() : 0);
        return result;
    }

    public Field() {
    }

    public Field(String label, Boolean required, Boolean active, FieldType type, List<String> variants) {
        this.label = label;
        this.required = required;
        this.active = active;
        this.type = type;
        this.variants = variants;
    }

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
