package scenes;

import java.time.chrono.ChronoLocalDateTime;

public class Booking {
    Integer bookingID;
    String mail;
    String starttime;
    String endtime;
    String room;

    public Booking(Integer bookingID, String mail, String starttime, String endtime, String room) {
        this.bookingID = bookingID;
        this.mail = mail;
        this.starttime = starttime;
        this.endtime = endtime;
        this.room = room;
    }

    public Integer getBookingID() {
        return bookingID;
    }

    public void setBookingID(Integer bookingID) {
        this.bookingID = bookingID;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getStarttime() {
        return starttime;
    }

    public void setStarttime(String starttime) {
        this.starttime = starttime;
    }

    public String getEndtime() {
        return endtime;
    }

    public void setEndtime(String endtime) {
        this.endtime = endtime;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }
}
