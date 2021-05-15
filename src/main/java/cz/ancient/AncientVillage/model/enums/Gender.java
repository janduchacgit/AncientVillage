package cz.ancient.AncientVillage.model.enums;

/**
 * Created by JanDuchac on 13.11.2016.
 */
public enum Gender {

   M("Male"), F("Female"); // MALE, FEMALE

   private String gender;

   Gender(final String gender) {
      this.gender = gender;
   }

   public String getGender() {
      return gender;
   }

}
