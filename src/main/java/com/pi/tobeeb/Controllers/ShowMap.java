package com.pi.tobeeb.Controllers;

import com.pi.tobeeb.Entities.PharmacyLocation;
import com.pi.tobeeb.Services.PharmacyLocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
@Controller
public class ShowMap {

    @Autowired
    private PharmacyLocationService pharamciservice ;
    @GetMapping("/map")
    public String showMap(Model model) {
        List<PharmacyLocation> pharmacies = pharamciservice.getAllPharmacyLocations();

        model.addAttribute("pharmacies", pharmacies);
        model.addAttribute("mapboxAccessToken", "pk.eyJ1IjoicmloYWJuYWJsaTIyIiwiYSI6ImNsZm9mYWRzYjBybGQzcm1oNDlxbHoycDgifQ.3mAZiCdrDOmBXNhGOxkxtg");

        return "location";
    }
}