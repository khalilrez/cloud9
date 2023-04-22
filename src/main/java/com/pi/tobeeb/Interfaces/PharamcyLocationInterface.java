package com.pi.tobeeb.Interfaces;

import com.pi.tobeeb.Entities.PharmacyLocation;

import java.util.List;

public interface PharamcyLocationInterface {
    public PharmacyLocation createPharmacyLocation(PharmacyLocation location);
    public PharmacyLocation getPharmacyLocationById(int id);
    public PharmacyLocation updatePharmacyLocation(int id, PharmacyLocation location)  ;
    public void deletePharmacyLocation(int id);
    public List<PharmacyLocation> getAllPharmacyLocations();
    public List<PharmacyLocation> search(String searchTerm);
}
