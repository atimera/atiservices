package com.amdiatou.atiservices.commune;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CommuneController {

    private final CommuneService communeService;

    public CommuneController(CommuneService communeService) {
        this.communeService = communeService;
    }

    @GetMapping("/api/v1/commues")
    public List<Commune> getListCommunes(){
        return communeService.list();
    }

    @GetMapping("/api/v1/commues/{id}")
    public Commune getCommune(@PathVariable(name = "id") Integer id){
        return communeService.getCommuneById(id);
    }
}
