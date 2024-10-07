package com.fitness_management;

import com.fitness_management.models.Personne;
import com.fitness_management.models.Role;
import com.fitness_management.models.User;
import com.fitness_management.models.Coach;
import com.fitness_management.repositories.PersonneRepository;
import com.fitness_management.services.PersonneService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PersonneServiceTest {

    @Mock
    private PersonneRepository personneRepository;

    @InjectMocks
    private PersonneService personneService;

    @Test
    public void testGetUsers() {
        // Arrange
        Personne user1 = new User();
        user1.setId(1L);
        user1.setNom("User 1");

        Personne user2 = new User();
        user2.setId(2L);
        user2.setNom("User 2");

        List<Personne> users = List.of(user1, user2);

        // Simuler l'appel au repository
        when(personneRepository.findAllByRole(Role.ROLE_UTILISATEUR)).thenReturn(users);

        // Act
        List<Personne> foundUsers = personneService.getUsers();

        // Assert
        assertEquals(2, foundUsers.size());
        assertEquals("User 1", foundUsers.get(0).getNom());
    }

    @Test
    public void testGetCoaches() {
        // Arrange
        Personne coach1 = new Coach();
        coach1.setId(3L);
        coach1.setNom("Coach 1");

        List<Personne> coaches = List.of(coach1);

        // Simuler l'appel au repository
        when(personneRepository.findAllByRole(Role.ROLE_COACH)).thenReturn(coaches);

        // Act
        List<Personne> foundCoaches = personneService.getCoaches();

        // Assert
        assertEquals(1, foundCoaches.size());
        assertEquals("Coach 1", foundCoaches.get(0).getNom());
    }

    @Test
    public void testDeletePersonne() {
        // Act
        personneService.deletePersonne(1L);

        // Assert
        verify(personneRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testFindAll() {
        // Arrange
        Personne user1 = new User();
        user1.setId(1L);
        user1.setNom("User 1");

        Personne user2 = new User();
        user2.setId(2L);
        user2.setNom("User 2");

        Personne coach1 = new Coach();
        coach1.setId(3L);
        coach1.setNom("Coach 1");

        List<Personne> allPersonnes = List.of(user1, user2, coach1);

        // Simuler l'appel au repository
        when(personneRepository.findAll()).thenReturn(allPersonnes);

        // Act
        List<Personne> foundAll = personneService.findAll();

        // Assert
        assertEquals(3, foundAll.size());
    }
}
