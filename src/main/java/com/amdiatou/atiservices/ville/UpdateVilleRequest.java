package com.amdiatou.atiservices.ville;

public record UpdateVilleRequest (
        String nom,
        Integer codePostal,
        String departement){
}
