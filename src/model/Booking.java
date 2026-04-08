
package model;

import java.util.Date;
import utils.DateUtils;

public class Booking {
    
    public static final String BOOKING_HEADER_FORMAT = "| %-8s | %-20s | %-8s | %-12s | %10s |\n";
    public static final String BOOKING_BODY_FORMAT = "| %-8s | %-20s | %-8s | %-12s | %10s |\n";
    
    private String id;
    private String fullName;
    private String tourId;
    private Date bookingDate;
    private String phone;

    public Booking() {
    }

    public Booking(String id, String fullName, String tourId, Date bookingDate, String phone) {
        this.id = id;
        this.fullName = fullName;
        this.tourId = tourId;
        this.bookingDate = bookingDate;
        this.phone = phone;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getTourId() {
        return tourId;
    }

    public void setTourId(String tourId) {
        this.tourId = tourId;
    }

    public Date getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(Date bookingDate) {
        this.bookingDate = bookingDate;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return String.format(BOOKING_BODY_FORMAT, id, fullName, tourId, DateUtils.dateToString(bookingDate), phone);
    }
}
