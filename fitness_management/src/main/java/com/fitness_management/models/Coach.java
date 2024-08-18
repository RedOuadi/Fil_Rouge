package com.fitness_management.models;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "Coach")
public class Coach extends Personne {

    private String certification;



    public Coach() {
        super();
        this.setRole(Role.ROLE_COACH);
    }


}
