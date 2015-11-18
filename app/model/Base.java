package model;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.util.UUID;

/**
 * Base entity class with Long id
 */
@MappedSuperclass
public abstract class Base {

    @Id
    @org.hibernate.annotations.Type(type="pg-uuid")
    private UUID uuid = UUID.randomUUID();

    public String getUuid() {
        return uuid != null ? uuid.toString() : null;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid == null ? null : UUID.fromString(uuid);
    }
}
