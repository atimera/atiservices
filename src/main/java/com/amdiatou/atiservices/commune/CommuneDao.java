package com.amdiatou.atiservices.commune;

import java.util.List;
import java.util.Optional;

public interface CommuneDao {

    List<Commune> list();

    Optional<Commune> getById(Integer id);
}
