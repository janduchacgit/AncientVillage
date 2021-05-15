package cz.ancient.AncientVillage.db.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cz.ancient.AncientVillage.db.entity.Empire;

@Repository
public interface EmpireRepository extends JpaRepository<Empire, Long> {

    List<Empire> findAll();
}
