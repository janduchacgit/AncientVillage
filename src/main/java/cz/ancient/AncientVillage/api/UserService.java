package cz.ancient.AncientVillage.api;

import java.io.IOException;

import cz.ancient.AncientVillage.model.User;

/**
 * Created by JanDuchac on 30.8.2016.
 */
public interface UserService {

   boolean registerUser(String playerName, String email, String password, String contextPath);

   User getUser(Long id);

   User getUser(String playerName);

   User activateUser(String token, String email) throws IOException;

   User setEmpire(Integer empireId, Long userId);
}
