package model;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.Date;
import java.util.Optional;
import java.util.Set;

@Entity
public class Record extends Base{
    private Date created;
    @OneToMany(mappedBy = "record")
    private Set<Cell> cells;

    public Optional<Cell> findCellByField(Field field) {
        return cells.stream().filter(cell -> cell.getField().equals(field)).findAny();
    }

    public Record() {
    }

    public Record(Date created) {
        this.created = created;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date updated) {
        this.created = updated;
    }

    public Set<Cell> getCells() {
        return cells;
    }

    public void setCells(Set<Cell> cells) {
        this.cells = cells;
    }
}
