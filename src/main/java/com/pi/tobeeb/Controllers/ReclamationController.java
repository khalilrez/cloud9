package com.pi.tobeeb.Controllers;



import com.pi.tobeeb.Entities.Reclamation;
import com.pi.tobeeb.Services.EmailService;
import com.pi.tobeeb.Services.Ireclamation;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.util.List;
@AllArgsConstructor
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)

@RequestMapping("/reclamations")
public class ReclamationController {

@Autowired
    private Ireclamation reclamationService;
    private EmailService emailService;
    @GetMapping("/getAllReclamationsnontraite")
    public List<Reclamation> getAllReclamationsnontraite() {
        return reclamationService.getreclamnontraite();
    }

    @GetMapping("/getAllReclamations")
    public List<Reclamation> getAllReclamations() {
        return reclamationService.getAllReclamations();
    }

    @GetMapping("/getreclam/{id}")
    public Reclamation getReclamationById(@PathVariable Long id) {
        return reclamationService.getReclamationById(id);
    }

    @PostMapping("/ajouterreclamation")
    public Reclamation saveReclamation(@RequestBody Reclamation reclamation) {

        emailService.sendEmail("yassine.khanfir@esprit.tn","Réclamation","Bonjour\n Votre reclamation sera traiter dans le delais");
        return reclamationService.createReclamation(reclamation);
    }

    @PutMapping("/modifreclamation")
    public Reclamation updateReclamation(@RequestBody Reclamation reclamation) {
        return reclamationService.updateReclamation(reclamation);
    }

    @DeleteMapping("deletereclam/{id}")
    public void deleteReclamation(@PathVariable Long id) {
        reclamationService.deleteReclamation(id);
    }
}

