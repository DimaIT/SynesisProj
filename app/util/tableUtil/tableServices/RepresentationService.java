package util.tableUtil.tableServices;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.Reflect;
import util.tableUtil.ColumnSettings;
import util.tableUtil.TableRecord;
import util.tableUtil.tables.TableRepresentation;

import javax.annotation.Nonnull;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;

import static util.Reflect.getAllFields;
import static util.Reflect.getGetter;
import static util.tableUtil.tableServices.CoreService.fieldCaption;
import static util.tableUtil.tableServices.CoreService.setTableNames;

public final class RepresentationService {

    private static final Logger logger = LoggerFactory.getLogger(RepresentationService.class);

    /**
     * Getting table names & column names
     * from @...Settings annotations
     * Put them into TableRepresentation
     *
     * @return table with header or null if error ocures
     */
    public static TableRepresentation setTableHeader(@Nonnull Class<?> entity, TableRepresentation table) {
        int colCount = 0;
        setTableNames(entity, table);
        for (Field field : getAllFields(entity)) {
            if (field.getAnnotation(ColumnSettings.class) != null && field.getAnnotation(ColumnSettings.class).represented()) {
                table.addHeaderAttribute(fieldCaption(field));
                colCount++;
            }
        }
        table.setColumnsCount(colCount);
        return table;
    }

    /**
     * Filling table with rows from list;
     * items from list are converted to TableRecords with list of Strings which represent fields of each item
     *
     * @param entity class
     * @param table  in which we put records
     * @param list   of entities
     * @return table filled with records
     */
    public static TableRepresentation fillTable(@Nonnull Class<?> entity, @Nonnull TableRepresentation table, Iterable list) {
        long start = System.currentTimeMillis();
        if (list != null) {
            Iterable<Method> getters = getRepresentedGetters(entity);
            TableRecord tableRecord;
            for (Object item : list) {
                tableRecord = getRecord(getters, item);
                if (tableRecord != null) {
                    table.addRecord(tableRecord);
                }
            }
            logger.info("TableRepresentation for class " + entity.getSimpleName() + " created in "
                    + (System.currentTimeMillis() - start) + "ms");
        }
        return table;
    }

    public static TableRepresentation fillTable(@Nonnull Class<?> entity, Iterable list) {
        TableRepresentation table = new TableRepresentation();
        setTableHeader(entity, table);
        table.setMessage(table.getName());
        return  fillTable(entity, table, list);
    }

    /**
     * Creating record by object and it's getters
     *
     * @param getters of represented fields of entity
     * @param entity  object
     * @return filled TableRecord or null if couldn't get an id
     */
    private static TableRecord getRecord(Iterable<Method> getters, Object entity) {
        TableRecord tableRecord = new TableRecord();
        try {
            String id = (String) entity.getClass().getMethod("getUuid").invoke(entity);
            tableRecord.setUuid(id);
        } catch (Exception e) {
            try {
                logger.error("Caught exception(" + e.getMessage() + ") while getting id of " + entity);
            } catch (NullPointerException ex) {
                return null;
            }
        }

        for (Method getter : getters)
            try {
                Object field = getter.invoke(entity);
                tableRecord.addField(field.toString());
            } catch (Exception e) {
                tableRecord.addField("");
            }
        return tableRecord;
    }

    /**
     * Getting getters for each represented field and adding it to list of getters
     *
     * @param entity class
     * @return list of entity's getters
     */
    private static Iterable<Method> getRepresentedGetters(Class<?> entity) {
        List<Method> list = new LinkedList<>();
        getAllFields(entity).stream()
                .filter(field -> field.getAnnotation(ColumnSettings.class) != null)
                .forEach(field -> {
                    if (field.getAnnotation(ColumnSettings.class).represented()) {
                        try {
                            Method getter = getGetter(entity, field);
                            list.add(getter);
                        } catch (Exception e) {
                            logger.error("No getter method for annotated field: " + field.getName());
                            Reflect.errorLog(RepresentationService.class, e);
                            list.add(null);  //null pointer exception will be caught in "getRecord" method
                        }
                    }
                });
        return list;
    }
}
