package se.iths.bird.dtos;

public class BirdDto {
    private int id;
    private String name;
    private String type;
    private double weight;
    private String gender;

    public BirdDto(int id, String name, String type, double weight, String gender) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.weight = weight;
        this.gender = gender;
    }

    public BirdDto() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }


}
