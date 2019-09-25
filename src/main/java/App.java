import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dao.Sql2oAnimalDao;
import dao.Sql2oSightingDao;
import models.Animal;
import models.Sighting;
import org.sql2o.Sql2o;
import spark.ModelAndView;
import spark.template.handlebars.HandlebarsTemplateEngine;
import static spark.Spark.*;






public class App {
    public static void main(String[] args) { //type “psvm + tab” to autocreate this
        staticFileLocation("/public");
        String connectionString = "jdbc:h2:~/wildlifetracker;INIT=RUNSCRIPT from 'classpath:db/create.sql'";
        Sql2o sql2o = new Sql2o(connectionString, null, null);
        Sql2oSightingDao sightingDao = new Sql2oSightingDao(sql2o);
        Sql2oAnimalDao animalDao = new Sql2oAnimalDao(sql2o);



        //get: show all Sightings in all animals and show all animals
        get("/", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            List<Animal> allAnimals = animalDao.getAll();
            model.put("animals", allAnimals);
            List<Sighting> sightings = sightingDao.getAll();
            System.out.println(allAnimals);
            model.put("sightings", sightings);
            return new ModelAndView(model, "index.hbs");
        }, new HandlebarsTemplateEngine());

        //get: show a form to create a new Animal
        get("/animals/new", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            List<Animal> animals = animalDao.getAll(); //refresh list of links for navbar
            model.put("animals", animals);
            return new ModelAndView(model, "animal-form.hbs"); //new layout
        }, new HandlebarsTemplateEngine());

        //post: process a form to create a new Animal
        post("/animals", (req, res) -> { //new
            Map<String, Object> model = new HashMap<>();
            String name = req.queryParams("name");
            String endangered = req.queryParams("endangered");
            String condition = req.queryParams("condition");
            Animal newAnimal = new Animal(name,endangered,condition);
            animalDao.add(newAnimal);
            res.redirect("/");
            return null;
        }, new HandlebarsTemplateEngine());


        //get: delete all animals and all Sightings
        get("/animals/delete", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            animalDao.clearAllAnimals();
            sightingDao.clearAllSights();
            res.redirect("/");
            return null;
        }, new HandlebarsTemplateEngine());

        //get: delete all Sightings
        get("/sightings/delete", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            sightingDao.clearAllSights();
            res.redirect("/");
            return null;
        }, new HandlebarsTemplateEngine());

        //get a specific Animal (and the Sightings it contains)
        get("/animals/:id", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            int idOfAnimalToFind = Integer.parseInt(req.params("id")); //new
            Animal foundAnimal = animalDao.findById(idOfAnimalToFind);
            model.put("animal", foundAnimal);
            List<Sighting> allSightingsByAnimal = animalDao.getAllSightingsByAnimal(idOfAnimalToFind);
            model.put("sightings", allSightingsByAnimal);
            model.put("animals", animalDao.getAll()); //refresh list of links for navbar
            return new ModelAndView(model, "animal-detail.hbs"); //new
        }, new HandlebarsTemplateEngine());

        //get: show a form to update a Animal
        get("/animals/:id/edit", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            model.put("editAnimal", true);
            Animal animal = animalDao.findById(Integer.parseInt(req.params("id")));
            model.put("animal", animal);
            model.put("animals", animalDao.getAll()); //refresh list of links for navbar
            return new ModelAndView(model, "animal-form.hbs");
        }, new HandlebarsTemplateEngine());

        //post: process a form to update a Animal
        post("/animals/:id", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            int idOfAnimalToEdit = Integer.parseInt(req.params("id"));
            String newName = req.queryParams("newAnimalName");
            String newEndangered = req.queryParams("newAnimalendangered");
            String newCondition = req.queryParams("newAnimalCondition");
            animalDao.update(idOfAnimalToEdit, newName,newEndangered,newCondition);
            res.redirect("/");
            return null;
        }, new HandlebarsTemplateEngine());

        //get: delete an individual Sighting
        get("/animals/:animal_id/sightings/:sighting_id/delete", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            int idOfSightingToDelete = Integer.parseInt(req.params("sighting_id"));
            sightingDao.deleteById(idOfSightingToDelete);
            res.redirect("/");
            return null;
        }, new HandlebarsTemplateEngine());

        //get: show new Sighting form
        get("/sightings/new", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            List<Animal> animals = animalDao.getAll();
            model.put("animals", animals);
            return new ModelAndView(model, "sighting-form.hbs");
        }, new HandlebarsTemplateEngine());

        //Sighting: process new Sighting form
        post("/sightings", (req, res) -> { //URL to make new Sighting on POST route
            Map<String, Object> model = new HashMap<>();
            List<Animal> allAnimals = animalDao.getAll();
            model.put("animals", allAnimals);
            String location = req.queryParams("location");
            String rangerName = req.queryParams("rangerName");
            int animalId = Integer.parseInt(req.queryParams("animalId"));
            Sighting newSighting = new Sighting(animalId, location, rangerName);        //See what we did with the hard coded AnimalId?
            sightingDao.add(newSighting);
//            List<Sighting> SightingsSoFar = SightingDao.getAll();
//            for (Sighting SightingItem: SightingsSoFar
//                 ) {
//                System.out.println(SightingItem);
//            }
//            System.out.println(SightingsSoFar);
            res.redirect("/");
            return null;
        }, new HandlebarsTemplateEngine());

        //get: show an individual Sighting that is nested in a Animal
        get("/animals/:animal_id/sightings/:sighting_id", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            int idOfSightingToFind = Integer.parseInt(req.params("sighting_id")); //pull id - must match route segment
            Sighting foundSighting = sightingDao.findById(idOfSightingToFind); //use it to find Sighting
            int idOfAnimalToFind = Integer.parseInt(req.params("animal_id"));
            Animal foundAnimal = animalDao.findById(idOfAnimalToFind);
            model.put("animal", foundAnimal);
            model.put("sighting", foundSighting); //add it to model for template to display
            model.put("animals", animalDao.getAll()); //refresh list of links for navbar
            return new ModelAndView(model, "sighting-detail.hbs"); //individual Sighting page.
        }, new HandlebarsTemplateEngine());

        //get: show a form to update a Sighting
        get("/sightings/:id/edit", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            List<Animal> allAnimals = animalDao.getAll();
            model.put("animals", allAnimals);
            Sighting sighting = sightingDao.findById(Integer.parseInt(req.params("id")));
            model.put("sighting", sighting);
            model.put("editSighting", true);
            return new ModelAndView(model, "sighting-form.hbs");
        }, new HandlebarsTemplateEngine());

        //Sighting: process a form to update a Sighting
        post("/sightings/:id", (req, res) -> { //URL to update Sighting on POST route
            Map<String, Object> model = new HashMap<>();
            int sightingToEditId = Integer.parseInt(req.params("id"));
            String newLocation = req.queryParams("location");
            int newAnimalId = Integer.parseInt(req.queryParams("animalId"));
            sightingDao.update(sightingToEditId, newLocation,newAnimalId);  // remember the hardcoded AnimalId we placed? See what we've done to/with it?
            res.redirect("/");
            return null;
        }, new HandlebarsTemplateEngine());

    }
}
