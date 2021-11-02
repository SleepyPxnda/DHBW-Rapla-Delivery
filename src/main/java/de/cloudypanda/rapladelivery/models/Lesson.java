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

    public long getStartTime() {
        return startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public String getLastChanged() {
        return lastChanged;
    }

    public String getTitle() {
        return title;
    }

    public String getProfessor() {
        return professor;
    }

    public String getInfo() {
        return info;
    }

    public String getComment() {
        return comment;
    }
}
