package com.norafit.norafit.entities;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column; 
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity // se marca que es una entidad para la base de datos
@Table(name = "users")
public class User {

    @Id //definir PK 
    @GeneratedValue(strategy = GenerationType.IDENTITY)    
    private Integer id;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;
    
    private char role;

    @Column(name = "created_at") 
    private LocalDate created_at;

    @Column(name = "email")
    private String email;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "verified")
    private Boolean verified = false;

    @OneToMany(mappedBy = "user")
    @Transient
    private List<Routine> routines = new ArrayList<>();

    // Constructor vacio
    public User() {
    }

    //  Constructor sin lista
    public User(int id, String username, String password, char role, LocalDate createdAt, String email) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.role = role;
        this.created_at = createdAt;
        this.email = email;
    }

    // Constructor completo
    public User(int id, String username, String password, char role, LocalDate createdAt, List<Routine> routines, String email) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.role = role;
        this.created_at = createdAt;
        this.email = email;
        this.routines = routines;
    }


    // Constructor SIN id (porque lo genera la BD)
    public User(String username, String password, char role, LocalDate createdAt) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.created_at = createdAt;
    }

    //getters y setters
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public char getRole() {
        return role;
    }
    public void setRole(char role) {
        this.role = role;
    }

    public LocalDate getCreatedAt() {
        return created_at;
    }
    public void setCreatedAt(LocalDate createdAt) {
        this.created_at = createdAt;
    }

    public String getEmail(){
        return email;
    }
    public void setEmail(String email){
        this.email = email;
    }

    public List<Routine> getRoutines() {
        return routines;
    }
    public void setRoutines(List<Routine> routines) {
        this.routines = routines;
    }

    public String getPhoneNumber() { 
        return phoneNumber; 
    }
    public void setPhoneNumber(String phoneNumber) { 
        this.phoneNumber = phoneNumber; 
    }

    public boolean isVerified() { 
        return verified; 
    }
    public void setVerified(boolean verified) { 
        this.verified = verified; 
    }
}
