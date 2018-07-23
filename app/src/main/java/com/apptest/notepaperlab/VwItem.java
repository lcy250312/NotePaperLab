package com.apptest.notepaperlab;

public class VwItem {
    public String idt;
    public String notedate;
    public String notetime;
    public String notecontents;
    public String color;
    public String background;
    public VwItem(String idt,String notedate, String notetime, String notecontents, String color,String background){
        this.idt = idt;
        this.notedate = notedate;
        this.notetime = notetime;
        this.notecontents = notecontents;
        this.color = color;
        this.background = background;
    }

    public String getIdt() {
        return idt;
    }

    public void setIdt(String idt) {
        this.idt = idt;
    }

    public String getNotedate() {
        return notedate;
    }

    public void setNotedate(String notedate) {
        this.notedate = notedate;
    }

    public String getNotetime() {
        return notetime;
    }

    public void setNotetime(String notetime) {
        this.notetime = notetime;
    }

    public String getNotecontents() {
        return notecontents;
    }

    public void setNotecontents(String notecontents) {
        this.notecontents = notecontents;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getBackground() {
        return background;
    }

    public void setBackground(String background) {
        this.background = background;
    }
}
