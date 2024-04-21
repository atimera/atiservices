package com.amdiatou.atiservices.ville;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("jpa")
public class VilleJPADataAccessService implements VilleDao {

    private final VilleRepository villeRepository;

    public VilleJPADataAccessService(VilleRepository villeRepository) {
        this.villeRepository = villeRepository;
    }

    @Override
    public List<Ville> selectAllVilles() {
        return villeRepository.findAll();
    }

    @Override
    public Optional<Ville> selectVilleById(Long id) {
        return villeRepository.findById(id);
    }

    @Override
    public void addVille(Ville ville) {
        villeRepository.save(ville);
    }

    @Override
    public void updateVille(Ville ville) {
        villeRepository.save(ville);
    }

    @Override
    public void deleteVilleById(Long id) {
        villeRepository.deleteById(id);
    }

    @Override
    public boolean existsByCodePostal(Integer codePostal) {
        return villeRepository.existsVilleByCodePostal(codePostal);
    }

    @Override
    public boolean existsById(Long id) {
        return villeRepository.existsById(id);
    }
}
