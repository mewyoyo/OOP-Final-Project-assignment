public abstract class Pet {
    private int id;
    private String name;
    private int age;
    private String status;

    public Pet(int id, String name, int age) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.status = "Available";
    }


    public int getId() {
        return id;
    }


    public String getName() {
        return name;
    }


    public int getAge() {
        return age;
    }


    public String getStatus() {
        return status;
    }

    public abstract void makeSound();

    public void setName(String name) { this.name = name; }
    public void setAge(int age) { this.age = age; }


    public String toFileString() {
        return id + "," + name + "," + age + "," + status;
    }
}
