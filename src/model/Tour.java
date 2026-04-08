package model;

import java.util.Date;
import utils.DateUtils;

public class Tour {

    public static final String TOUR_HEADER_FORMAT = "| %-8s | %-20s | %-18s | %5s | %-8s | %-12s | %-12s | %6s | %-6s | %6s |\n";
    public static final String TOUR_BODY_FORMAT = "| %-8s | %-20s | %-18s | %5d | %-8s | %-12s | %-12s | %6d | %-6s | %6d |\n";

    private String id;
    private String name;
    private String time;
    private int price;
    private String homeId;
    private Date departureDate;
    private Date endDate;
    private int numberTourist;
    private boolean booking = false;

    public Tour() {
    }

    public Tour(String id, String name, String time, int price, String homeId, Date departureDate, Date endDate, int numberTourist, boolean booking) {
        this.id = id;
        this.name = name;
        this.time = time;
        this.price = price;
        this.homeId = homeId;
        this.departureDate = departureDate;
        this.endDate = endDate;
        this.numberTourist = numberTourist;
        this.booking = booking;
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

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getHomeId() {
        return homeId;
    }

    public void setHomeId(String homeId) {
        this.homeId = homeId;
    }

    public Date getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(Date departureDate) {
        this.departureDate = departureDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public int getNumberTourist() {
        return numberTourist;
    }

    public void setNumberTourist(int numberTourist) {
        this.numberTourist = numberTourist;
    }

    public boolean isBooking() {
        return booking;
    }

    public void setBooking(boolean booking) {
        this.booking = booking;
    }
    
    public int getTotalAmount(){
        return price * numberTourist;
    }

    @Override
    public String toString() {
        return String.format(TOUR_BODY_FORMAT, id, name, time, price, homeId, DateUtils.dateToString(departureDate), DateUtils.dateToString(endDate), numberTourist, booking ? "Yes" : "No", getTotalAmount());
    }

}
