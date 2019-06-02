package scenes;

public class Room {
Integer entry;
String roomname;
Integer chairs;
Integer size;
String equipment;

    public Room(Integer entry, String roomname, Integer chairs, Integer size, String equipment) {
        this.entry = entry;
        this.roomname = roomname;
        this.chairs = chairs;
        this.size = size;
        this.equipment = equipment;
    }


    public Integer getEntry() {
        return entry;
    }

    public void setEntry(Integer entry) {
        this.entry = entry;
    }

    public String getRoomname() {
        return roomname;
    }

    public void setRoomname(String roomname) {
        this.roomname = roomname;
    }

    public Integer getChairs() {
        return chairs;
    }

    public void setChairs(Integer chairs) {
        this.chairs = chairs;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public String getEquipment() {
        return equipment;
    }

    public void setEquipment(String equipment) {
        this.equipment = equipment;
    }
}
