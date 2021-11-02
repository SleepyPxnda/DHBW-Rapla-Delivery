package de.cloudypanda.rapladelivery.models;

public class Lesson {

    long startTime;
    long endTime;
    String lastChanged;
    String title;
    String professor;
    String info;
    String comment;

    public Lesson(long startTime, long endTime, String lastChanged, String title, String professor, String info, String comment) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.lastChanged = lastChanged;
        this.title = title;
        this.professor = professor;
        this.info = info;
        this.comment = comment;
    }

    @Override
    public String toString() {
        return "Lesson{" +
                "startTime=" + startTime +
                ", endTime=" + endTime +
                ", lastChanged='" + lastChanged + '\'' +
                ", title='" + title + '\'' +
                ", professor='" + professor + '\'' +
                ", info='" + info + '\'' +
                ", comment='" + comment + '\'' +
                '}';
    }
}
