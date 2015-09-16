package model;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.Date;
import java.util.List;

@Entity
public class Record extends Base{
    private Date updated;
    @OneToMany
    private List<Cell> cells;
}
