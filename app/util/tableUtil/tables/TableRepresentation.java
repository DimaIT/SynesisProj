package util.tableUtil.tables;

import util.tableUtil.TableRecord;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class TableRepresentation extends Table {
    public static final String TYPE_TABLE = "table";

    private int columnsCount;
    private List<TableRecord> tableRecords = new LinkedList<>();
    private List<String> header = new ArrayList<>();
    private TableRepProperties properties = new TableRepProperties();

    public TableRepresentation() {
    }

    public void addRecord(long id, List<String> list) {
        tableRecords.add(new TableRecord(id, list));
    }

    public void addRecord(TableRecord tableRecord) {
        tableRecords.add(tableRecord);
    }

    public void addHeaderAttribute(String value) {
        header.add(value);
    }

    @Override
    public String getType() {
        return TYPE_TABLE;
    }

    public int getColumnsCount() {
        return columnsCount;
    }

    public void setColumnsCount(int columnsCount) {
        this.columnsCount = columnsCount;
    }

    public List<TableRecord> getTableRecords() {
        return tableRecords;
    }

    public void setTableRecords(List<TableRecord> tableRecords) {
        this.tableRecords = tableRecords;
    }

    public List<String> getHeader() {
        return header;
    }

    public void setHeader(List<String> header) {
        this.header = header;
    }

    public TableRepProperties getProperties() {
        return properties;
    }

    public TableRepresentation setProperties(TableRepProperties properties) {
        this.properties = properties;
        return this;
    }
}
