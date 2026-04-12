public class Dog extends Pet {
    private String breed;

    public Dog(int id, String name, int age, String breed) {
        super(id, name, age);
        this.breed = breed;
    }

    @Override
    public void makeSound() {
        System.out.println("Woof! Woof!");
    }
}
