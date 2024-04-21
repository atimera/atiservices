package com.amdiatou.atiservices.it;

import com.amdiatou.atiservices.ville.NewVilleRequest;
import com.amdiatou.atiservices.ville.UpdateVilleRequest;
import com.amdiatou.atiservices.ville.Ville;
import com.amdiatou.atiservices.ville.VilleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
public class VilleIntegrationTest {

    @Autowired
    private WebTestClient webTestClient;
    private static final String VILLE_URI = "/api/v1/villes";

    @Autowired
    VilleRepository villeRepository;

    @BeforeEach
    void setUp() {
        villeRepository.deleteAll();
    }

    @Test
    void itShouldAddNewVille() {
        // === créer une nouvelle ville
        NewVilleRequest request = new NewVilleRequest("Roissy", 77680, "Seine-Et-Marne");
        // === envoyer une requête

        webTestClient.post()
                .uri(VILLE_URI)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(request), NewVilleRequest.class)
                .exchange()
                .expectStatus()
                .isOk();

        // === lister toutes les villes
        List<Ville> responseBody = webTestClient.get()
                .uri(VILLE_URI)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(new ParameterizedTypeReference<Ville>() {
                })
                .returnResult()
                .getResponseBody();

        assertThat(responseBody).isNotEmpty();

        Ville expected = new Ville(request.nom(), request.codePostal(), request.departement());
        assertThat(responseBody).isNotEmpty();
        assertThat(responseBody)
                .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id")
                .contains(expected);

        Long id = responseBody.stream()
                .filter(ville ->
                        ville.getNom().equals(request.nom()) &&
                        ville.getCodePostal().equals(request.codePostal()) &&
                        ville.getDepartement().equals(request.departement()))
                .map(Ville::getId)
                .findFirst().orElseThrow();

        // === rechercher par id
        Ville actual = webTestClient.get()
                .uri(VILLE_URI + "/{id}", Map.of("id", id))
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(new ParameterizedTypeReference<Ville>() {
                })
                .returnResult()
                .getResponseBody();

        expected.setId(id);
        assertThat(actual).isEqualTo(expected);

        // === modifier la ville
        UpdateVilleRequest updateVilleRequest = new UpdateVilleRequest(null, 77340, "SEINE");
        webTestClient.put()
                .uri(VILLE_URI + "/{id}", Map.of("id", id))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(updateVilleRequest), UpdateVilleRequest.class)
                .exchange()
                .expectStatus()
                .isOk();

        // === rechercher par id
        Ville updated = webTestClient.get()
                .uri(VILLE_URI + "/{id}", Map.of("id", id))
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(new ParameterizedTypeReference<Ville>() {
                })
                .returnResult()
                .getResponseBody();

        expected.setCodePostal(77340);
        expected.setDepartement("SEINE");
        assertThat(updated).isEqualTo(expected);


        // === supprimer la ville
        webTestClient.delete()
                .uri(VILLE_URI + "/{id}", id)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk();

        // === rechercher par id
        webTestClient.get()
                .uri(VILLE_URI + "/{id}", Map.of("id", id))
                .exchange()
                .expectStatus()
                .isNotFound();
    }
}
