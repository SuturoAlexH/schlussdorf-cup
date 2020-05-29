package model;

import java.util.UUID;

public class Result implements Comparable<Result>{
    private final UUID uuid;

    private int place;

    private String fireDepartment;

    private double time;

    private int mistakePoints;

    private double finalScore;

    private String imagePath;

    public Result(UUID uuid, int place, String fireDepartment, double time, int mistakePoints, double finalScore, String imagePath) {
        this.uuid = uuid;
        this.place = place;
        this.fireDepartment = fireDepartment;
        this.time = time;
        this.mistakePoints = mistakePoints;
        this.finalScore = finalScore;
        this.imagePath = imagePath;
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getFireDepartment() {
        return fireDepartment;
    }

    public void setFireDepartment(String fireDepartment) {
        this.fireDepartment = fireDepartment;
    }

    public int getPlace() {
        return place;
    }

    public void setPlace(int place) {
        this.place = place;
    }

    public double getTime() {
        return time;
    }

    public void setTime(double time) {
        this.time = time;
    }

    public int getMistakePoints() {
        return mistakePoints;
    }

    public void setMistakePoints(int mistakePoints) {
        this.mistakePoints = mistakePoints;
    }

    public double getFinalScore() {
        return finalScore;
    }

    public void setFinalScore(double finalScore) {
        this.finalScore = finalScore;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    @Override
    public boolean equals(final Object other){
        if(other instanceof Result){
            Result otherResult = (Result) other;
            return uuid.equals(otherResult.uuid);
        }

        return false;
    }

    @Override
    public String toString(){
        StringBuilder builder = new StringBuilder();

        builder.append("uuid: ").append(uuid).append(", ")
                .append("place: ").append(place).append(", ")
                .append("time: ").append(time).append(", ")
                .append("fireDepartment: ").append(fireDepartment).append(", ")
                .append("mistakePoints: ").append(mistakePoints).append(", ")
                .append("time: ").append(time).append(", ")
                .append("finalScore: ").append(finalScore).append(", ")
                .append("imagePath: ").append(imagePath);

        return builder.toString();
    }

    public int compareTo(Result other) {
        return -1 * Double.compare(finalScore, other.finalScore);
    }
}
