package com.amdiatou.atiservices.ville;

import java.util.List;
import java.util.Optional;

public interface VilleDao {

    List<Ville> list();
    Optional<Ville> getById(Integer id);

}
