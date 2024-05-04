package nl.vu.cs.softwaredesign;

public class Achievement {
    private String description;

    public Achievement (int num){
            switch (num){
                case 1:
                    this.description = "Amazing, you got a perfect score.";
                    break;

                case 2:
                    this.description = "Needs a little more practice.";
                    break;

                case 3:
                    this.description = "Good, but you can do better than this.";
                    break;

                case 4:
                    this.description = "Needs a lot more practice.";
                    break;

                default:
                    break;
            }
    }

    public String getDescription() {
        return description;
    }
}

