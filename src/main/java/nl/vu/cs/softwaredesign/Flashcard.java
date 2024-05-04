package nl.vu.cs.softwaredesign;

public class Flashcard {

    private final String question;
    private final String answer;

    public Flashcard(String question, String answer){
        this.answer = answer;
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public String getQuestion() {
        return question;
    }
}

