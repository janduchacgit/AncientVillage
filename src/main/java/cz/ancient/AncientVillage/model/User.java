package cz.ancient.AncientVillage.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import cz.ancient.AncientVillage.model.enums.Gender;
import cz.ancient.AncientVillage.model.enums.Role;

@Entity
public class User {

    @Id
    @GeneratedValue
    private Long id;
    
    private String password;
    
    private String email;
    
    private String playerName;
    
    private Boolean enabled;
    
    private String firstName;
    
    private String surname;
    
    private Integer age;
    
    private Gender gender;
    
    private String description;
    
    private Role role;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "empire_id", referencedColumnName = "id")
    private Empire empire;

    public User() {
    }

    public User(final String playerName, final String email, final String password) {
        this.playerName = playerName;
        this.email = email;
        this.password = password;
        this.enabled = false;
        this.role = Role.PLAYER;
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

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Empire getEmpire() {
        return empire;
    }

    public void setEmpire(Empire empire) {
        this.empire = empire;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("User{");
        sb.append("id=").append(id);
        sb.append(", password='").append(password).append('\'');
        sb.append(", email='").append(email).append('\'');
        sb.append(", playerName='").append(playerName).append('\'');
        sb.append(", enabled=").append(enabled);
        sb.append(", firstName='").append(firstName).append('\'');
        sb.append(", surname='").append(surname).append('\'');
        sb.append(", age=").append(age);
        sb.append(", gender=").append(gender);
        sb.append(", description='").append(description).append('\'');
        sb.append(", role=").append(role);
        sb.append(", empire=").append(empire);
        sb.append('}');
        return sb.toString();
    }
}
