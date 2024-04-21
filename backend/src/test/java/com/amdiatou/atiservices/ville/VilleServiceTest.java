package com.amdiatou.atiservices.ville;

import com.amdiatou.atiservices.execption.DuplicateResource;
import com.amdiatou.atiservices.execption.InvalidRequest;
import com.amdiatou.atiservices.execption.ResourceNotFound;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.amdiatou.atiservices.ville.Messages.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VilleServiceTest {

    private VilleService underTest;
    @Mock private VilleDao villeDao;

    @BeforeEach
    void setUp() {
        underTest = new VilleService(villeDao);
    }

    @Test
    void itShouldList() {
        // When
        underTest.list();

        // Then
        verify(villeDao).selectAllVilles();
    }

    @Test
    void itShouldGetVilleById() {
        // Given
        Long id = 10L;
        Ville ville = new Ville(id, "Roissy", 77680, "Seine-Et-Marne");
        Mockito.when(villeDao.selectVilleById(id)).thenReturn(Optional.of(ville));

        // When
        Ville actual = underTest.getVilleById(id);

        // Then
        assertThat(actual).isEqualTo(ville);
    }

    @Test
    void itWillThrowWhenGetVilleByIdReturnEmptyOptional() {
        // Given
        Long id = 10L;
        Mockito.when(villeDao.selectVilleById(id)).thenReturn(Optional.empty());

        // When
        // Then
        assertThatThrownBy(() -> underTest.getVilleById(id))
                .isInstanceOf(ResourceNotFound.class)
                .hasMessage(VILLE_N_EXISTE_PAS.formatted(id));

    }

    @Test
    void itShouldAddVille() {
        // Given
        NewVilleRequest request = new NewVilleRequest("Roissy", 77680, "Seine-Et-Marne");
        when(villeDao.existsByCodePostal(request.codePostal())).thenReturn(false);

        // When
        underTest.addVille(request);

        // Then
        ArgumentCaptor<Ville> villeArgumentCaptor = ArgumentCaptor.forClass(Ville.class);

        verify(villeDao).addVille(villeArgumentCaptor.capture());

        Ville capturedVille = villeArgumentCaptor.getValue();

        assertThat(capturedVille.getId()).isNull();
        assertThat(capturedVille.getNom()).isEqualTo(request.nom());
        assertThat(capturedVille.getCodePostal()).isEqualTo(request.codePostal());
        assertThat(capturedVille.getDepartement()).isEqualTo(request.departement());
    }

    @Test
    void itWillThrowWhenRequiredFieldsAreBlankWhileAddingVille() {
        // Given
        NewVilleRequest request = new NewVilleRequest("", 77680, "");

        // When
        assertThatThrownBy(() -> underTest.addVille(request))
                .isInstanceOf(InvalidRequest.class)
                .hasMessage(VILLE_CHAMP_OBLIGATOIRE_VIDE);

        // Then
        verify(villeDao, never()).addVille(any());
    }

    @Test
    void itWillThrowWhenRequiredFieldsAreNullWhileAddingVille() {
        // Given
        NewVilleRequest request = new NewVilleRequest(null, 77680, null);

        // When
        assertThatThrownBy(() -> underTest.addVille(request))
                .isInstanceOf(InvalidRequest.class)
                .hasMessage(VILLE_CHAMP_OBLIGATOIRE_VIDE);

        // Then
        verify(villeDao, never()).addVille(any());
    }

    @Test
    void itWillThrowWhenCodePostalExistsWhileAddingVille() {
        // Given
        NewVilleRequest request = new NewVilleRequest("Roissy", 77680, "Seine-Et-Marne");
        when(villeDao.existsByCodePostal(request.codePostal())).thenReturn(true);

        // When
        assertThatThrownBy(() -> underTest.addVille(request))
                .isInstanceOf(DuplicateResource.class)
                .hasMessage(VILLE_CODE_POSTAL_EXISTE_DEJA);

        // Then
        verify(villeDao, never()).addVille(any());
    }

    @Test
    void itShouldUpdateVille() {
        // Given
        Long id = 10L;
        UpdateVilleRequest request = new UpdateVilleRequest("ROISSY-EN-BRIE", 77680, "SEINE-ET-MARNE");

        Ville ville = new Ville(id, "Roissy", 77688, "Seine");
        when(villeDao.selectVilleById(id)).thenReturn(Optional.of(ville));
        when(villeDao.existsByCodePostal(request.codePostal())).thenReturn(false);

        // When
        underTest.updateVille(id, request);

        // Then
        ArgumentCaptor<Ville> villeArgumentCaptor = ArgumentCaptor.forClass(Ville.class);

        verify(villeDao).updateVille(villeArgumentCaptor.capture());

        Ville villeCaptured = villeArgumentCaptor.getValue();

        assertThat(villeCaptured.getId()).isEqualTo(id);
        assertThat(villeCaptured.getNom()).isEqualTo(request.nom());
        assertThat(villeCaptured.getCodePostal()).isEqualTo(request.codePostal());
        assertThat(villeCaptured.getDepartement()).isEqualTo(request.departement());
    }

    @Test
    void canUpdateOnlyVilleNom() {
        // Given
        Long id = 10L;
        UpdateVilleRequest request = new UpdateVilleRequest("ROISSY-EN-BRIE", null, null );

        Ville ville = new Ville(id, "Roissy", 77680, "Seine");
        when(villeDao.selectVilleById(id)).thenReturn(Optional.of(ville));

        // When
        underTest.updateVille(id, request);

        // Then
        ArgumentCaptor<Ville> villeArgumentCaptor = ArgumentCaptor.forClass(Ville.class);

        verify(villeDao).updateVille(villeArgumentCaptor.capture());

        Ville villeCaptured = villeArgumentCaptor.getValue();

        assertThat(villeCaptured.getId()).isEqualTo(id);
        assertThat(villeCaptured.getNom()).isEqualTo(request.nom());
        assertThat(villeCaptured.getCodePostal()).isEqualTo(ville.getCodePostal());
        assertThat(villeCaptured.getDepartement()).isEqualTo(ville.getDepartement());
    }

    @Test
    void canUpdateOnlyVilleDepartement() {
        // Given
        Long id = 10L;
        UpdateVilleRequest request = new UpdateVilleRequest(null, null, "SEINE-ET-MARNE" );

        Ville ville = new Ville(id, "Roissy", 77680, "Seine");
        when(villeDao.selectVilleById(id)).thenReturn(Optional.of(ville));

        // When
        underTest.updateVille(id, request);

        // Then
        ArgumentCaptor<Ville> villeArgumentCaptor = ArgumentCaptor.forClass(Ville.class);

        verify(villeDao).updateVille(villeArgumentCaptor.capture());

        Ville villeCaptured = villeArgumentCaptor.getValue();

        assertThat(villeCaptured.getId()).isEqualTo(id);
        assertThat(villeCaptured.getNom()).isEqualTo(ville.getNom());
        assertThat(villeCaptured.getCodePostal()).isEqualTo(ville.getCodePostal());
        assertThat(villeCaptured.getDepartement()).isEqualTo(request.departement());
    }

    @Test
    void canUpdateOnlyVilleCodePostal() {
        // Given
        Long id = 10L;
        UpdateVilleRequest request = new UpdateVilleRequest(null, 77685, null );

        Ville ville = new Ville(id, "Roissy", 77680, "Seine");
        when(villeDao.selectVilleById(id)).thenReturn(Optional.of(ville));
        when(villeDao.existsByCodePostal(request.codePostal())).thenReturn(false);

        // When
        underTest.updateVille(id, request);

        // Then
        ArgumentCaptor<Ville> villeArgumentCaptor = ArgumentCaptor.forClass(Ville.class);

        verify(villeDao).updateVille(villeArgumentCaptor.capture());

        Ville villeCaptured = villeArgumentCaptor.getValue();

        assertThat(villeCaptured.getId()).isEqualTo(id);
        assertThat(villeCaptured.getNom()).isEqualTo(ville.getNom());
        assertThat(villeCaptured.getCodePostal()).isEqualTo(request.codePostal());
        assertThat(villeCaptured.getDepartement()).isEqualTo(ville.getDepartement());
    }

    @Test
    void willThrowWhenCodePostalExistsWhileUpdatingVille() {
        // Given
        Long id = 10L;
        UpdateVilleRequest request = new UpdateVilleRequest("ROISSY-EN-BRIE", 77685, "SEINE-ET-MARNE" );

        Ville ville = new Ville(id, "Roissy", 77680, "Seine");
        when(villeDao.selectVilleById(id)).thenReturn(Optional.of(ville));
        when(villeDao.existsByCodePostal(request.codePostal())).thenReturn(true);

        // When
        assertThatThrownBy(() -> underTest.updateVille(id, request))
                .isInstanceOf(DuplicateResource.class)
                .hasMessage(VILLE_CODE_POSTAL_EXISTE_DEJA);

        // Then
        verify(villeDao, never()).updateVille(any());
    }

    @Test
    void willThrowWhenUpdatingVilleWithNoChanges() {
        // Given
        Long id = 10L;
        UpdateVilleRequest request = new UpdateVilleRequest("ROISSY-EN-BRIE", 77685, "SEINE-ET-MARNE" );

        Ville ville = new Ville(id, "ROISSY-EN-BRIE", 77685, "SEINE-ET-MARNE");
        when(villeDao.selectVilleById(id)).thenReturn(Optional.of(ville));

        // When
        assertThatThrownBy(() -> underTest.updateVille(id, request))
                .isInstanceOf(InvalidRequest.class)
                .hasMessage(VILLE_INCHANGEE);

        // Then
        verify(villeDao, never()).updateVille(any());
    }

    @Test
    void itShouldDeleteVille() {
        // Given
        Long id = 10L;
        when(villeDao.existsById(id)).thenReturn(true);

        // When
        underTest.deleteVille(id);

        // Then
        verify(villeDao).deleteVilleById(id);
    }

    @Test
    void itWillThrowWhenVilleNotFoundWhileDeleting() {
        // Given
        Long id = 10L;
        when(villeDao.existsById(id)).thenReturn(false);

        // When
        // Then
        assertThatThrownBy(() -> underTest.deleteVille(id))
                .isInstanceOf(ResourceNotFound.class)
                .hasMessage(VILLE_N_EXISTE_PAS.formatted(id));

        verify(villeDao, never()).deleteVilleById(any());
    }
}