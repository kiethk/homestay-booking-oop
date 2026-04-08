package controller;

import fileio.BookingFileHelper;
import fileio.IFileReadWrite;
import java.util.ArrayList;
import java.util.List;
import model.Booking;

public class BookingController {

    private List<Booking> list = new ArrayList<>();

    public BookingController() {
        try {
            IFileReadWrite file = new BookingFileHelper();
            list = file.read();
        } catch (Exception e) {
            System.err.println("Error: Required file Bookings.txt not found!");
            System.exit(1);
        }
    }

    public boolean save() {
        try {
            IFileReadWrite<Booking> file = new BookingFileHelper();
            return file.write(list);
        } catch (Exception e) {
            return false;
        }
    }

    public void addNew(Booking booking) {
        list.add(booking);
    }

    public boolean update(Booking newBooking) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getId().equalsIgnoreCase(newBooking.getId())) {
                list.set(i, newBooking);
                return true;
            }
        }
        return false;
    }
    
    public boolean deleteById(String id){
        for (Booking b : list) {
            if (b.getId().equalsIgnoreCase(id)) {
                list.remove(b);
                return true;
            }
        }
        return false;
    }
    
    public List<Booking> searchByFullName(String str){
        List<Booking> l = new ArrayList<>();
        
        if (str == null || str.trim().isEmpty()) return l;
        
        for (Booking b : list) {
            if (b.getFullName().toLowerCase().contains(str.toLowerCase())){
                l.add(b);
            }
        }
        return l;
    }

    public Booking findById(String id) {
        for (Booking b : list) {
            if (b.getId().equalsIgnoreCase(id)) {
                return b;
            }
        }
        return null;
    }
    
    public String getBookingHeader() {
        String line = "--------------------------------------------------------------------------\n";

        String header = String.format(Booking.BOOKING_HEADER_FORMAT,
                "ID", "Full Name", "Tour ID", "Booking Date", "Phone");

        return line + header + line;
    }
}
