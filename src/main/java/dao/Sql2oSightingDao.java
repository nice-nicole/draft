package dao;

import models.Animal;
import models.Sighting;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import java.util.List;

public class Sql2oSightingDao implements SightingDao {

    private final Sql2o sql2o;

    public Sql2oSightingDao(Sql2o sql2o){
        this.sql2o = sql2o;
    }

    @Override
    public void add(Sighting sighting) {
        String sql = "INSERT INTO sightings (location, rangerName) VALUES (:location, :rangerName)";
        try(Connection con = sql2o.open()){
            int id = (int) con.createQuery(sql, true)
                    .bind(sighting)
                    .executeUpdate()
                    .getKey();
            sighting.setId(id);
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public List<Sighting> getAll() {
        try(Connection con = sql2o.open()){
            return con.createQuery("SELECT * FROM sightings")
                    .executeAndFetch(Sighting.class);
        }
    }

    @Override
    public Sighting findById(int id) {
        try(Connection con = sql2o.open()){
            return con.createQuery("SELECT * FROM sightings WHERE id = :id")
                    .addParameter("id", id)
                    .executeAndFetchFirst(Sighting.class);
        }
    }

    @Override
    public void update(int id, String newLocation, int newAnimalId){
        String sql = "UPDATE sightings SET location= :location, animalId= :animalId WHERE id=:id";
        try(Connection con = sql2o.open()){
            con.createQuery(sql)
                    .addParameter("location", newLocation)
                    .addParameter("animalId", newAnimalId)
                    .addParameter("id", id)
                    .executeUpdate();
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public void deleteById(int id) {
        String sql = "DELETE from sightings WHERE id=:id"; //raw sql
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("id", id)
                    .executeUpdate();
        } catch (Sql2oException ex){
            System.out.println(ex);
        }
    }

    @Override
    public void clearAllSights() {
        String sql = "DELETE from sightings"; //raw sql
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .executeUpdate();
        } catch (Sql2oException ex){
            System.out.println(ex);
        }
    }
}
