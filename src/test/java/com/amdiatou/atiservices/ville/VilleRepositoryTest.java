package com.amdiatou.atiservices.ville;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.ApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class VilleRepositoryTest {

    @Autowired
    private VilleRepository underTest;

    @Autowired
    private ApplicationContext applicationContext;

    @BeforeEach
    void setUp() {
        underTest.deleteAll();
        System.out.println("Nb bean definition = " + applicationContext.getBeanDefinitionCount());
    }

    @Test
    void itShouldExistsVilleByCodePostal() {
        // Given
        Ville ville = new Ville(
                "Roissy-En-Brie", 77679, "Seine-Et-Marne"
        );
        underTest.save(ville);

        // When
        boolean exists = underTest.existsVilleByCodePostal(77679);
        boolean existsNot = underTest.existsVilleByCodePostal(-1000);

        // Then
        assertThat(exists).isTrue();
        assertThat(existsNot).isFalse();
    }

    @Test
    void itShouldExistsVilleById() {
        // Given
        Ville ville = new Ville(
                "Roissy-En-Brie", 77678, "Seine-Et-Marne"
        );
        underTest.save(ville);
        Long id = underTest.findAll().stream().filter(v -> v.getCodePostal().equals(77678))
                .map(Ville::getId)
                .findFirst().orElseThrow();

        // When
        boolean exists = underTest.existsById(id);
        boolean existsNot = underTest.existsById(0L);

        // Then
        assertThat(exists).isTrue();
        assertThat(existsNot).isFalse();
    }
}