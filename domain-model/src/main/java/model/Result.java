package model;

import java.io.File;
import java.util.UUID;

public class Result implements Comparable<Result>{
    private final UUID uuid;

    private int place;

    private String fireDepartment;

    private double time;

    private int mistakePoints;

    private double finalScore;

    private File image;

    public Result(UUID uuid, int place, String fireDepartment, double time, int mistakePoints, double finalScore, File image) {
        this.uuid = uuid;
        this.place = place;
        this.fireDepartment = fireDepartment;
        this.time = time;
        this.mistakePoints = mistakePoints;
        this.finalScore = finalScore;
        this.image = image;
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

    public File getImage() {
        return image;
    }

    public void setImage(File image) {
        this.image = image;
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
                .append("imagePath: ").append(image.getAbsoluteFile());

        return builder.toString();
    }

    public int compareTo(Result other) {
        return -1 * Double.compare(finalScore, other.finalScore);
    }
}
