package cz.ancient.AncientVillage.db.impl;

import static cz.ancient.common.constant.CommonConstant.Path.RESOURCES_JSON;
import static cz.ancient.common.util.CommonUtil.commonUtil;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JsonNode;

import cz.ancient.AncientVillage.api.UserService;
import cz.ancient.dao.UserDataService;
import cz.ancient.dao.UserService;
import cz.ancient.dao.mapper.EmpireDAO;
import cz.ancient.dao.mapper.UserActivationDAO;
import cz.ancient.dao.mapper.UserDAO;
import cz.ancient.dao.typeHandler.UserBuildingDataTypeHandler;
import cz.ancient.model.Empire;
import cz.ancient.model.User;
import cz.ancient.model.UserActivation;
import cz.ancient.model.data.UserData;

/**
 * UserServiceImpl -
 * @author Jan DUCHAC
 */
@Component
public class UserServiceImpl implements UserService {

   private static final int EXPIRATION_DAY = 1; // TODO to properties

   //   @Autowired
   //   private SqlSessionTemplate sqlTemplate;

   @Autowired
   private UserDAO userDAO;

   @Autowired
   private EmpireDAO empireDAO;

   @Autowired
   private UserActivationDAO userActivationDAO;

   @Autowired
   private UserDataService userDataService;

   @Override
   public boolean registerUser(final String playerName, final String email, final String password, final String contextPath) {

      final ShaPasswordEncoder shaPasswordEncoder = new ShaPasswordEncoder(512);
      final String encodedPassword = shaPasswordEncoder.encodePassword(password, null);
      final User user = new User(playerName, email, encodedPassword);
      // password sha512

      // try-catch / throws Exception ?

      int i = userDAO.insert(user);

      if (i != 1) {
         // throw exception
      }

      System.out.println("userId id 2 : " + user.getId());

      final String token = UUID.randomUUID().toString();
      final Date expiryDate = calculateExpiryDate();

      i = userActivationDAO.insert(new UserActivation(token, expiryDate.getTime(), user));

      if (i != 1) {
         // throw exception
      }

      final UserActivation userActivation = userActivationDAO.getUserActivation(token);
      System.out.println("userActivation: " + userActivation.toString());

      // send email
      final String link = contextPath + "?token=" + token + "&email=" + email;
      System.out.println("link: " + link);

      return true;
   }

   @Override
   public User getUser(final Long id) {

      System.out.println("do User sql");

      return userDAO.getUser(id);
   }

   @Override
   public User getUser(final String playerName) {

      System.out.println("getUser playerName: " + playerName);

      return userDAO.getUserByName(playerName);
   }

   @Override
   public User activateUser(final String token, final String email) throws IOException {

      final UserActivation userActivation = userActivationDAO.getUserActivation(token);

      if (userActivation == null) {
         // throw exception token
      }

      if (userActivation.getUser().isEnabled()) {
         // already activated - log and continue
      }

      if (!email.equalsIgnoreCase(userActivation.getUser().getEmail())) {
         // throw exception email
      }

      final Date today = new Date();

      if (today.after(new Date(userActivation.getValidTo()))) {
         // throw exception date
      }

      final int i = userDAO.activateUser(userActivation.getUser().getId());

      // check i
//      if  ok 
      initUserData(userActivation.getUser());


      return userActivation.getUser();
   }

   @Override
   public User setEmpire(final Integer empireId, final Long userId) {

      final Empire empire = empireDAO.getEmpire(empireId);

      if (empire != null) {

         final int i = userDAO.setEmpire(empire, userId);

      }

      return userDAO.getUser(userId);
   }

   
   private void initUserData(final User user) throws IOException {
      // user resources

      // user buildings
      System.out.println("user_builidng.json");
      final JsonNode userBuildingJson = commonUtil.getJson(RESOURCES_JSON + "user_building_data.json");
      System.out.println("userBuilding: " + userBuildingJson.toString());

      final UserData userData = new UserData();
      userData.setUser(user);
      userData.setUserBuildingData(UserBuildingDataTypeHandler.convert(userBuildingJson.toString()));

      userDataService.updateUserData(userData);

      // TODO json to data
//      final UserBuilding userBuilding = new UserBuilding(userBuildingJson, user);
//      userDataDAO.insert(userBuilding);

      // user storage

      // user production
      
   }



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

