package model.services;


import model.Base;

import java.util.List;

import static play.db.jpa.JPA.em;

public class CrudService<T extends Base> {

    private Class<T> type;

    @SuppressWarnings("unchecked")
    public CrudService(Class<? extends T> clazz) {
        type = (Class<T>) clazz;
    }

    public T findOne(Long id) {
        return em().find(type, id);
    }

    public List<T> findAll() {
        return em().createQuery("SELECT a FROM " + type.getSimpleName() + " a", type).getResultList();
    }

    public void delete(Long id) {
        em().remove(em().getReference(type, id));
    }

    public T save(T entity) {
        return em().merge(entity);
    }
}
