package util.tableUtil.tableServices;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.Reflect;
import util.tableUtil.ColumnSettings;
import util.tableUtil.Record;
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
     * from @...Info annotations
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
     * Creating Table;
     * setting header;
     * adding all rows by CrudRepository.findAll() method
     *
     * @return filled table
     */
    //todo fix
//    TableRepresentation createFullTable(@Nonnull Class<?> entity) {
//        TableRepresentation table = createEmptyTable(entity);
//        return coreService.crudByClass(entity).map(repo -> fillTable(entity, table, repo.findAll())).orElse(null);
//    }
    public static TableRepresentation createEmptyTable(@Nonnull Class<?> entity) {
        TableRepresentation table = new TableRepresentation();
        setTableHeader(entity, table);
        table.setMessage("Таблица " + table.getName());
        return table;
    }

    /**
     * Filling table with rows from list;
     * items from list are converting by "getEntityRecord()" method if entity implements AutoDisplayedTable
     * or by getting & converting to String represented fields of each item
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
            Record record;
            for (Object item : list) {
                record = getRecord(getters, item);
                if (record != null) {
                    table.addRecord(record);
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
        return  fillTable(entity, table, list);
    }

    /**
     * Creating record by object and it's getters
     *
     * @param getters of represented fields of entity
     * @param entity  object
     * @return filled Record or null if couldn't get an id
     */
    private static Record getRecord(Iterable<Method> getters, Object entity) {
        Record record = new Record();
        try {
            Long id = (Long) entity.getClass().getMethod("getId").invoke(entity);
            record.setId(id);
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
                record.addField(field.toString());
            } catch (Exception e) {
                record.addField("");
            }
        return record;
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
