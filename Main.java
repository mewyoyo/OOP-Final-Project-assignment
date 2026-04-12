import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    private static ArrayList<Pet> petDatabase = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        loadFromFile();

        boolean running = true;
        System.out.println("--- Welcome to Pet Adoption System ---");

        while (running) {
            System.out.println("\nSelect an option:");
            System.out.println("1. Add a Dog");
            System.out.println("2. Add a Cat");
            System.out.println("3. View all pets");
            System.out.println("4. Update pet info");
            System.out.println("5. Remove a pet");
            System.out.println("6. Export to CSV");
            System.out.println("0. Exit");
            System.out.print("Your choice: ");

            int choice = getValidInt();
            scanner.nextLine();

            switch (choice) {
                case 1: addDog(); break;
                case 2: addCat(); break;
                case 3: viewPets(); break;
                case 4: updatePet(); break;
                case 5: deletePet(); break;
                case 6: exportToCSV(); break;
                case 0:
                    saveToFile();
                    running = false;
                    System.out.println("Exiting... Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }

    private static void addDog() {
        System.out.print("Enter ID: ");
        int id = getValidInt();
        System.out.print("Enter Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Age: ");
        int age = getValidInt();
        System.out.print("Enter Breed: ");
        String breed = scanner.nextLine();

        petDatabase.add(new Dog(id, name, age, breed));
        System.out.println("Dog added successfully!");
    }


    private static void addCat() {
        System.out.print("Enter ID: ");
        int id = getValidInt();
        System.out.print("Enter Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Age: ");
        int age = getValidInt();
        System.out.print("Is it an indoor cat? (true/false): ");
        boolean isIndoor = scanner.nextBoolean();

        petDatabase.add(new Cat(id, name, age, isIndoor));
        System.out.println("Cat added successfully!");
    }

    private static void viewPets() {
        if (petDatabase.isEmpty()) {
            System.out.println("Database is empty.");
        } else {
            System.out.println("\n--- List of Pets ---");
            for (Pet p : petDatabase) {

                System.out.println("ID: " + p.getId() + " | Name: " + p.getName() + " | Age: " + p.getAge());
                p.makeSound();
            }
        }
    }

    private static void updatePet() {
        System.out.print("Enter Pet ID to update: ");
        int id = getValidInt();

        for (Pet p : petDatabase) {
            if (p.getId() == id) {
                System.out.print("Enter new name: ");
                p.setName(scanner.nextLine());
                System.out.print("Enter new age: ");
                int newAge = getValidInt();

                p.setAge(newAge);

                System.out.println("Pet updated successfully!");
                return;
            }
        }
        System.out.println("Pet with ID " + id + " not found.");
    }

    private static void deletePet() {
        System.out.print("Enter Pet ID to remove: ");
        int id = getValidInt();

        boolean removed = petDatabase.removeIf(p -> p.getId() == id);

        if (removed) {
            System.out.println("Pet removed from database.");
        } else {
            System.out.println("Pet with ID " + id + " not found.");
        }
    }


    private static void saveToFile() {
        try (java.io.PrintWriter writer = new java.io.PrintWriter(new java.io.FileWriter("pets.txt"))) {
            for (Pet p : petDatabase) {
                String type = (p instanceof Dog) ? "Dog" : "Cat";
                writer.println(type + "," + p.toFileString());
            }
            System.out.println("Data successfully saved to pets.txt.");
        } catch (java.io.IOException e) {
            System.out.println("Error saving data: " + e.getMessage());
        }
    }


    private static void loadFromFile() {
        java.io.File file = new java.io.File("pets.txt");
        if (!file.exists()) return;

        try (java.util.Scanner fileScanner = new java.util.Scanner(file)) {
            while (fileScanner.hasNextLine()) {
                String[] data = fileScanner.nextLine().split(",");
                String type = data[0];
                int id = Integer.parseInt(data[1]);
                String name = data[2];
                int age = Integer.parseInt(data[3]);

                if (type.equals("Dog")) {
                    petDatabase.add(new Dog(id, name, age, "Unknown"));
                } else {
                    petDatabase.add(new Cat(id, name, age, true));
                }
            }
        } catch (Exception e) {
            System.out.println("Error loading data: " + e.getMessage());
        }
    }

    private static void exportToCSV() {
        try (java.io.PrintWriter writer = new java.io.PrintWriter(new java.io.FileWriter("exported_pets.csv"))) {
            writer.println("Type,ID,Name,Age,Status");
            for (Pet p : petDatabase) {
                String type = (p instanceof Dog) ? "Dog" : "Cat";
                writer.println(type + "," + p.toFileString());
            }
            System.out.println("Data exported to exported_pets.csv!");
        } catch (java.io.IOException e) {
            System.out.println("Export failed: " + e.getMessage());
        }
    }

    private static int getValidInt() {
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.print("Invalid input! Please enter a number: ");
            }
        }
    }
}
