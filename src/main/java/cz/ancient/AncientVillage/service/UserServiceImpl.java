package cz.ancient.AncientVillage.service;

import static cz.ancient.AncientVillage.AncientVillageApplication.encoder;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cz.ancient.AncientVillage.api.UserService;
import cz.ancient.AncientVillage.db.EmpireRepository;
import cz.ancient.AncientVillage.db.UserActivationRepository;
import cz.ancient.AncientVillage.db.UserRepository;
import cz.ancient.AncientVillage.model.User;
import cz.ancient.AncientVillage.model.UserActivation;

@Component
public class UserServiceImpl implements UserService {

   private static final int EXPIRATION_DAY = 1; // TODO to properties

   //   @Autowired
   //   private SqlSessionTemplate sqlTemplate;

   @Autowired
   private UserRepository userRepository;

   @Autowired
   private EmpireRepository empireRepository;

   @Autowired
   private UserActivationRepository userActivationRepository;

//   @Autowired
//   private UserDataService userDataService;

   @Override
   public User getUser(final long id) { // TODO je potreba?

      System.out.println("do User sql");

      return userRepository.findById(id);
   }

   @Override
   public User getUser(final String playerName) { // TODO je potreba?

      System.out.println("getUser playerName: " + playerName);

      return userRepository.findByPlayerName(playerName);
   }
   
   @Override
   public boolean registerUser(final String playerName, final String email, final String password, final String contextPath) {
      final User user = userRepository.save(new User(playerName, email, encoder.encode(password)));

      System.out.println("new user: " + user);
      final String token = UUID.randomUUID().toString();

      userActivationRepository.save(new UserActivation(token, calculateExpiryDate().getTime(), user));


      final UserActivation userActivation = userActivationRepository.findByToken(token);
      System.out.println("userActivation: " + userActivation.toString());

      // send email TODO
      final String link = contextPath + "?token=" + token + "&email=" + email;
      System.out.println("link: " + link);

      return true;
   }

   @Override
   public User activateUser(final String token, final String email) {
      final UserActivation userActivation = userActivationRepository.findByToken(token);

      if (userActivation == null) {
         // throw exception token
      }
      
      final User user = userActivation.getUser();


      if (user.isEnabled()) {
         // already activated - log and continue
      }

      if (!email.equalsIgnoreCase(user.getEmail())) {
         // throw exception email
      }

      final Date today = new Date();

      if (today.after(new Date(userActivation.getValidTo()))) {
         // throw exception date
      }

      user.setEnabled(true);
      userRepository.save(user);

      // check i
//      if  ok 
//      initUserData(userActivation.getUser());


      return user;
   }

   @Override
   public User setEmpire(final Integer empireId, final long userId) { // TODO id to object
      final User user = userRepository.findById(userId);
      user.setEmpire(empireRepository.findById(empireId));

      return userRepository.save(user);
   }

   
//   private void initUserData(final User user) throws IOException {
//      // user resources
//
//      // user buildings
//      System.out.println("user_builidng.json");
//      final JsonNode userBuildingJson = commonUtil.getJson(RESOURCES_JSON + "user_building_data.json");
//      System.out.println("userBuilding: " + userBuildingJson.toString());
//
//      final UserData userData = new UserData();
//      userData.setUser(user);
//      userData.setUserBuildingData(UserBuildingDataTypeHandler.convert(userBuildingJson.toString()));
//
//      userDataService.updateUserData(userData);
//
//      // TODO json to data
////      final UserBuilding userBuilding = new UserBuilding(userBuildingJson, user);
////      userDataDAO.insert(userBuilding);
//
//      // user storage
//
//      // user production
//      
//   }

   private Date calculateExpiryDate() {
      Calendar cal = Calendar.getInstance();
      cal.setTime(new Date());
      System.out.println("today: " + cal.getTime());

      cal.add(Calendar.DAY_OF_YEAR, EXPIRATION_DAY);

      System.out.println("tomorrow: " + cal.getTime());

      return cal.getTime();
   }

   // add sql annotation

   private void sendConfirmEmail(final User user) {

   }





   /*

   private static final int EXPIRATION = 60 * 24;
 
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
     
    private String token;
   
    @OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "user_id")
    private User user;
     
    private Date expiryDate;
 
    public VerificationToken() {
        super();
    }
    public VerificationToken(String token, User user) {
        super();
        this.token = token;
        this.user = user;
        this.expiryDate = calculateExpiryDate(EXPIRATION);
        this.verified = false;
    }
     
    private Date calculateExpiryDate(int expiryTimeInMinutes) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Timestamp(cal.getTime().getTime()));
        cal.add(Calendar.MINUTE, expiryTimeInMinutes);
        return new Date(cal.getTime().getTime());
    }

    */

   //   @Override
   //   public User getU(final String str) {
   //      System.out.println("do something with sql");
   //
   ////      //      sqlTemplate.insert();
   ////      User u = new User();
   ////      //      u.setId(11l);
   ////      u.setPassword("hesloKleslo");
   ////      u.setRole_id();
   //
   //
   ////      userAuthenticationMapper.insert(u);
   //
   //      return userAuthenticationMapper.getUser(11l);
   //   }


   /*

   public interface UserDAO {
  @Select("SELECT * FROM users WHERE id = #{userId}")
  User getUser(@Param("userId") String userId);
}

http://www.mybatis.org/spring/getting-started.html
   https://www.javacodegeeks.com/2012/11/mybatis-tutorial-crud-operations-and-mapping-relationships-part-1.html

package com.sivalabs.mybatisdemo.mappers;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.sivalabs.mybatisdemo.domain.Blog;

public interface BlogMapper
{
 @Insert('INSERT INTO BLOG(BLOG_NAME, CREATED_ON) VALUES(#{blogName}, #{createdOn})')
 @Options(useGeneratedKeys=true, keyProperty='blogId')
 public void insertBlog(Blog blog);

 @Select('SELECT BLOG_ID AS blogId, BLOG_NAME as blogName, CREATED_ON as createdOn FROM BLOG WHERE BLOG_ID=#{blogId}')
 public Blog getBlogById(Integer blogId);

 @Select('SELECT * FROM BLOG ')
 @Results({
  @Result(id=true, property='blogId', column='BLOG_ID'),
  @Result(property='blogName', column='BLOG_NAME'),
  @Result(property='createdOn', column='CREATED_ON')
 })
 public List<Blog> getAllBlogs();

 @Update('UPDATE BLOG SET BLOG_NAME=#{blogName}, CREATED_ON=#{createdOn} WHERE BLOG_ID=#{blogId}')
 public void updateBlog(Blog blog);

 @Delete('DELETE FROM BLOG WHERE BLOG_ID=#{blogId}')
 public void deleteBlog(Integer blogId);

    */
}

