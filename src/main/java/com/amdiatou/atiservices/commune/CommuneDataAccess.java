package com.amdiatou.atiservices.commune;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("fake")
public class CommuneDataAccess implements CommuneDao {

    public static List<Commune> list =
            List.of(new Commune(1, "Rossy-en-Brie", 77680, "SEINE-ET-MARNE"),
            new Commune(2, "Pontault-combault", 77340, "SEINE-ET-MARNE"));
    @Override
    public List<Commune> list() {
        return list;
    }

    @Override
    public Optional<Commune> getById(Integer id) {
        return list.stream().filter(c -> c.getId().equals(id)).findFirst();
    }
}
