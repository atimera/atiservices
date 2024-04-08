package com.amdiatou.atiservices.ville;

import org.springframework.stereotype.Component;


@Component
public class Mapper {

    public Ville toVille(VilleDto villeDto) {
        return new Ville(villeDto.nom(), villeDto.codePostal(), villeDto.departement());
    }
}
