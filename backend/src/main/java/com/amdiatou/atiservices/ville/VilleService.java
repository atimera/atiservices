package com.amdiatou.atiservices.ville;

import com.amdiatou.atiservices.execption.DuplicateResource;
import com.amdiatou.atiservices.execption.InvalidRequest;
import com.amdiatou.atiservices.execption.ResourceNotFound;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.amdiatou.atiservices.ville.Messages.VILLE_CODE_POSTAL_EXISTE_DEJA;
import static com.amdiatou.atiservices.ville.Messages.VILLE_N_EXISTE_PAS;

@Service
public class VilleService {

    private final VilleDao villeDao;

    public VilleService(@Qualifier("jdbc") VilleDao villeDao) {
        this.villeDao = villeDao;
    }

    public List<Ville> list() {
        return villeDao.selectAllVilles();
    }

    public Ville getVilleById(Long id) {
        return villeDao
                .selectVilleById(id)
                .orElseThrow(() -> new ResourceNotFound(VILLE_N_EXISTE_PAS.formatted(id)));
    }

    public void addVille(NewVilleRequest request) {
        if( request.nom() == null || request.nom().isBlank()
                || request.codePostal() == null ||
                request.departement() == null || request.departement().isBlank()){
            throw new InvalidRequest(Messages.VILLE_CHAMP_OBLIGATOIRE_VIDE);
        }

        if(villeDao.existsByCodePostal(request.codePostal())){
            throw new DuplicateResource(Messages.VILLE_CODE_POSTAL_EXISTE_DEJA);
        }

        Ville ville = new Ville(request.nom(), request.codePostal(), request.departement());

        villeDao.addVille(ville);
    }

    public void updateVille(Long id, UpdateVilleRequest request) {
        Ville villeToUpdate = villeDao.selectVilleById(id)
                .orElseThrow(() -> new ResourceNotFound(VILLE_N_EXISTE_PAS.formatted(id)));

        boolean dateChanged = false;
        if(request.nom() != null && !request.nom().equals(villeToUpdate.getNom())){
            villeToUpdate.setNom(request.nom());
            dateChanged = true;
        }

        if(request.codePostal() != null && !request.codePostal().equals(villeToUpdate.getCodePostal())){
            if(villeDao.existsByCodePostal(request.codePostal())){
                throw new DuplicateResource(VILLE_CODE_POSTAL_EXISTE_DEJA);
            }
            villeToUpdate.setCodePostal(request.codePostal());
            dateChanged = true;
        }

        if(request.departement() != null && !request.departement().equals(villeToUpdate.getDepartement())){
            villeToUpdate.setDepartement(request.departement());
            dateChanged = true;
        }

        if(!dateChanged){
            throw new InvalidRequest(Messages.VILLE_INCHANGEE);
        }

        villeDao.updateVille(villeToUpdate);
    }

    public void deleteVille(Long id) {
        if(!villeDao.existsById(id)){
            throw new ResourceNotFound(VILLE_N_EXISTE_PAS.formatted(id));
        }
        villeDao.deleteVilleById(id);
    }
}
