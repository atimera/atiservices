package com.amdiatou.atiservices.ville;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


class VilleJPADataAccessServicesTest {

    @Mock
    private VilleRepository villeRepository;
    private VilleJPADataAccessServices underTest;

    private AutoCloseable autoCloseable;

    @BeforeEach
    void setUp() {
        // On aurait pu juste ajouter l'annotation @ExtendWith(MockitoExtension.class) à la classe de test
        autoCloseable = MockitoAnnotations.openMocks(this);
        underTest = new VilleJPADataAccessServices(villeRepository);
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    void itShouldSelectAllVilles() {
        // When
        underTest.selectAllVilles();

        // Then
        verify(villeRepository).findAll();
    }

    @Test
    void itShouldSelectVilleById() {
        // Given
        Long id = 10L;

        // When
        underTest.selectVilleById(id);

        // Then
        verify(villeRepository).findById(id);
    }

    @Test
    void itShouldAddVille() {
        // Given
        Ville ville = new Ville("Roissy", 77680, "Seine-Et-Marne");

        // When
        underTest.addVille(ville);

        // Then
        verify(villeRepository).save(ville);
    }

    @Test
    void itShouldUpdateVille() {
        // Given
        Ville ville = new Ville(10L, "Roissy", 77680, "Seine-Et-Marne");

        // When
        underTest.updateVille(ville);

        // Then
        verify(villeRepository).save(ville);
    }

    @Test
    void itShouldDeleteVilleById() {
        // Given
        Long id = 10L;
        when(villeRepository.existsVilleById(id)).thenReturn(true);

        // When
        underTest.deleteVilleById(id);

        // Then
        verify(villeRepository).deleteById(id);
    }

    @Test
    void willThrowWhenDeleteVilleByIdThatDoesNotExists() {
        // Given
        Long id = 10L;
        when(villeRepository.existsVilleById(id)).thenReturn(false);

        // When
        underTest.deleteVilleById(id);

        // Then
        verify(villeRepository).deleteById(id);
    }

    @Test
    void itShouldExistsByCodePostal() {
        // Given
        Integer codePostal = 10;

        // When
        underTest.existsByCodePostal(codePostal);

        // Then
        verify(villeRepository).existsVilleByCodePostal(codePostal);
    }

    @Test
    void itShouldExistsById() {
        // Given
        Long id = 10L;

        // When
        underTest.existsById(id);

        // Then
        verify(villeRepository).existsById(id);
    }
}