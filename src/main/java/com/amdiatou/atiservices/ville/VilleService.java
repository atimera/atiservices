package com.amdiatou.atiservices.ville;

import com.amdiatou.atiservices.execption.DuplicateResource;
import com.amdiatou.atiservices.execption.InvalidRequest;
import com.amdiatou.atiservices.execption.ResourceNotFound;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VilleService {

    private final VilleDao villeDao;
    private final Mapper mapper;

    public VilleService(@Qualifier("inMemory") VilleDao villeDao, Mapper mapper) {
        this.villeDao = villeDao;
        this.mapper = mapper;
    }

    public List<Ville> list() {
        return villeDao.selectAllVilles();
    }

    public Ville getVilleById(Integer id) {
        return villeDao
                .selectVilleById(id)
                .orElseThrow(() -> new ResourceNotFound("La ville avec l'id [%s] n'existe pas".formatted(id)));
    }

    public void addVille(VilleDto villeDto) {
        if( villeDto.nom() == null || villeDto.nom().isBlank()
                || villeDto.codePostal() == null ||
                villeDto.departement() == null || villeDto.departement().isBlank()){
            throw new InvalidRequest("Au moins un champ obligatoire est vide");
        }

        if(villeDao.existsByCodePostal(villeDto.codePostal())){
            throw new DuplicateResource("Une ville avec ce codePostal existe déjà");
        }

        villeDao.addVille(mapper.toVille(villeDto));
    }

    public void updateVille(Integer id, VilleDto villeDto) {
        Ville villeToUpdate = villeDao.selectVilleById(id)
                .orElseThrow(() -> new ResourceNotFound("La ville avec l'id [%s] n'existe pas".formatted(id)));

        boolean dateChanged = false;
        if(villeDto.nom() != null && !villeDto.nom().equals(villeToUpdate.getNom())){
            villeToUpdate.setNom(villeDto.nom());
            dateChanged = true;
        }

        if(villeDto.codePostal() != null && !villeDto.codePostal().equals(villeToUpdate.getCodePostal())){
            villeToUpdate.setCodePostal(villeDto.codePostal());
            dateChanged = true;
        }

        if(villeDto.departement() != null && !villeDto.departement().equals(villeToUpdate.getDepartement())){
            villeToUpdate.setDepartement(villeDto.departement());
            dateChanged = true;
        }

        if(!dateChanged){
            throw new InvalidRequest("Aucun changement apporté");
        }
        villeDao.updateVille(villeToUpdate);

    }

    public void deleteVille(Integer id) {
        villeDao.deleteVilleById(id);
    }
}
