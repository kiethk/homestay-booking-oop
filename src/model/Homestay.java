
package model;
public class Homestay {
    private String id;
    private String name;
    private int roomNumber;
    private String address;
    private int maximumCapacity;

    public Homestay() {
    }

    public Homestay(String id, String name, int roomNumber, String address, int maximumCapacity) {
        this.id = id;
        this.name = name;
        this.roomNumber = roomNumber;
        this.address = address;
        this.maximumCapacity = maximumCapacity;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getMaximumCapacity() {
        return maximumCapacity;
    }

    public void setMaximumCapacity(int maximumCapacity) {
        this.maximumCapacity = maximumCapacity;
    }

    @Override
    public String toString() {
        return "Homestay{" + "id=" + id + ", name=" + name + ", roomNumber=" + roomNumber + ", address=" + address + ", maximumCapacity=" + maximumCapacity + "}\n";
    }
}
