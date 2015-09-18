package model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class Cell extends Base {
    @ManyToOne
    private Field field;
    @ManyToOne
    private Record record;
    private String value;

    public Cell() {
    }

    public Cell(Field field, Record record, String value) {
        this.field = field;
        this.record = record;
        this.value = value;
    }

    public Field getField() {
        return field;
    }

    public void setField(Field field) {
        this.field = field;
    }

    public Record getRecord() {
        return record;
    }

    public void setRecord(Record record) {
        this.record = record;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
