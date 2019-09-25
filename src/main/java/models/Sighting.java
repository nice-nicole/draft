package models;

import java.util.Objects;

public class Sighting {
    private int animalId;
    private String location;
    private String rangerName;
    private int id;

    public Sighting(int animalId, String location, String rangerName) {
        this.animalId = animalId;
        this.location = location;
        this.rangerName= rangerName;
       
    }

    public int getId() {
        return id;
    }

    public void setAnimalId(int animalId) {
        this.animalId = animalId;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setRangerName(String rangerName) {
        this.rangerName = rangerName;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAnimalId() {
        return animalId;
    }

    public String getLocation() {
        return location;
    }

    public String getRangerName() {
        return rangerName;
    }

    @Override
    public boolean equals(Object obj) {
        if(!(this == obj))return true;
        if(!(obj instanceof Sighting)) return false;
        Sighting sighting = (Sighting) obj;
        return animalId == sighting.animalId &&
                rangerName == sighting.rangerName &&
                Objects.equals(location, sighting.location);

    }

    @Override
    public int hashCode() {
        return Objects.hash(location, rangerName);
    }
}
