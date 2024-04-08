package com.amdiatou.atiservices.ville;

import org.springframework.data.jpa.repository.JpaRepository;


public interface VilleRepository extends JpaRepository<Ville, Integer> {

    boolean existsVilleByCodePostal(Integer codePostal);
    boolean existsVilleById(Integer id);
}
