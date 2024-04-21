package com.amdiatou.atiservices.ville;

import org.springframework.data.jpa.repository.JpaRepository;


public interface VilleRepository extends JpaRepository<Ville, Long> {

    boolean existsVilleByCodePostal(Integer codePostal);
    boolean existsVilleById(Long id);
}
