package com.amdiatou.atiservices.ville;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository("inMemory")
public class VilleInMemoryDataAccessService implements VilleDao {

    public List<Ville> list = new ArrayList<>(List.of(
            new Ville(1L, "Rossy-en-Brie", 77680, "SEINE-ET-MARNE"),
            new Ville(2L, "Pontault-combault", 77340, "SEINE-ET-MARNE")
    ));

    @Override
    public List<Ville> selectAllVilles() {
        return list;
    }

    @Override
    public Optional<Ville> selectVilleById(Long id) {
        return list.stream().filter(c -> c.getId().equals(id)).findFirst();
    }

    @Override
    public void addVille(Ville ville) {
        ville.setId(list.size() + 1L);
        list.add(ville);
    }

    @Override
    public void updateVille(Ville ville) {
        list.stream().filter(v -> v.getId().equals(ville.getId())).findFirst()
                .ifPresent(list::remove);
        list.add(ville);
    }

    @Override
    public void deleteVilleById(Long id) {
        list.stream().filter(v -> v.getId().equals(id)).findFirst()
                .ifPresent(list::remove);
    }

    @Override
    public boolean existsByCodePostal(Integer codePostal) {
        return list.stream().anyMatch(ville -> ville.getCodePostal().equals(codePostal));
    }

    @Override
    public boolean existsById(Long id) {
        return list.stream().anyMatch(ville -> ville.getId().equals(id));
    }
}
