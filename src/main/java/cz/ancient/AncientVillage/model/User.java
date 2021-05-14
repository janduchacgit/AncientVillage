package cz.ancient.AncientVillage.model;

public class User {

   /** user_id {PK} */
   private Long id;

   /** password {SHA512} */
   private String password;

   /** email */
   private String email;

   /** playername */
   private String playerName;

   /** enabled */
   private Boolean enabled;

   /** firstname */
   private String firstName;

   /** surname */
   private String surname;

   /** age */
   private Integer age;

//   /** gender */
//   private Gender gender;
//
//   /** description */
//   private String description;
//
//   /** role_id {FK, user_role} */
//   private Role role;
//
//   /** empire_id {FK, empire} */
//   private Empire empire;

   /**
    * Empty constructor
    */
   public User() {
   }

   public User(final String playerName, final String email, final String password) {
      this.playerName = playerName;
      this.email = email;
      this.password = password;
      this.enabled = false;
//      this.role = Role.PLAYER;
   }

   public Long getId() {
      return id;
   }

   public void setId(Long id) {
      this.id = id;
   }

   public String getPassword() {
      return password;
   }

   public void setPassword(String password) {
      this.password = password;
   }

   public String getEmail() {
      return email;
   }

   public void setEmail(String email) {
      this.email = email;
   }

   public String getPlayerName() {
      return playerName;
   }

   public void setPlayerName(String playerName) {
      this.playerName = playerName;
   }

   public Boolean isEnabled() {
      return enabled;
   }

   public void setEnabled(Boolean enabled) {
      this.enabled = enabled;
   }

   public String getFirstName() {
      return firstName;
   }

   public void setFirstName(String firstName) {
      this.firstName = firstName;
   }

   public String getSurname() {
      return surname;
   }

   public void setSurname(String surname) {
      this.surname = surname;
   }

   public Integer getAge() {
      return age;
   }

   public void setAge(Integer age) {
      this.age = age;
   }

//   public Gender getGender() {
//      return gender;
//   }
//
//   public void setGender(Gender gender) {
//      this.gender = gender;
//   }
//
//   public String getDescription() {
//      return description;
//   }
//
//   public void setDescription(String description) {
//      this.description = description;
//   }
//
//   public Role getRole() {
//      return role;
//   }
//
//   public void setRole(Role role) {
//      this.role = role;
//   }
//
//   public Empire getEmpire() {
//      return empire;
//   }
//
//   public void setEmpire(Empire empire) {
//      this.empire = empire;
//   }
}
