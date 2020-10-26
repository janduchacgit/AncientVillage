package cz.ancient.AncientVillage.db;

import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Empire - db entity
 *
 * @author Jan Duchac [jan.duchac@intelis.cz]
 */
@Getter
@Entity
public class Empire {
    
    @Id
    private long id;
    
    private String name;
}
