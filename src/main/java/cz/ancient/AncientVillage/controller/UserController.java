package cz.ancient.AncientVillage.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cz.ancient.AncientVillage.api.UserService;
import cz.ancient.AncientVillage.model.User;

//@SpringBootApplication
@Controller
@RequestMapping("/user")
public class UserController {

   // logger

   @Autowired
   private UserService userService;

//   @Autowired
//   private UserDataService userDataService;


   //   @RequestMapping(value = "/registration/create_user", method = { RequestMethod.GET, RequestMethod.POST }, produces = APPLICATION_JSON)
//   public @ResponseBody void createUser(
//         @RequestParam("playername") final String playername,
//         @RequestParam("email") final String email,
//         @RequestParam("password") final String password) {
//
//      // TODO validation/check playername, email, password
//
////      ResponseResult
//
//      userServiceDAO.createUser(playername, email, password);
//
//
////      return "";
//
//   }

//   @RequestMapping(value = "/get_user", method = { RequestMethod.GET, RequestMethod.POST }, produces = APPLICATION_JSON)
//   @ResponseBody
//   public User getUser(
//         @RequestParam("userId") final Long userId) {
//
//      // validate
//
//      final User user = userService.getUser(userId);
//
//      //		final String newName = UserService.sqlSomething(name);
//      //
//      //		Shop shop = new Shop();
//      //		shop.setName(newName);
//      ////		shop.setName(name);
//      //		shop.setStaffName(new String[] { "mkyong1", "mkyong2" });
//
//      return user;
//
//   }
//
//   @RequestMapping(value = "/get_user_data", method = { RequestMethod.GET, RequestMethod.POST }, produces = APPLICATION_JSON)
//   @ResponseBody
//   public UserData getUserData(
//         @RequestParam("userId") final Long userId) {
//
//      // validate
//
////      final User user = userService.getUser(userId);
//
//      final UserData userData = userDataService.getActualUserData(userId);
//
//      if (userData == null) {
//         return new UserData();
//      }
//
//      //      final UserBuilding userBuilding = userBuildingService.getActualUserBuilding(userId);
//
////      final UserData userData = new UserData();
//////      userData.setUser(user);
////      userData.setUserBuildingData(userBuilding.getData());
//
//      // calculateRemainingDuration
//      for (final BuildingData buildingData : userData.getUserBuildingData().getBuildings().values()) {
//         System.out.println("");
//         System.out.println("GET USER DATA: buildingDaTA: " + buildingData);
//
//         if (!CONSTRUCTED.equals(buildingData.getStatus())) {
//            final long remainingDuration = calculateRemainingDuration(buildingData);
//            System.out.println("remainingDuration: " + remainingDuration);
//
//            buildingData.setDuration((remainingDuration < 0) ? 0l : remainingDuration);
////            if (remainingDuration < 0) {
////               // TODO ERROR? should not be happened
////               System.out.println(" remainingDuration: continue ");
////
////
////               continue;
////            }
////
////            buildingData.setDuration(remainingDuration);
//         }
//      }
//
//      //      userData.setUserBuilding(userBuilding); // resources
////      userData.setUserBuilding(userBuilding); // army
//
//      //      userStorageService
////      userProductionService
//
//      //		final String newName = UserService.sqlSomething(name);
//      //
//      //		Shop shop = new Shop();
//      //		shop.setName(newName);
//      ////		shop.setName(name);
//      //		shop.setStaffName(new String[] { "mkyong1", "mkyong2" });
//
//      return userData;
//
//   }
//
//   // called after js time expiry of construction of building
//
//   @RequestMapping(value = "/check_user_data", method = { RequestMethod.GET, RequestMethod.POST }, produces = APPLICATION_JSON)
//   @ResponseBody
//   public UserData getUserDataWithCheck(
//         @RequestParam("userId") final Long userId,
//         @RequestParam("userId") final Integer positionId,
//         @RequestParam("userId") final Integer level) {
//
//      final UserData userData = getUserData(userId);
//
////      if (building on positionId finished (check level, because may be continue next level (OR start next level)))
//      // then return userData;
////      else wait 1 second and getUserData again + check
////         max waiting set from configuration (like 5-10s)
//
//      return userData;
//   }

   @RequestMapping(value = "/set_empire", method = { RequestMethod.POST })
   @ResponseBody
   public User setEmpire(
         @RequestParam("empireId") final Integer empireId,
         @RequestParam("userId") final Long userId) {

      // validate

      final User user = userService.setEmpire(empireId, userId);

      //		final String newName = UserService.sqlSomething(name);
      //
      //		Shop shop = new Shop();
      //		shop.setName(newName);
      ////		shop.setName(name);
      //		shop.setStaffName(new String[] { "mkyong1", "mkyong2" });

      return user;

   }
}