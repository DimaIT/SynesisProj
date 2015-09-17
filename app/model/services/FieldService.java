package model.services;

import model.Field;
import model.modelUtil.FieldType;
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
        Field field = bindFromRequest();
        if (id.equals(0L)) return crud.save(field); //add
        field.setId(id);
        return crud.save(field); //edit
    }

    private static Field bindFromRequest() {
        Map<String, String> data = Form.form().bindFromRequest().data();

        String label = data.get("label");
        boolean required = Objects.equals(data.get("required"), "on");
        boolean active = Objects.equals(data.get("active"), "on");
        FieldType type = FieldType.valueOf(data.get("type"));
        List<String> vars = null;

        if (typesWithVars.contains(type))
            vars = getVars(data);

        return new Field(label, required, active, type, vars);
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
