package model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class Note {
    public static ArrayList<Note> notesList;
    private String noteTitle;
    private String noteText;
    private String noteCompDate;

    public Note(String noteTitle, String noteText, String noteCompDate) {
        this.noteTitle = noteTitle;
        this.noteText = noteText;
        this.noteCompDate = noteCompDate;
    }

    public Note(){};

    public String getNoteTitle() {
        return noteTitle;
    }

    public String getNoteText() {
        return noteText;
    }

    public String getNoteCompDate() {
        return noteCompDate;
    }

    public void setNoteTitle(String noteTitle) {
        this.noteTitle = noteTitle;
    }

    public void setNoteText(String noteText) {
        this.noteText = noteText;
    }

    public void setNoteCompDate(String noteCompDate) {
        this.noteCompDate = noteCompDate;
    }

    public static void saveAll(ArrayList<Note> list){
        notesList = list;
    }

    public static ArrayList<Note>  getAll(){
        return notesList;
    }
}
