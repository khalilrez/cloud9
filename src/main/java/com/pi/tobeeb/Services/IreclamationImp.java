package com.pi.tobeeb.Services;

import com.pi.tobeeb.Entities.Reclamation;
import com.pi.tobeeb.Exceptions.ResourceNotFoundException;
import com.pi.tobeeb.Repositorys.ReclamationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class IreclamationImp implements Ireclamation {



@Autowired
    private ReclamationRepository reclamationRepository;

    @Override
    public List<Reclamation> getAllReclamations() {
        return reclamationRepository.findAll();
    }

    @Override
    public Reclamation getReclamationById(Long id) {
        return reclamationRepository.findById(id).orElse(null);
    }

    @Override
    public Reclamation createReclamation(Reclamation reclamation) {
        return reclamationRepository.save(reclamation);
    }

    @Override
    public Reclamation updateReclamation(Reclamation reclamation) {
        Reclamation updatedReclamation = reclamationRepository.findById(reclamation.getIdReclamation()).orElseThrow(() -> new ResourceNotFoundException("Delivery", "id", reclamation.getIdReclamation()));
        updatedReclamation.setDatereclam(reclamation.getDatereclam());
        updatedReclamation.setTitle(reclamation.getTitle());
        updatedReclamation.setDescription(reclamation.getDescription());
        updatedReclamation.setStatus(reclamation.getStatus());



        return reclamationRepository.save(reclamation);
    }

    @Override
    public void deleteReclamation(Long id) {
        reclamationRepository.deleteById(id);
    }
    @Override
    public List<Reclamation> getreclamnontraite()
    {
        return reclamationRepository.getreclamnontraite();
    }
}

