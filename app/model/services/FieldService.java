package model.services;

import model.Field;

import java.util.List;
import java.util.stream.Collectors;

public class FieldService {

    private static CrudService<Field> crud = new CrudService<>(Field.class);

    public static CrudService<Field> crud() {
        return crud;
    }

    public static List<Field> getActualFields() {
        return crud.findAll().stream().filter(Field::getActive).collect(Collectors.toList());
    }
}
