package controller;

import fileio.IFileReadWrite;
import fileio.TourFileHelper;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import model.Tour;

public class TourController {

    private List<Tour> list = new ArrayList<>();

    public TourController() {
        try {
            IFileReadWrite file = new TourFileHelper();
            list = file.read();
        } catch (Exception e) {
            System.err.println("Error: Required file Tours.txt not found!");
            System.exit(1);
        }
    }

    public boolean save() {
        try {
            IFileReadWrite<Tour> file = new TourFileHelper();
            return file.write(list);
        } catch (Exception e) {
            return false;
        }
    }

    public void addNew(Tour tour) {
        list.add(tour);
    }

    // isDuplicateDate for adding
    public Tour isDuplicateDate(String homeId, Date newStart, Date newEnd) {
        for (Tour tour : this.getAll()) {

            if (tour.getHomeId().trim().equalsIgnoreCase(homeId.trim())) {
                if (!newStart.after(tour.getEndDate()) && !newEnd.before(tour.getDepartureDate())) {
                    return tour;
                }
            }
        }
        return null;
    }

    // isDuplicateDate for updating
    public Tour isDuplicateDate(String homeId, Date newStart, Date newEnd, String ignoreTourId) {
        for (Tour tour : this.getAll()) {
            // Ignore the tour that is updating
            if (tour.getId().equals(ignoreTourId)) {
                continue;
            }

            if (tour.getHomeId().trim().equalsIgnoreCase(homeId.trim())) {
                if (!newStart.after(tour.getEndDate()) && !newEnd.before(tour.getDepartureDate())) {
                    return tour;
                }
            }
        }
        return null;
    }

    public boolean update(Tour newTour) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getId().equalsIgnoreCase(newTour.getId())) {
                list.set(i, newTour);
                return true;
            }
        }
        return false;
    }

    public List<Tour> getAll() {
        return list;
    }

    public List<Tour> getCompletedTour() {
        Date now = new Date();

        List<Tour> result = list.stream()
                .filter(item -> item.getDepartureDate().before(now))
                .collect(Collectors.toList());

        return result;
    }

    public List<Tour> getTotalAmountFutureTours() {
        Date now = new Date();

        List<Tour> result = list.stream()
                //check is must be in future and isBooked
                .filter(item -> item.getDepartureDate().after(now))
                .collect(Collectors.toList());

        //Sort descending order by TotalMount
        Collections.sort(result, (Tour t1, Tour t2) -> Double.compare(t2.getTotalAmount(), t1.getTotalAmount()));

        return result;
    }

    public String getTourHeader() {
        String line = "------------------------------------------------------------------------------------------------------------------------------------\n";

        String header = String.format(Tour.TOUR_HEADER_FORMAT,
                "ID", "Tour Name", "Duration", "Price", "HomeID", "Start", "End", "Slots", "Booked", "Total");

        return line + header + line;
    }

    public Tour findById(String id) {
        for (Tour m : list) {
            if (m.getId().equalsIgnoreCase(id)) {
                return m;
            }
        }
        return null;
    }
}
