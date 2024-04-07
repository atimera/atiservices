package com.amdiatou.atiservices.ville;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("jpa")
public class VilleJPADataAccessServices implements VilleDao {

    private final VilleRepository villeRepository;

    public VilleJPADataAccessServices(VilleRepository villeRepository) {
        this.villeRepository = villeRepository;
    }

    @Override
    public List<Ville> list() {
        return villeRepository.findAll();
    }

    @Override
    public Optional<Ville> getById(Integer id) {
        return villeRepository.findById(id);
    }
}
