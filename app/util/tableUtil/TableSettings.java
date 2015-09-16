package util.tableUtil;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation used by TableService for getting info about table
 *
 * value() - caption of the table, will be represented as jsp title
 * keyField() - name of class field which will represent object in TableRepresentation
 *              in TableInput select input creates automatically by this field
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface TableSettings {
    String value() default "";
    String keyField() default "";
}
