package model.services;

import model.Cell;
import model.Field;
import model.FieldType;
import model.Record;
import play.data.Form;
import play.db.jpa.JPA;
import play.i18n.Messages;
import util.Reflect;
import util.tableUtil.TableRecord;
import util.tableUtil.tables.TableRepresentation;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Response processing service
 * - save response
 * - track current recordsCount
 * - creates response table
 */
public class ResponseService {

    public static final CrudService<Record> crud = new CrudService<>(Record.class);
    private static long recordsCount = 0;

    public static Long countRecords() {
        try {
            return (recordsCount = JPA.em().createQuery("Select count(*) from Record", Long.class).getSingleResult());
        } catch (Exception e) {
            return recordsCount;
        }
    }

    public static boolean saveResponse() {
        Record newRecord = new Record(new Date());
        try {
            JPA.em().persist(newRecord);
            Record record = bindFromRequest(newRecord);
            record.getCells().forEach(cell -> JPA.em().persist(cell));
            ResponseUpdaterService.updateAll(record);
            return true;
        } catch (Exception e) {
            Reflect.errorLog(ResponseService.class, e);
            return false;
        }
    }

    private static Record bindFromRequest(Record record) {
        Map<String, String> data = Form.form().bindFromRequest().data();
        List<Field> fields = FieldService.getActualFields();

        Set<Cell> cells = fields.stream()
                .map(field -> createCell(record, field, data.get(field.getLabel())))
                .collect(Collectors.toSet());

        record.setCells(cells);
        return record;
    }

    private static Cell createCell(Record record, Field field, String value) {
        if (field.getType().equals(FieldType.Checkbox))
            value = value != null && value.equals("on") ? Messages.get("true") : Messages.get("false");
        return new Cell(field, record, value);
    }

    public static TableRepresentation table() {
        TableRepresentation table = new TableRepresentation();
        List<Record> recordList = crud.findAll();
        recordsCount = recordList.size();
        List<Field> fieldList = FieldService.getActualFields();
        fillTable(table, recordList, fieldList);

        table.getProperties()
                .setEditLinkEnabled(false)
                .setDeleteLinkEnabled(false);
        table.setMessage("table.responses");
        return table;
    }

    private static void fillTable(TableRepresentation table, List<Record> list, List<Field> fields) {
        fields.forEach(field -> table.addHeaderAttribute(field.getLabel()));
        table.setColumnsCount(fields.size());
        for (Record record : list) {
            table.addRecord(createRecord(fields, record));
        }
    }

    private static TableRecord createRecord(List<Field> fields, Record record) {
        List<String> values = new ArrayList<>();
        fields.forEach(field -> values.add(record.findCellByField(field)
                .map(Cell::getValue)
                .orElse(Messages.get("not.available"))));
        return new TableRecord(record.getId(), values);
    }

}
