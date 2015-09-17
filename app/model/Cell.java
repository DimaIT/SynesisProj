package model;

import org.springframework.util.CollectionUtils;
import util.Utils;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.util.Arrays;
import java.util.List;

@Entity
public class Cell extends Base{
    @ManyToOne
    private Field field;
    @ManyToOne
    private Record record;
    private String value;
    @ElementCollection
    private List<String> additionalValues;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Cell cell = (Cell) o;

        if (field != null ? !field.equals(cell.field) : cell.field != null) return false;
        if (value != null ? !value.equals(cell.value) : cell.value != null) return false;
        return !(additionalValues != null ? !additionalValues.equals(cell.additionalValues) : cell.additionalValues != null);
    }

    @Override
    public int hashCode() {
        int result = field != null ? field.hashCode() : 0;
        result = 31 * result + (value != null ? value.hashCode() : 0);
        result = 31 * result + (additionalValues != null ? additionalValues.hashCode() : 0);
        return result;
    }

    public String value() {
        return CollectionUtils.isEmpty(additionalValues) ? value : value + ", " + Utils.mkString(additionalValues, ", ");
    }

    public Cell() {
    }

    public Cell(Field field, String... values) {
        this.field = field;
        if (values.length > 0) {
            this.value = values[0];
            if (values.length > 1)
                additionalValues = Arrays.asList(Arrays.copyOfRange(values, 1, values.length));
        }
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

    public List<String> getAdditionalValues() {
        return additionalValues;
    }

    public void setAdditionalValues(List<String> values) {
        this.additionalValues = values;
    }
}
