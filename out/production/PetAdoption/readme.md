# Pet Adoption Database System (v2.0 - GUI Enhanced)

### Student Information
* **Name:** Fanisova Raniia
* **University:** Ala-Too International University
* **Course:** Programming Language II (Java)

---

## 1. Project Description
This is a high-level Java application designed for animal shelters. It features a dual-interface system: a robust **Command Line Interface (CLI)** for backend management and a **Graphical User Interface (GUI)** for visual data interaction. The system includes advanced security with user roles and persistent storage.

## 2. Project Objectives
* **User Experience:** Provide both console and window-based interaction.
* **Security:** Implement role-based access control (Admin/Guest).
* **Data Reliability:** Ensure zero data loss using automated file synchronization.
* **Code Quality:** Demonstrate advanced OOP concepts (Polymorphism, Inheritance, Encapsulation).

## 3. Key Requirements Implemented (Project Requirement List)
1.  **Dual Interface:** Both CLI and GUI versions functional.
2.  **Authentication:** Secure login system with password protection.
3.  **Role-Based Access:** Admin (Full control) vs Guest (View-only) permissions.
4.  **Full CRUD:** Create, Read, Update, and Delete pets via console or window.
5.  **Advanced Validation:** Custom methods to prevent crashes from invalid data types.
6.  **Persistence:** Auto-save/load functionality via `pets.txt`.
7.  **Data Export:** One-click CSV export for external reporting.
8.  **Inheritance:** Hierarchical structure (`Pet` -> `Dog`/`Cat`).
9.  **Polymorphism:** Overridden methods for specific pet sounds and file formatting.
10. **Error Handling:** Robust `try-catch` blocks throughout the application logic.

## 4. Documentation of Algorithms & Data Structures
### Data Structures
The core engine uses an `ArrayList<Pet>`. In the GUI, this is synchronized with a `DefaultTableModel`. This "Model-View" approach ensures that adding a pet to the backend immediately updates the `JTable` visual display.

### Key Algorithms
* **Role Logic:** When a user logs in, a `currentRole` string is set. The `switch-case` menu in the CLI and the buttons in the GUI check this string before executing "Add" or "Delete" commands.
* **Event Handling (GUI):** We use **ActionListeners** with Lambda expressions to capture button clicks. When "Add Dog" is clicked, a `JOptionPane` sequence gathers data, validates it, and pushes it to the table.
* **Input Sanitization:** The `getValidBoolean()` and `getValidInt()` algorithms use recursive loops to ensure the program never crashes from an `InputMismatchException`.

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