package com.amdiatou.atiservices.ville;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class VilleRowMapperTest {

    @Test
    void itShouldMapRow() throws SQLException {
        // Given
        VilleRowMapper villeRowMapper = new VilleRowMapper();

        ResultSet resultSet = Mockito.mock(ResultSet.class);
        Mockito.when(resultSet.getLong("id")).thenReturn(1L);
        Mockito.when(resultSet.getString("nom")).thenReturn("Roissy");
        Mockito.when(resultSet.getInt("code_postal")).thenReturn(77680);
        Mockito.when(resultSet.getString("departement")).thenReturn("Seine-Et-Marne");

        // When
        Ville actual = villeRowMapper.mapRow(resultSet, 1);

        // Then
        Ville expected = new Ville(1L, "Roissy", 77680, "Seine-Et-Marne");
        assertThat(expected).isEqualTo(actual);
    }
}