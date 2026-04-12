# Pet Adoption Database System

### Student Information
* **Name:** Fanisova Raniia
* **University:** Ala-Too International University
* **Course:** Programming Language II (Java)

---

## 1. Project Description
This is a console-based Java application designed to manage records of pets available for adoption. The system acts as a centralized database for shelter administrators to track different types of animals, manage their detailed profiles, and handle the adoption lifecycle through a structured Command Line Interface (CLI).

## 2. Project Objectives
* **Functional CRUD Implementation:** Create a reliable system to add, view, update, and remove pet records.
* **OOP Mastery:** Demonstrate professional use of Encapsulation, Inheritance, and Polymorphism.
* **Data Integrity:** Ensure that information is never lost by using file-based persistent storage.
* **Software Stability:** Implement robust input validation to prevent runtime crashes.

## 3. Key Requirements Implemented (Project Requirement List)
To achieve the project goals, the following 10+ requirements were successfully integrated:
1.  **Create:** Add new Dog or Cat profiles with unique attributes.
2.  **Read:** Display a formatted list of all pets currently in the system.
3.  **Update:** Modify existing records (name, age) based on a specific ID.
4.  **Delete:** Remove records once a pet is adopted or transferred.
5.  **Data Persistence:** Automatic saving to `pets.txt` upon program termination.
6.  **Data Import:** Automatic loading of existing records when the app starts.
7.  **Export Capability:** Exporting database records to a standard `.csv` format.
8.  **Input Validation:** Using a validation engine to filter out non-numeric inputs for IDs and ages.
9.  **Encapsulation:** All class fields are `private` with access controlled via `getters` and `setters`.
10. **Inheritance:** A hierarchical structure with a `Pet` parent class and specialized `Dog`/`Cat` children.
11. **Polymorphism:** Method overriding for the `makeSound()` function to trigger breed-specific sounds.

## 4. Documentation of Algorithms & Functions
### Data Structures
The core of the system is an `ArrayList<Pet>`. This dynamic array was chosen because it allows for efficient sequential access and flexible resizing as the shelter grows.

### Key Algorithms & Logic
* **Validation Algorithm:** The `getValidInt()` function uses an infinite `while` loop combined with a `try-catch` block. It attempts to parse a string into an integer; if a `NumberFormatException` occurs, it catches the error and prompts the user again instead of crashing.
* **File I/O Logic:**
    * **Saving:** The system iterates through the `ArrayList` and converts each object into a comma-separated string using a custom `toFileString()` method.
    * **Loading:** Upon startup, the `Scanner` reads the file line-by-line, splits the string into parts, and uses conditional logic to reconstruct the correct object type (Dog or Cat).

### Challenges Faced

During the development of the Pet Adoption Database System, several technical challenges were encountered and successfully resolved:

* **Polymorphic Object Reconstruction from Files:**
    * **Problem:** When loading data from `pets.txt`, the application receives raw strings. The challenge was to correctly reconstruct the specific subclass (`Dog` or `Cat`) from a generic text line while preserving unique attributes like "breed" or "indoor status".
    * **Solution:** I implemented a parsing algorithm that reads a type identifier at the beginning of each line. Using conditional logic, the system invokes the appropriate constructor to recreate the object, ensuring that polymorphism remains functional throughout the session.

* **Data Persistence & Integrity:**
    * **Problem:** Risk of data loss if the program is closed unexpectedly (e.g., manually stopping the process in the IDE) before the `ArrayList` is saved.
    * **Solution:** I established a strict "save-on-exit" protocol. The `saveToFile()` method is integrated directly into the main control loop's exit sequence. This ensures that the current state of the database is always synchronized with the local storage before the application terminates.

* **Input Buffer and Scanner Management:**
    * **Problem:** One of the most common issues in Java CLI applications is the "buffer skip" effect when switching between `nextInt()` and `nextLine()`, which often leads to the program skipping user input prompts.
    * **Solution:** To resolve this, I developed a unified `getValidInt()` method. Instead of using `nextInt()`, it reads the entire line as a String and parses it into an Integer. This approach clears the scanner buffer completely and prevents input misalignment.

* **Robust Input Validation:**
    * **Problem:** The application was initially prone to crashing if a user entered non-numeric characters (like letters) when an ID or age was required.
    * **Solution:** I implemented an error-handling mechanism using `try-catch` blocks within an infinite loop. The system now gracefully catches `NumberFormatException`, providing a user-friendly error message and reprompting the user instead of terminating the program.