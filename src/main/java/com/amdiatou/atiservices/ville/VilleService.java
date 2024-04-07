package com.amdiatou.atiservices.ville;

import com.amdiatou.atiservices.execption.ResourceNotFound;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VilleService {

    private final VilleDao villeDao;

    public VilleService(@Qualifier("jpa") VilleDao villeDao) {
        this.villeDao = villeDao;
    }

    public List<Ville> list() {
        return villeDao.list();
    }

    public Ville getVilleById(Integer id) {
        return villeDao
                .getById(id)
                .orElseThrow(() -> new ResourceNotFound("La ville avec l'id [%s] n'existe pas".formatted(id)));
    }
}
