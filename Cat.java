public class Cat extends Pet {
    private boolean isIndoor;

    public Cat(int id, String name, int age, boolean isIndoor) {
        super(id, name, age);
        this.isIndoor = isIndoor;
    }

    @Override
    public void makeSound() {
        System.out.println("Meow! Meow!");
    }
}
