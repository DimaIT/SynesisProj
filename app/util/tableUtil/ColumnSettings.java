package util.tableUtil;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation used by TableService for getting info about columns
 * empty annotation says that column should be represented in TableRepresentation
 *
 * value() - caption of the column (if empty column.caption = field.name)
 * represented() - if false column will not be shown to user
 * editable() - if false user will not be able to fill or edit value of this column
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ColumnSettings {
    String value() default "";
    boolean represented() default true;
    boolean editable() default true;
    boolean required() default false;
    boolean readonly() default false;
    boolean hidden() default false;
}
