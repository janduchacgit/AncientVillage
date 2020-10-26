package cz.ancient.AncientVillage.db;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmpireRepository extends JpaRepository<Empire, Long> {
}
