package com.amdiatou.atiservices.ville;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class VilleController {

    private final VilleService villeService;

    public VilleController(VilleService villeService) {
        this.villeService = villeService;
    }

    @GetMapping("/api/v1/villes")
    public List<Ville> getListVilles(){
        return villeService.list();
    }

    @GetMapping("/api/v1/villes/{id}")
    public Ville getVille(@PathVariable(name = "id") Integer id){
        return villeService.getVilleById(id);
    }
}
