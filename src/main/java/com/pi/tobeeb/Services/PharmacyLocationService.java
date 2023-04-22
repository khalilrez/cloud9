package com.pi.tobeeb.Services;

import com.pi.tobeeb.Entities.Order2;
import com.pi.tobeeb.Entities.PharmacyLocation;
import com.pi.tobeeb.Interfaces.PharamcyLocationInterface;
import com.pi.tobeeb.Repositorys.PharmacyLocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class PharmacyLocationService implements PharamcyLocationInterface {

    @Autowired
    private PharmacyLocationRepository repository;
    @Override

    public PharmacyLocation createPharmacyLocation(PharmacyLocation location) {
        return repository.save(location);
    }
    @Override

    public List<PharmacyLocation> getAllPharmacyLocations() {
        return (List<PharmacyLocation>) repository.findAll();
    }


    @Override

    public PharmacyLocation getPharmacyLocationById(int id) {
        return repository.findById(id).orElse((PharmacyLocation) null);
    }
    @Override

    public PharmacyLocation updatePharmacyLocation(int id, PharmacyLocation location) {
        PharmacyLocation existingLocation =  getPharmacyLocationById(id);

        existingLocation.setAltitude(location.getAltitude());
        existingLocation.setAmplitude(location.getAmplitude());
        existingLocation.setName(location.getName());

        return repository.save(existingLocation);
    }
    @Override

    public void deletePharmacyLocation(int id) {
        repository.deleteById(id);
    }
    @Override
    public List<PharmacyLocation> search(String searchTerm) {
        return repository.search(searchTerm);
    }

}
