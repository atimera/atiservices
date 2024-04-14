package com.amdiatou.atiservices.ville;

import com.amdiatou.atiservices.AbstractTestcontainers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class VilleJDBCDataAccessServiceTest extends AbstractTestcontainers {
    private VilleJDBCDataAccessService underTest;
    private final VilleRowMapper villeRowMapper = new VilleRowMapper();

    @BeforeEach
    void setUp() {
        // On instancie ici, pour avoir une nouvelle instance à chaque test
        underTest = new VilleJDBCDataAccessService(
                getJdbcTemplate(),
                villeRowMapper
        );
    }

    @Test
    void itShouldSelectAllVilles() {
        // Given
        Ville ville = new Ville(
                "Roissy-En-Brie", 77680, "Seine-Et-Marne"
        );
        underTest.addVille(ville);

        // When
        List<Ville> villes = underTest.selectAllVilles();

        // Then
        assertThat(villes).isNotEmpty();
    }

    @Test
    void itShouldSelectVilleById() {
        // Given
        Ville ville = new Ville(
                "Roissy-En-Brie", 77690, "Seine-Et-Marne"
        );
        underTest.addVille(ville);
        Long id = underTest.selectAllVilles().stream()
                .filter(v -> v.getCodePostal().equals(77690))
                .map(Ville::getId)
                .findFirst()
                .orElseThrow();

        // When
        Optional<Ville> actual = underTest.selectVilleById(id);

        // Then
        assertThat(actual).isPresent().hasValueSatisfying(v -> {
            assertThat(v.getId()).isEqualTo(id);
            assertThat(v.getCodePostal()).isEqualTo(77690);
            assertThat(v.getDepartement()).isEqualTo("Seine-Et-Marne");
            assertThat(v.getNom()).isEqualTo("Roissy-En-Brie");
        });
    }

    @Test
    void willeReturnEmptyResultWhenSelectVilleById() {
        // Given
        Long id = -1L;

        // When
        Optional<Ville> actual = underTest.selectVilleById(id);

        // Then
        assertThat(actual).isEmpty();
    }

    @Test
    void itShouldAddVille() {
        // Given
        Ville ville = new Ville("Pontaul-Combault", 77340, "Seine-Et-Marne");

        // When
        underTest.addVille(ville);

        // Then
        Optional<Ville> found = underTest.selectAllVilles()
                .stream()
                .filter(v -> v.getNom().equals("Pontaul-Combault") &&
                        v.getCodePostal().equals(77340) &&
                        v.getDepartement().equals("Seine-Et-Marne")
                ).findFirst();
        assertThat(found).isPresent();
        assertThat(found.get().getId()).isNotNull();

    }

    @Test
    void itShouldUpdateVille() {
        // Given
        Ville ville = new Ville("Pontault-Combault", 77341, "Seine-Et-Marne");
        underTest.addVille(ville);
        Ville actual = underTest.selectAllVilles().stream()
                .filter(v -> v.getCodePostal().equals(77341))
                .findFirst().orElseThrow();
        actual.setNom("Pontault");
        actual.setDepartement("SEINE ET MARNE");
        actual.setCodePostal(77342);

        // When
        underTest.updateVille(actual);

        // Then
        assertThat(underTest.selectVilleById(actual.getId()))
                .isPresent().hasValueSatisfying(v -> {
                    assertThat(v.getNom().equals("Pontault")).isTrue();
                    assertThat(v.getDepartement().equals("SEINE ET MARNE")).isTrue();
                    assertThat(v.getCodePostal().equals(77342)).isTrue();
        });
    }

    @Test
    void itShouldDeleteVilleById() {
        // Given
        Ville ville = new Ville("Pontault-Combault", 77343, "Seine-Et-Marne");
        underTest.addVille(ville);
        Ville actual = underTest.selectAllVilles().stream()
                .filter(v -> v.getCodePostal().equals(77343))
                .findFirst().orElseThrow();

        // When
        underTest.deleteVilleById(actual.getId());

        // Then
        assertThat(underTest.selectVilleById(actual.getId())).isEmpty();
    }

    @Test
    void itShouldExistsByCodePostal() {
        // Given
        Ville ville = new Ville("Pontault-Combault", 77344, "Seine-Et-Marne");
        underTest.addVille(ville);

        // When
        boolean exists = underTest.existsByCodePostal(77344);
        boolean doNotExists = underTest.existsByCodePostal(-1000);

        // Then
        assertThat(exists).isTrue();
        assertThat(doNotExists).isFalse();
    }

    @Test
    void itShouldExistsById() {
        // Given
        Ville ville = new Ville("Pontault-Combault", 77345, "Seine-Et-Marne");
        underTest.addVille(ville);
        Ville actual = underTest.selectAllVilles().stream()
                .filter(v -> v.getCodePostal().equals(77345))
                .findFirst().orElseThrow();

        // When
        boolean exists = underTest.existsById(actual.getId());
        boolean doNotExists = underTest.existsById(-1L);

        // Then
        assertThat(exists).isTrue();
        assertThat(doNotExists).isFalse();
    }
}