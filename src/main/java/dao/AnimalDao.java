package dao;

import models.Animal;
import models.Sighting;

import java.util.List;

public interface AnimalDao {
    //List
    List<Animal> getAll();

    //Create
    void add (Animal animal);

    //Read
    Animal findById(int id);
    List<Sighting> getAllSightingsByAnimal(int animalId);

    //Update
    void update(int id, String name, String endangered, String condition);

    //Delete
    void deleteById(int id);
    void clearAllAnimals ();
}
