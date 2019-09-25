package models;

import java.time.LocalDateTime;
import java.util.Objects;

public class Animal {
    private int id;
    private String name;
    private String endangered;
    private String condition;
    private LocalDateTime age;

    public Animal(String name,String endangered, String condition) {
        this.name = name;
        this.endangered = endangered;
        this.condition = condition;
        this.age = LocalDateTime.now();
        this.id= id;

    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }


    public void setCondition(String condition) {
        this.condition = condition;
    }

    public void setAge(LocalDateTime age) {
        this.age = age;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEndangered() {
        return endangered;
    }

    public void setEndangered(String endangered) {
        this.endangered = endangered;
    }

    public String getCondition() {
        return condition;
    }

    public LocalDateTime getAge() {
        return age;
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj ) return  true;
        if(!(obj instanceof Animal)) return false;
        Animal animal = (Animal) obj;
        return condition == animal.condition &&

                endangered== animal.endangered &&
                Objects.equals(name, animal.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, endangered,condition,age,id);
    }
}
