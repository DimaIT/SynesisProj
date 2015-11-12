package services.impl;

import model.Field;
import model.FieldType;
import play.data.Form;
import play.i18n.Messages;
import services.CrudService;
import services.FieldService;
import util.Reflect;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Field processing service
 * - save fields
 * - get actual field list
 * - gives access to field crud service
 */
public class FieldServiceImpl implements FieldService {

    private EnumSet<FieldType> typesWithVars = EnumSet.of(FieldType.Radiobutton, FieldType.Select);

    public final CrudService<Field> crud = new CrudService<>(Field.class);

    @Override
    public List<Field> getActualFields() {
        return crud.findAll().stream().filter(Field::getActive).collect(Collectors.toList());
    }

    @Override
    public String saveFromRequest(Long id) {
        Field field;
        try {
            if (id.equals(0L))
                field = new Field();
            else
                field = crud.findOne(id);
            crud.save(bindFromRequest(field));
            return null;
        } catch (Exception e) {
            Reflect.errorLog(FieldServiceImpl.class, e);
            return Messages.get("fields.add.error");
        }
    }

    @Override
    public Field bindFromRequest(Field field) {
        Map<String, String> data = Form.form().bindFromRequest().data();
        FieldType type = FieldType.valueOf(data.get("type"));

        field.setLabel(data.get("label"));
        field.setRequired(Objects.equals(data.get("required"), "on"));
        field.setActive(Objects.equals(data.get("active"), "on"));
        field.setType(type);
        List<String> vars = null;

        if (typesWithVars.contains(type))
            vars = getVars(data);
        field.setVariants(vars);

        return field;
    }

    @Override
    public List<String> getVars(Map<String, String> data) {
        int i = 0;
        String var;
        List<String> list = new ArrayList<>();
        while ((var = data.get("var" + i++)) != null)
            list.add(var);
        return list;
    }
}
