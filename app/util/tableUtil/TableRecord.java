package util.tableUtil;

import java.util.ArrayList;
import java.util.List;

public class TableRecord {
    protected List<String> columns;
    protected String uuid;
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

    public TableRecord(String uuid, List<String> columns) {
        this.columns = columns;
        this.uuid = uuid;
    }

    public List<String> getColumns() {
        return columns;
    }

    public void setColumns(List<String> columns) {
        this.columns = columns;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
