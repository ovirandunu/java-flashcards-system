package nl.vu.cs.softwaredesign;

public class Game {

    private int correctAnswers;
    private int totalPoints;

    public Game(){
        this.correctAnswers = 0;
        this.totalPoints = 0;
    }
    public void setTotalPoints(int total){
        this.totalPoints = total;
    }

    public void increaseCorrectAnswers(){
        correctAnswers++;
    }

    public void giveFeedback(){
       float result = (float) correctAnswers/totalPoints * 100;
       int i = 0;
       if (result == 100){
           i = 1;
       } else if (result >= 80){
           i = 2;
       } else if (result >= 70){
           i=3;
       } else {
           i=4;
       }
       Achievement achievement = new Achievement(i);
       System.out.println(achievement.getDescription());
       this.correctAnswers = 0;
    }
}
