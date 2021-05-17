package cz.ancient.AncientVillage.api;

import java.io.IOException;

import cz.ancient.AncientVillage.model.User;

/**
 * Created by JanDuchac on 30.8.2016.
 */
public interface UserService {

   User getUser(long id);

   User getUser(String playerName);

   boolean registerUser(String playerName, String email, String password, String contextPath);

   User activateUser(String token, String email);

   User setEmpire(Integer empireId, long userId);
}
