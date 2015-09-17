package model;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.Date;
import java.util.Optional;
import java.util.Set;

@Entity
public class Record extends Base{
    private Date updated;
    @OneToMany
    private Set<Cell> cells;

    public Optional<Cell> findCellByField(Field field) {
        return cells.stream().filter(cell -> cell.getField().equals(field)).findAny();
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    public Set<Cell> getCells() {
        return cells;
    }

    public void setCells(Set<Cell> cells) {
        this.cells = cells;
    }
}
