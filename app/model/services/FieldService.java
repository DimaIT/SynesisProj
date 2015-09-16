package model.services;

import model.Field;

public class FieldService {

    private static CrudService<Field> crud = new CrudService<>(Field.class);

    public static CrudService<Field> crud() {
        return crud;
    }

}
