package com.amdiatou.atiservices.commune;

public record CommuneDto(
        Integer id,
        String nom,
        Integer codePostal,
        String departement) {
}
