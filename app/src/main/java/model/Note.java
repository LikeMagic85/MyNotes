package model;

import android.os.Parcel;
import android.os.Parcelable;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class Note implements Parcelable {
    public static ArrayList<Note> notesList = new ArrayList<>();
    private String noteTitle;
    private String noteText;
    private String noteCompDate;

    public Note(String noteTitle, String noteText, String noteCompDate) {
        this.noteTitle = noteTitle;
        this.noteText = noteText;
        this.noteCompDate = noteCompDate;
    }

    public Note(){};

    protected Note(Parcel in) {
        noteTitle = in.readString();
        noteText = in.readString();
        noteCompDate = in.readString();
    }

    public static final Creator<Note> CREATOR = new Creator<Note>() {
        @Override
        public Note createFromParcel(Parcel in) {
            return new Note(in);
        }

        @Override
        public Note[] newArray(int size) {
            return new Note[size];
        }
    };

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

    public static void saveNote(Note note){
        notesList.add(note);
    }

    public static ArrayList<Note>  getAll(){
        return notesList;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(noteTitle);
        parcel.writeString(noteText);
        parcel.writeString(noteCompDate);
    }

    public static Note getNote(int index){
        return notesList.get(index);
    };
}
