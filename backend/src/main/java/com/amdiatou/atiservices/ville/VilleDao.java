package com.amdiatou.atiservices.ville;

import java.util.List;
import java.util.Optional;

public interface VilleDao {

    List<Ville> selectAllVilles();
    Optional<Ville> selectVilleById(Long id);
    void addVille(Ville ville);
    void updateVille(Ville villeToUpdate);
    void deleteVilleById(Long id);
    boolean existsByCodePostal(Integer codePostal);
    boolean existsById(Long id);

}
