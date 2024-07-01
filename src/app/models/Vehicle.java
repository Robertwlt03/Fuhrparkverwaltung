package app.models;

public class Vehicle {
    private int id;
    private String licensePlate;
    private String brand;
    private String model;
    private String type;
    private String color;
    private int year;
    private String description;
    private String imagePath;

    // Getter und Setter
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String[] getPopularBrands() {
        return new String[]{
                "", "Audi", "BMW", "Cadillac", "Chevrolet", "DAF", "Dodge",   // PKW- und LKW-Marken alphabetisch sortiert
                "Ford", "Honda", "Iveco", "Jeep", "Kenworth", "MAN",
                "Mazda", "Mercedes-Benz", "Mercedes-Benz Trucks", "Nissan",
                "Porsche", "Renault Trucks", "Saab", "Scania", "Subaru",
                "Tesla", "Toyota", "Volkswagen", "Volvo", "Volvo Trucks"
        };
    }

    private final String[] truckBrands = {
            "DAF", "Iveco", "Kenworth", "MAN", "Mercedes-Benz Trucks", "Renault Trucks",
            "Scania", "Volvo Trucks"
    };

    // Method to check if the brand is a truck brand
    public boolean isTruckBrand(String brand) {
        for (String truckBrand : truckBrands) {
            if (truckBrand.equals(brand)) {
                return true;
            }
        }
        return false;
    }
}
