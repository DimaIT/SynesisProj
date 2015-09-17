package model.services;

import model.Cell;
import model.Field;
import model.Record;
import play.i18n.Messages;
import util.tableUtil.tables.TableRepresentation;

import java.util.ArrayList;
import java.util.List;

public class ResponseService {

    private static CrudService<Record> crud = new CrudService<>(Record.class);

    public static TableRepresentation table() {
        TableRepresentation table = new TableRepresentation();
        List<Record> recordList = crud.findAll();
        List<Field> fieldList = FieldService.getActualFields();
        fillTable(table, recordList, fieldList);

        table.getProperties()
                .setEditLinkEnabled(false)
                .setDeleteLinkEnabled(false);
        table.setMessage("table.responses");
        return new TableRepresentation();
    }

    public static void fillTable(TableRepresentation table, List<Record> list, List<Field> fields) {
        fields.forEach(field -> table.addHeaderAttribute(field.getLabel()));
        table.setColumnsCount(fields.size());
        for (Record record: list) {
            List<String> values = new ArrayList<>();
            fields.forEach(field -> values.add(record.findCellByField(field)
                    .map(Cell::value)
                    .orElse(Messages.get("not.available"))));
            table.addRecord(0L, values);
        }
    }
}
