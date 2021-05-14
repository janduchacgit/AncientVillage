package cz.ancient.AncientVillage.db;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmpireRepository extends JpaRepository<Empire, Long> {

    List<Empire> findAll();
}
