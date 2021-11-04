package de.cloudypanda.rapladelivery.models;

public class Lesson {

    long startTime;
    long endTime;
    String title;
    String professor;

    public Lesson(long startTime, long endTime, String title, String professor) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.title = title;
        this.professor = professor;
    }

    @Override
    public String toString() {
        return "Lesson{" +
                "startTime=" + startTime +
                ", endTime=" + endTime +
                ", title='" + title + '\'' +
                ", professor='" + professor + '\'' +
                '}';
    }

    public long getStartTime() {
        return startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public String getTitle() {
        return title;
    }

    public String getProfessor() {
        return professor;
    }
}
