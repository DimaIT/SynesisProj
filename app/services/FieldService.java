package services;

import com.google.inject.ImplementedBy;
import model.Field;
import services.impl.FieldServiceImpl;

import java.util.List;
import java.util.Map;

/**
 * @author D.Tolpekin
 */
@ImplementedBy(FieldServiceImpl.class)
public interface FieldService {
    List<Field> getActualFields();

    String saveFromRequest(Long id);

    Field bindFromRequest(Field field);

    List<String> getVars(Map<String, String> data);
}
