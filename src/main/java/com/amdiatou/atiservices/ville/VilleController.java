package com.amdiatou.atiservices.ville;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/villes")
public class VilleController {

    private final VilleService villeService;

    public VilleController(VilleService villeService) {
        this.villeService = villeService;
    }

    @GetMapping
    public List<Ville> getListVilles(){
        return villeService.list();
    }

    @GetMapping("{id}")
    public Ville getVille(@PathVariable(name = "id") Long id){
        return villeService.getVilleById(id);
    }

    @PostMapping
    public void addVille(@RequestBody VilleDto villeDto){
         villeService.addVille(villeDto);
    }

    @PutMapping("{id}")
    public void updateVille(@PathVariable(name = "id") Long id,
                            @RequestBody VilleDto villeDto){
        villeService.updateVille(id, villeDto);
    }

    @DeleteMapping("{id}")
    public void removeVille(@PathVariable(name = "id") Long id){
        villeService.deleteVille(id);
    }





}
