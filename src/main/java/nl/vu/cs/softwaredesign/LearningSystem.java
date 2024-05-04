package nl.vu.cs.softwaredesign;

import nl.vu.cs.softwaredesign.util.JsonUtil;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class LearningSystem {
    private int numLevels;
    private Map<Integer, Level> levels;
    private Random random = new Random();

    private static final String LEVELS_DIR = System.getProperty("user.home") + File.separator + "learning_system_levels"; // Dedicated directory to save to
    private static LearningSystem instance = new LearningSystem();

    private LearningSystem() {
        this.numLevels = 0;
        this.levels = new HashMap<>();
        new File(LEVELS_DIR).mkdirs(); // Ensure dedicated directory exists
    }

    public void initialize() {
        loadLevelsFromJSON();
    }

    // Load levels from the dedicated directory
    private void loadLevelsFromJSON() {
        try (Stream<Path> paths = Files.walk(Paths.get(LEVELS_DIR))) {
            paths.filter(Files::isRegularFile)
                    .forEach(filePath -> {
                        Level level = JsonUtil.loadLevelFromJSON(filePath.toString());
                        if(level != null) {
                            Integer levelID = Integer.parseInt(filePath.getFileName().toString().replace(".json", ""));
                            levels.put(levelID, level);
                            numLevels++;
                            System.out.println("Identified level with ID: " + levelID);
                        }
                    });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private List<String> getResourceFiles(String path) throws IOException {
        List<String> filenames = new ArrayList<>();

        try (InputStream in = getClass().getResourceAsStream(path)) {
            if (in != null) {
                BufferedReader br = new BufferedReader(new InputStreamReader(in));
                filenames = br.lines().collect(Collectors.toList());
            }
        }

        return filenames;
    }

    private Integer generateLevelID() {
        while (true) {
            int randomInt = random.nextInt(99);
            if (!levels.containsKey(randomInt)) {
                return randomInt;
            }
        }
    }

    public static LearningSystem getInstance() {
        return instance;
    }

    public void playLevel(int levelID) {
        if(!levels.containsKey(levelID)){
            System.out.println("Level with this ID doesn't exist, type new command.");
            return;
        }
        Level currentLevel = levels.get(levelID);
        currentLevel.startGame();
    }

    public void createLevel(String title) {
        Integer levelID = generateLevelID();
        Level level = new Level(title);
        levels.put(levelID, level);
        numLevels++;
        System.out.println("Level created: saving ...");
        JsonUtil.saveLevelToJSON(level, LEVELS_DIR + File.separator + levelID + ".json");
        System.out.println("Level Saved Successfully! " + levelID);
    }

    public void deleteLevel(Integer levelID) {
        if (levels.containsKey(levelID)) {
            levels.remove(levelID);
            numLevels--;
            System.out.println("Level deleted");
            new File(LEVELS_DIR, levelID + ".json").delete();
        } else {
            System.out.println("Level with this ID doesn't exist");
        }
    }

    public void updateLevel(int levelID) {
        if(!this.levels.containsKey(levelID)){
            System.out.println("Doesn't have this level, returning to the levels...");
            return;
        }
        Scanner in = new Scanner(System.in);
        Level currentLevel = levels.get(levelID);
        String info = "('add', 'remove' or 'exit')";
        System.out.println("What would you like to do? " + info);
        while (true) {
            String command = in.nextLine();
            try {
                switch (command) {
                    case "add":
                        System.out.println("Question: ");
                        String question = in.nextLine();
                        System.out.println("Answer: ");
                        String answer = in.nextLine();
                        currentLevel.addFlashcard(question, answer);
                        // Update the path to use LEVELS_DIR for saving
                        JsonUtil.saveLevelToJSON(currentLevel, LEVELS_DIR + File.separator + levelID + ".json");
                        System.out.println("Flashcard Added and level updated.");
                        break;

                    case "remove":
                        System.out.println("In remove");
                        currentLevel.displayFlashcards();
                        System.out.println("Give an ID for which one to delete...");
                        try {
                            String input = in.nextLine();
                            Integer id = Integer.parseInt(input);
                            currentLevel.removeFlashcard(id);
                            // Update the path to use LEVELS_DIR for saving
                            JsonUtil.saveLevelToJSON(currentLevel, LEVELS_DIR + File.separator + levelID + ".json");
                            System.out.println("Flashcard has been deleted and level updated.");
                        } catch (NumberFormatException e) {
                            System.out.println("Please enter a valid integer ID.");
                        }
                        break;
                    case "info":
                        System.out.println(info);
                        break;

                    case "exit":
                        System.out.println("Exiting level update...");
                        return;

                    default:
                        System.out.println("Wrong expression, please try again!");
                        break;
                }
            } catch (Exception e) {
                System.out.println("An error occurred: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }


    public void displayLevels(){
        if (levels.isEmpty()) {
            System.out.println("There are no levels in the system");
            return;
        }
        System.out.println("Displaying all levels:");
        for (Map.Entry<Integer, Level> entry : levels.entrySet()) { //use iterator when have it implemented
            Integer levelID = entry.getKey();
            System.out.println("ID: " + levelID);
            // Or you could directly access question and answer if you prefer:
        }
    }
}

