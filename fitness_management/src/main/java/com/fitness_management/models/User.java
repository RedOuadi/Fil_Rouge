package com.fitness_management.models;



import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "Utilisateur")
public class User extends Personne {

    @OneToMany(mappedBy = "utilisateur")
    private List<Activite> activites;

    @OneToMany(mappedBy = "utilisateur")
    private List<ObjectifFitness> objectifs;



    public User() {
        super();
        this.setRole(Role.ROLE_UTILISATEUR);
    }


}
