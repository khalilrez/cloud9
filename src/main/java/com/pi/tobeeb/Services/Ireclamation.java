package com.pi.tobeeb.Services;

import com.pi.tobeeb.Entities.Reclamation;

import java.util.List;

public interface Ireclamation {


    List<Reclamation> getAllReclamations();

    Reclamation getReclamationById(Long id);

    Reclamation createReclamation(Reclamation reclamation);

    Reclamation updateReclamation(Reclamation reclamation);

    void deleteReclamation(Long id);

    List<Reclamation> getreclamnontraite();
}


