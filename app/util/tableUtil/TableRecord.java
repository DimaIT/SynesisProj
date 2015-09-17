package util.tableUtil;

import java.util.ArrayList;
import java.util.List;

public class TableRecord {
    protected List<String> columns;
    protected long id;
    protected String value;

    public void removeField(int index){
        columns.remove(index);
    }

    public void addField(String field){
        columns.add(field);
    }

    public TableRecord() {
        columns = new ArrayList<>();
    }

    public TableRecord(long id, List<String> columns) {
        this.columns = columns;
        this.id = id;
    }

    public List<String> getColumns() {
        return columns;
    }

    public void setColumns(List<String> columns) {
        this.columns = columns;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
