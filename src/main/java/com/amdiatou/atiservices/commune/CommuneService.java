package com.amdiatou.atiservices.commune;

import com.amdiatou.atiservices.execption.ResourceNotFound;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommuneService {

    private final CommuneDao communeDao;

    public CommuneService(CommuneDao communeDao) {
        this.communeDao = communeDao;
    }

    public List<Commune> list() {
        return communeDao.list();
    }

    public Commune getCommuneById(Integer id) {
        return communeDao
                .getById(id)
                .orElseThrow(() -> new ResourceNotFound("La ville avec l'id [%s] n'existe pas".formatted(id)));
    }
}
