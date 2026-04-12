# Pet Adoption Database System

### Student Information
* **Name:** Fanisova Raniia
* **University:** Ala-Too International University
* **Course:** Programming Language II (Java)

---

## Project Description
A robust console-based Java application for managing pet adoption records. The system now features permanent data storage, input safety mechanisms, and data export capabilities.

## Key Features (Implemented)
1. **Full CRUD Lifecycle:** Create, Read, Update, and Delete pet records.
2. **Data Persistence:** All records are automatically saved to `pets.txt` upon exit and reloaded on startup.
3. **Advanced Error Handling:** Implementation of `try-catch` blocks to handle File I/O exceptions and invalid user inputs.
4. **Input Validation:** A custom validation engine prevents application crashes from incorrect data types (e.g., entering text where a number is required).
5. **CSV Export:** Support for exporting the entire database to a `.csv` format for external use in Excel.
6. **OOP Principles:** * **Inheritance:** Specialized `Dog` and `Cat` classes extending a base `Pet` class.
    * **Polymorphism:** Method overriding for pet-specific sounds and behaviors.
    * **Encapsulation:** Private fields protected by public getters and setters.

## Technical Details
* **Language:** Java 25
* **Storage:** Local file-based system (`.txt`, `.csv`).
* **Architecture:** Modular design with separation of concerns.

## How to Run
1. Open the project in IntelliJ IDEA.
2. Run `Main.java`.
3. Use the menu to manage pets. **Important:** Always use option '0' to exit to ensure data is saved correctly.