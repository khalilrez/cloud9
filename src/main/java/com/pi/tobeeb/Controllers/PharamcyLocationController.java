package com.pi.tobeeb.Controllers;

import com.pi.tobeeb.Entities.Counttypepharmacy;
import com.pi.tobeeb.Entities.PharmacyLocation;
import com.pi.tobeeb.Services.PharmacyLocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
@CrossOrigin(origins = "http://localhost:4200/")

@RestController
@RequestMapping("/api/PharamcyLocation")
public class PharamcyLocationController {
    @Autowired
    private PharmacyLocationService pharamciservice ;

    @PostMapping
    public PharmacyLocation createPharmacyLocation(@RequestBody PharmacyLocation pharmacyLocation) {
        return pharamciservice.createPharmacyLocation(pharmacyLocation);
    }

    @GetMapping("/{id}")
    public PharmacyLocation getPharmacyLocation(@PathVariable int id) {
     return pharamciservice.getPharmacyLocationById(id);
    }
    @GetMapping
    public List<PharmacyLocation> getAllPharmacyLocations() {
        return pharamciservice.getAllPharmacyLocations();
    }

    @PutMapping("/{id}")
    public PharmacyLocation updatePharmacyLocation(@PathVariable int id,@RequestBody PharmacyLocation pharmacyLocation) {
        return pharamciservice.updatePharmacyLocation(id, pharmacyLocation);    }

    @DeleteMapping("deleteph/{id}")
    public String deletePharmacyLocation(@PathVariable int id)
    {
        pharamciservice.deletePharmacyLocation(id);
        return "location deleted succefully";
    }
    @RequestMapping("/map")
    @ResponseBody
    public String showMap() {
        // Retrieve the pharmacy location from your database or wherever it's stored
        ModelAndView modelAndView = new ModelAndView();
        // Return the name of your HTML file (without the .html extension)
        modelAndView.setViewName("location");
        return  ("location");
    }

    @GetMapping("/search/{searchTerm}")
    public List<PharmacyLocation> searchPharmacy(@PathVariable("searchTerm") String searchTerm) {
        return  pharamciservice.search(searchTerm);
    }
    @GetMapping("/percentage")
    public List<Counttypepharmacy> getpercentagebytype() {
        return  pharamciservice.countByTyp();
    }


}
