package com.fitness_management.models;


import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "Admin")
public class Admin extends Personne {

    public Admin() {
        super();
        this.setRole(Role.ROLE_ADMIN);
    }


}
