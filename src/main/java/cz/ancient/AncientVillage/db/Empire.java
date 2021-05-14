package cz.ancient.AncientVillage.db;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Empire - db entity
 *
 * @author Jan Duchac [jan.duchac@intelis.cz]
 */
@Entity
public class Empire {
    
    @Id
    @GeneratedValue
    private long id;
    
    private String name;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
