package com.amdiatou.atiservices.ville;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("inMemory")
public class VilleInMemoryDataAccessService implements VilleDao {

    public static List<Ville> list =
            List.of(new Ville(1, "Rossy-en-Brie", 77680, "SEINE-ET-MARNE"),
            new Ville(2, "Pontault-combault", 77340, "SEINE-ET-MARNE"));
    @Override
    public List<Ville> list() {
        return list;
    }

    @Override
    public Optional<Ville> getById(Integer id) {
        return list.stream().filter(c -> c.getId().equals(id)).findFirst();
    }
}
