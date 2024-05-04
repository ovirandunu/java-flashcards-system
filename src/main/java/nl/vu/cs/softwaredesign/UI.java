package nl.vu.cs.softwaredesign;

import java.util.Scanner;

public class UI {
    private static final UI instance = new UI();
    private final Scanner in = new Scanner(System.in);

    private UI() {}

    public static UI getInstance() {
        return instance;
    }

    public void run() {
        LearningSystem system = LearningSystem.getInstance();
        system.initialize();
        printWelcomeMessage();

        boolean loop = true;
        while (loop) {
            System.out.print("\nEnter command: ");
            String command = in.nextLine().trim().toLowerCase();
            switch (command) {
                case "add":
                    handleAddLevel(system);
                    break;
                case "remove":
                    handleRemoveLevel(system);
                    break;
                case "update":
                    handleUpdateLevel(system);
                    break;
                case "play":
                    handlePlayLevel(system);
                    break;
                case "print":
                    system.displayLevels();
                    break;
                case "info":
                    printInfo();
                    break;
                case "exit":
                    loop = false;
                    break;
                default:
                    System.out.println("Invalid command. Type 'info' for available commands.");
                    break;
            }
        }
    }

    private void handleAddLevel(LearningSystem system) {
        System.out.print("Enter title for new level: ");
        String title = in.nextLine();
        system.createLevel(title);
        System.out.println("Level \"" + title + "\" added.");
    }

    private void handleRemoveLevel(LearningSystem system) {
        system.displayLevels();
        int id = askForInt("Enter ID of level to delete: ");
        system.deleteLevel(id);
        System.out.println("Level with ID " + id + " has been deleted.");
    }

    private void handleUpdateLevel(LearningSystem system) {
        int id = askForInt("Enter ID of level to update: ");
        system.updateLevel(id);
        System.out.println("Level with ID " + id + " has been updated.");
    }

    private void handlePlayLevel(LearningSystem system) {
        int id = askForInt("Enter ID of level to play: ");
        system.playLevel(id);
        System.out.println("Playing level with ID " + id + ".");
    }

    private int askForInt(String message) {
        while (true) {
            System.out.print(message);
            String input = in.nextLine();
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input, please enter a number.");
            }
        }
    }

    private void printWelcomeMessage() {
        System.out.println("Welcome to the Learning System");
        printInfo();
    }

    private void printInfo() {
        System.out.println("Available commands:\n" +
                "- add: Add a new level\n" +
                "- remove: Remove a level\n" +
                "- print: Print all levels\n" +
                "- update: Update a level\n" +
                "- play: Play a level\n" +
                "- info: Show this message again\n" +
                "- exit: Exit the program");
    }
}
