package util.tableUtil.tableServices;

import util.tableUtil.ColumnSettings;
import util.tableUtil.TableSettings;
import util.tableUtil.tables.Table;

import java.lang.reflect.Field;

public final class CoreService {

    /**
     * Getting table name & entityName
     * from @...Settings annotations
     * Put them into Table
     *
     * @return named table
     */
    public static Table setTableNames(Class<?> entity, Table table) {
        table.setName(entityCaption(entity));
        table.setEntityName(entity.getSimpleName());
        return table;
    }

    static String fieldCaption(Field field) {
        ColumnSettings columnSettings = field.getAnnotation(ColumnSettings.class);
        if (columnSettings != null && !columnSettings.value().equals(""))
            return field.getAnnotation(ColumnSettings.class).value();
        return field.getName();
    }

    public static String entityCaption(Class<?> entity) {
        TableSettings tableSettings = entity.getAnnotation(TableSettings.class);
        if (tableSettings != null && !tableSettings.value().equals(""))
            return tableSettings.value();
        return entity.getSimpleName();
    }
}
