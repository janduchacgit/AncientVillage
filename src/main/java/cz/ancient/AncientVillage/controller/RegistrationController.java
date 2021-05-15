package cz.ancient.AncientVillage.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cz.ancient.AncientVillage.api.UserService;

/**
 * RegistrationController -
 * @author Jan DUCHAC
 */
@Controller
@RequestMapping("/registration")
public class RegistrationController {

   @Autowired
   private MessageSource messages;

//   @Autowired
//   private JavaMailSender mailSender;

   @Autowired
   private UserService userService;


   @RequestMapping(value = "/register_user", method = { RequestMethod.POST })
   @ResponseBody
   public void createUser(
         @RequestParam("playername") final String playername,
         @RequestParam("email") final String email,
         @RequestParam("password") final String password,
         final HttpServletRequest request) {

      // TODO validation/check playername, email, password

      //      ResponseResult

      // email token
      final String contextPath = request.getContextPath() + request.getServletPath() + "/registration/activate_user";

      final boolean isCreated = userService.registerUser(playername, email, password, contextPath);
      // TODO isCreated, false = nepodarilo se vytvorit

      //      return "";

   }

   @RequestMapping(value = "/activate_user", method = { RequestMethod.POST })
   @ResponseBody
   public void activateUser(
         @RequestParam("token") final String token,
         @RequestParam("email") final String email) {

      // TODO validation/check playername, email, password

      //      ResponseResult


//      userService.createUser(playername, email, password);
      try {
         userService.activateUser(token, email);
      } catch (Exception e) {
         System.out.println(e.getMessage());
      }
      //      return "";

   }



   /*

   @Component
   public class RegistrationListener implements ApplicationListener<OnRegistrationCompleteEvent> {
       @Autowired
       private UserService service;
       @Autowired
       private MessageSource messages;
       @Autowired
       private JavaMailSender mailSender;

       @Override
       public void onApplicationEvent(OnRegistrationCompleteEvent event) {
           this.confirmRegistration(event);
       }

       private void confirmRegistration(OnRegistrationCompleteEvent event) {
           User user = event.getUser();
           String token = UUID.randomUUID().toString();
           service.createVerificationToken(user, token);

           String recipientAddress = user.getEmail();
           String subject = "Registration Confirmation";
           String confirmationUrl
             = event.getAppUrl() + "/regitrationConfirm.html?token=" + token;
           String message = messages.getMessage("message.regSucc", null, event.getLocale());

           SimpleMailMessage email = new SimpleMailMessage();
           email.setTo(recipientAddress);
           email.setSubject(subject);
           email.setText(message + " rn" + "http://localhost:8080" + confirmationUrl);
           mailSender.send(email);
       }
   }
    */

}
