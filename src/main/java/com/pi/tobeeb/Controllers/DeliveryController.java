package com.pi.tobeeb.Controllers;
import com.pi.tobeeb.Entities.Delivery;
import com.pi.tobeeb.Services.Idelevirey;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(origins = "*", maxAge = 3600)

@RestController
@RequestMapping("/delivery")
@AllArgsConstructor
public class DeliveryController {
    @Autowired
    Idelevirey idelevirey;
        @GetMapping("/getAlldeleviry")
    public List<Delivery> getAlldelivery() {
        return idelevirey.retrieveDeliverys();
    }

    @PostMapping("/adddelevirey")
    public Delivery adddelivery(@RequestBody Delivery delivery) {
        return idelevirey.addDelivery(delivery);
    }
    @DeleteMapping("/deletedelevirey/{id}")
    public void deletedelivery(@PathVariable("id") int id) {
        idelevirey.removedelivery(id);
    }


    @PutMapping("/updatedelevirey")
    public Delivery updatedelivery(@RequestBody Delivery delivery) {

        return idelevirey.updateDelivery(delivery);
    }

}


