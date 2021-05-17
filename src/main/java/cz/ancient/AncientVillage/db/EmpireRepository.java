package cz.ancient.AncientVillage.db;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cz.ancient.AncientVillage.model.Empire;

@Repository
public interface EmpireRepository extends JpaRepository<Empire, Long> {

    Empire findById(long id);
}
