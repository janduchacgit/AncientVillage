package cz.ancient.AncientVillage.model.enums;

/**
 * Created by JanDuchac on 13.1.2017.
 */
public enum VillageType {

   VILLAGE("Village"), TOWN("Town"), CITY("City");

   private String name;

   VillageType(final String name) {
      this.name = name;
   }
}
