package com.amdiatou.atiservices.ville;

public record NewVilleRequest(
        String nom,
        Integer codePostal,
        String departement) {
}
