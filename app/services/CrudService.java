package services;

import model.Base;
import model.Field;
import model.Record;

import java.util.List;

import static play.db.jpa.JPA.em;

/**
 * This service implement CRUD logic for any @Entity
 * @param <T> - processed type
 */
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
        T t = em().merge(entity);
        em().flush();
        em().getTransaction().commit();
        em().getTransaction().begin();
        return t;
    }

    /**
     * clean up method
     */
    public static void deleteAll() {
        new CrudService<>(Record.class).findAll().forEach(r -> em().remove(r));
        new CrudService<>(Field.class).findAll().forEach(f -> em().remove(f));
    }
}
