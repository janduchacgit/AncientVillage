package cz.ancient.AncientVillage.db;

import org.springframework.data.jpa.repository.JpaRepository;

import cz.ancient.AncientVillage.model.UserActivation;

public interface UserActivationRepository extends JpaRepository<UserActivation, Long> {

   UserActivation findByToken(String token);
   
//   @Insert("INSERT INTO user_activation (token, valid_to, user_id) "
//         + "VALUES ("
//         + "#{token}, "
//         + "#{validTo}, "
//         + "#{user.id})")
//   int insert(UserActivation userActivation);
//
//   @Results(value = {
//         @Result(property = "token", column = "token"),
//         @Result(property = "validTo", column = "valid_to"),
//         @Result(property = "user", column = "user_id", javaType = User.class , one = @One(select = "cz.ancient.dao.mapper.UserDAO.getUser")),
//   })
//   @Select("SELECT * FROM user_activation WHERE token = #{token}")
//   UserActivation getUserActivation(@Param("token") String token);

}
