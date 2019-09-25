package dao;

import models.Animal;
import models.Sighting;

import java.util.List;

public interface SightingDao {

    //List
    List<Sighting> getAll();

    //Create
    void add (Sighting sighting);

    //Read
    Sighting findById(int id);

    //Update
    void update(int id, String location, int animalId);
    //Delete
    void deleteById(int id);
    void clearAllSights();
}
