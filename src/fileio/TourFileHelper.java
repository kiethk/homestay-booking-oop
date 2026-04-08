package fileio;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import model.Tour;
import utils.DateUtils;

public class TourFileHelper implements IFileReadWrite<Tour> {

    private final String FILE_NAME = "src/fileio/Tours.txt";

    @Override
    public List<Tour> read() throws Exception {
        List<Tour> list = new ArrayList<>();
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
                Tour tour = convertToTour(line);
                list.add(tour);
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
    public boolean write(List<Tour> list) throws Exception {
        File f;
        FileOutputStream file = null;
        BufferedWriter myOutput = null;// create Buffer    
        try {
            f = new File(FILE_NAME);//open file
            String fullPath = f.getAbsolutePath(); //get Fullpath of file
            file = new FileOutputStream(fullPath);
            myOutput = new BufferedWriter(new OutputStreamWriter(file, StandardCharsets.UTF_8));
            // write line until the end of the file
            for (Tour tour : list) {
                String str = convertToString(tour);
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

    private Tour convertToTour(String str) throws ParseException {
        if (str == null || str.trim().isEmpty()) {
            return null;
        }
        Tour tour = null;

        String[] p = str.split(",");
        String id = p[0].trim();
        String name = p[1].trim();
        String time = p[2].trim();
        int price = Integer.parseInt(p[3].trim().replaceAll("[^0-9]", ""));
        String homeId = p[4].trim();
        Date depatureDate = DateUtils.stringToDate(p[5].trim());
        Date endDate = DateUtils.stringToDate(p[6].trim());
        int numberTourist = Integer.parseInt(p[7].trim().replaceAll("[^0-9]", ""));
        boolean booking = "TRUE".equals(p[8].trim());

        tour = new Tour(id, name, time, price, homeId, depatureDate, endDate, numberTourist, booking);
        return tour;
    }

    private String convertToString(Tour tour) {
        return tour.getId() + ","
                + tour.getName() + ","
                + tour.getTime() + ","
                + tour.getPrice() + ","
                + tour.getHomeId() + ","
                + DateUtils.dateToString(tour.getDepartureDate()) + ","
                + DateUtils.dateToString(tour.getEndDate()) + ","
                + tour.getNumberTourist() + ","
                + (tour.isBooking() ? "TRUE" : "FALSE");
    }
}
