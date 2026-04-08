package fileio;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import model.Booking;
import utils.DateUtils;

public class BookingFileHelper implements IFileReadWrite<Booking> {

    private final String FILE_NAME = "src/fileio/Bookings.txt";
    


    @Override
    public List<Booking> read() throws Exception {
        List<Booking> list = new ArrayList<>();
        File f;
        FileInputStream file = null;
        BufferedReader myInput = null;// create Buffer    
        try {
            f = new File(FILE_NAME);//open file
            String fullPath = f.getAbsolutePath(); //get Fullpath of file
            file = new FileInputStream(fullPath);
            myInput = new BufferedReader(new InputStreamReader(file));
            // read line until the end of the file
            String line = null;
            while ((line = myInput.readLine()) != null) {
                //Remove BOM in text file
                if (line.startsWith("\uFEFF")) {
                    line = line.substring(1);
                }

                if (line.trim().isEmpty()) {
                    continue;
                }
                Booking booking = convertToBooking(line);
                list.add(booking);
            }

        } catch (IOException ex) {
            throw ex;
        } finally {
            if (myInput != null) {
                myInput.close();
            }
            if (file != null) {
                file.close();
            }
        }
        return list;
    }

    @Override
    public boolean write(List<Booking> list) throws Exception {
        File f;
        FileOutputStream file = null;
        BufferedWriter myOutput = null;// create Buffer    
        try {
            f = new File(FILE_NAME);//open file
            String fullPath = f.getAbsolutePath(); //get Fullpath of file
            file = new FileOutputStream(fullPath);
            myOutput = new BufferedWriter(new OutputStreamWriter(file));
            // write line until the end of the file
            for (Booking booking : list) {
                String str = convertToString(booking);
                myOutput.write(str);
                myOutput.newLine();
            }
        } catch (IOException ex) {
            throw ex;
        } finally {
            if (myOutput != null) {
                myOutput.close();
            }
            if (file != null) {
                file.close();
            }
        }
        return true;
    }

    private Booking convertToBooking(String str) throws ParseException {
        if (str == null || str.trim().isEmpty()) {
            return null;
        }
        Booking booking = null;

        String[] p = str.split(",");
        String id = p[0].trim();
        String fullName = p[1].trim();
        String tourId = p[2].trim();
        Date bookingDate = DateUtils.stringToDate(p[3].trim());
        String phone = p[4].trim();

        booking = new Booking(id, fullName, tourId, bookingDate, phone);
        return booking;
    }
    
    private String convertToString(Booking booking){
        return booking.getId() + "," + 
               booking.getFullName() + "," +
               booking.getTourId()+ "," + 
               DateUtils.dateToString(booking.getBookingDate()) + "," + 
               booking.getPhone();
    }
}
