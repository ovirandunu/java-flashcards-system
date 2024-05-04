package nl.vu.cs.softwaredesign;

import nl.vu.cs.softwaredesign.util.FlashcardIterator;

import java.util.*;

public class Level {
    private int numFlashcards;
    private final String title;
    private Map<Integer, Flashcard> flashcards;
    private Random random = new Random();
    private Game score = new Game();

    public Level(String title){
        this.title = title;
        this.numFlashcards = 0;
        this.flashcards = new HashMap<>();
    }

    private Integer generateFlashcardID(){
        while(true){
            int rand = random.nextInt(99);  // returns pseudo-random value between 0 and 99
            System.out.println("The new level flashcardID is " + rand);
            if(!flashcards.containsKey(rand)){
                return rand;
            }
        }
    }

    public String getTitle() {
        return title;
    }

    public void addFlashcard(String question, String answer){
        if(this.numFlashcards == 100){
            System.out.println("Max has been reached!");
            return;
        }
        Integer flashcardID = generateFlashcardID();
        Flashcard card = new Flashcard(question, answer);
        flashcards.put(flashcardID, card);
        this.numFlashcards ++;
        score.setTotalPoints(this.numFlashcards);
    }

    public void removeFlashcard(Integer flashcardID){
        if(!flashcards.containsKey(flashcardID)){
            System.out.println("Flashcard with this ID doesn't exist");
            return;
        }
        flashcards.remove(flashcardID);
        this.numFlashcards --;
        score.setTotalPoints(this.numFlashcards);
    }

    public void displayFlashcards() {
        if (flashcards.isEmpty()) {
            System.out.println("There are no flashcards in this level.");
            return;
        }
        int flashcardCount = 0;
        FlashcardIterator iterator = new FlashcardIterator(flashcards);
        System.out.println("Displaying all flashcards in the level: " + this.title);
        while (iterator.hasNext()) {
            flashcardCount++;
            Flashcard card = iterator.next();
            System.out.println("Flashcard #" + flashcardCount + ", Question: " + card.getQuestion() + ", Answer: " + card.getAnswer());
        }
    }

    public void startGame() {
        if (flashcards.isEmpty()) {
            System.out.println("There are no flashcards in this level.");
            return;
        }
        FlashcardIterator iterator = new FlashcardIterator(flashcards);
        System.out.println("Enter the corresponding answers for the questions!");
        while (iterator.hasNext()) {
            Flashcard card = iterator.next();
            displayQuestion(card);
            boolean correct = checkAnswer(card);
            if (correct) {
                this.score.increaseCorrectAnswers();
                System.out.println("Correct answer, keep it up!");
            }
        }
        score.giveFeedback(); // Assuming giveFeedback is implemented elsewhere
    }

    private void displayQuestion(Flashcard currentCard){
        System.out.println("Question: " + currentCard.getQuestion());
    }

    private boolean checkAnswer(Flashcard currentCard){
        Scanner in = new Scanner(System.in);
        String answer = currentCard.getAnswer();
        String input = in.nextLine();
        return answer.equals(input);
    }
}
