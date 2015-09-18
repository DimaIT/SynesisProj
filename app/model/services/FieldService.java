package model.services;

import model.Field;
import model.FieldType;
import play.data.Form;

import java.util.*;
import java.util.stream.Collectors;

public class FieldService {

    private static EnumSet<FieldType> typesWithVars = EnumSet.of(FieldType.Radiobutton, FieldType.Select);

    private static CrudService<Field> crud = new CrudService<>(Field.class);

    public static CrudService<Field> crud() {
        return crud;
    }

    public static List<Field> getActualFields() {
        return crud.findAll().stream().filter(Field::getActive).collect(Collectors.toList());
    }

    public static Field saveFromRequest(Long id) {
        Field field;
        if (id.equals(0L))
            field = new Field();
        else
            field = FieldService.crud.findOne(id);
        return crud.save(bindFromRequest(field));
    }

    private static Field bindFromRequest(Field field) {
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

    private static List<String> getVars(Map<String, String> data) {
        int i = 0;
        String var;
        List<String> list = new ArrayList<>();
        while ((var = data.get("var" + i++)) != null)
            list.add(var);
        return list;
    }
}
