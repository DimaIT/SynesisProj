package model.services;

import model.Cell;
import model.Field;
import model.FieldType;
import model.Record;
import play.data.Form;
import play.db.jpa.JPA;
import play.i18n.Messages;
import util.Reflect;
import util.tableUtil.tables.TableRepresentation;

import java.util.*;
import java.util.stream.Collectors;

public class ResponseService {

    public static final CrudService<Record> crud = new CrudService<>(Record.class);

    public static boolean saveResponse() {
        Record newRecord = new Record(new Date());
        try {
            JPA.em().persist(newRecord);
            Record record = bindFromRequest(newRecord);
            record.getCells().forEach(cell -> JPA.em().persist(cell));
            return true;
        } catch (Exception e) {
            Reflect.errorLog(ResponseService.class, e);
            return false;
        }
    }

    public static Record bindFromRequest(Record record) {
        Map<String, String> data = Form.form().bindFromRequest().data();
        List<Field> fields = FieldService.getActualFields();

        Set<Cell> cells = fields.stream()
                .map(field -> createCell(record, field, data.get(field.getLabel())))
                .collect(Collectors.toSet());

        record.setCells(cells);
        return record;
    }

    protected static Cell createCell(Record record, Field field, String value) {
        if (field.getType().equals(FieldType.Checkbox))
            value = value != null && value.equals("on") ? Messages.get("true") : Messages.get("false");
        return new Cell(field, record, value);
    }

    private static void persistCells(Collection<Cell> list) {
        list.forEach(cell -> JPA.em().persist(cell));
    }


    public static TableRepresentation table() {
        TableRepresentation table = new TableRepresentation();
        List<Record> recordList = crud.findAll();
        List<Field> fieldList = FieldService.getActualFields();
        fillTable(table, recordList, fieldList);

        table.getProperties()
                .setEditLinkEnabled(false)
                /*.setDeleteLinkEnabled(false)*/;
        table.setMessage("table.responses");
        return table;
    }

    public static void fillTable(TableRepresentation table, List<Record> list, List<Field> fields) {
        fields.forEach(field -> table.addHeaderAttribute(field.getLabel()));
        table.setColumnsCount(fields.size());
        for (Record record : list) {
            List<String> values = new ArrayList<>();
            fields.forEach(field -> values.add(record.findCellByField(field)
                    .map(Cell::getValue)
                    .orElse(Messages.get("not.available"))));
            table.addRecord(record.getId(), values);
        }
    }
}
