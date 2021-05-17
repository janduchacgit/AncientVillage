package cz.ancient.AncientVillage.db;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cz.ancient.AncientVillage.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findById(long id);

    User findByPlayerName(String playerName);

//    List<User> findAll();
    
    /*
    @Update("UPDATE user SET enabled = true WHERE user_id = #{id}")
   int activateUser(@Param("id") Long id);

   @Update("UPDATE user SET empire_id = #{empire.id}  WHERE user_id = #{id}")
   int setEmpire(@Param("empire") Empire empire, @Param("id") Long id);
     */
}
