package de.cloudypanda.rapladelivery;

public class Lesson {

    long weekNumber;
    String day;
    String lastChanged;
    String beginn;
    String end;
    String title;
    String professor;
    String info;
    String comment;

    public Lesson(long weekNumber, String day, String lastChanged, String beginn, String end, String title, String professor, String info, String comment) {
        this.weekNumber = weekNumber;
        this.day = day;
        this.lastChanged = lastChanged;
        this.beginn = beginn;
        this.end = end;
        this.title = title;
        this.professor = professor;
        this.info = info;
        this.comment = comment;
    }

    @Override
    public String toString() {
        return "Class{" +
                "weekNumber='" + weekNumber + '\'' +
                ", day='" + day + '\'' +
                ", lastChanged='" + lastChanged + '\'' +
                ", beginn='" + beginn + '\'' +
                ", end='" + end + '\'' +
                ", title='" + title + '\'' +
                ", professor='" + professor + '\'' +
                ", info='" + info + '\'' +
                ", comment='" + comment + '\'' +
                '}';
    }
}
