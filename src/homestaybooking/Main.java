package homestaybooking;

import controller.BookingController;
import controller.HomestayController;
import controller.TourController;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import model.Booking;
import model.Homestay;
import model.Tour;
import utils.DateUtils;
import utils.Validator;
import view.View;

public class Main {

    private final TourController tourController = new TourController();
    private final HomestayController homestayController = new HomestayController();
    private final BookingController bookingController = new BookingController();
    private final View view = new View(new Scanner(System.in));
    private boolean isChanged = false;

    public static void main(String[] args) {
        Main main = new Main();

        while (true) {
            try {
                int choice = main.view.showMainMenu();
                switch (choice) {
                    case 1:
                        main.addNewTour();
                        break;
                    case 2:
                        main.updateTour();
                        break;
                    case 3:
                        main.showCompletedTour();
                        break;
                    case 4:
                        main.showTotalAmountFutureTours();
                        break;
                    case 5:
                        main.addNewBooking();
                        break;
                    case 6:
                        main.deleteBooking();
                        break;
                    case 7:
                        main.updateBooking();
                        break;
                    case 8:
                        main.searchByFullName();
                        break;
                    case 9:
                        main.showStatistic();
                        break;
                    case 10:
                        main.exit();
                        break;
                    default:
                        main.view.showMessage("This function is not available.");
                }
            } catch (Exception e) {
                main.view.showMessage("Invalid input. Please try again.");
            }
        }
    }

    private void addNewTour() {
        String id;
        while (true) {
            id = view.readString("Enter Tour ID: ");
            if (Validator.isValid(id, Validator.TOUR_ID)) {
                if (tourController.findById(id) == null) {
                    break;
                }
                view.showMessage("ID already exists!");
            } else {
                view.showMessage("Invalid ID format (e.g. T12345)!");
            }
        }

        String name = view.readString("Enter Name: ");
        while (name.isEmpty()) {
            name = view.readString("Name cannot by empty! Try again: ");
        }

        String time = view.readString("Enter Time: ");
        while (time.isEmpty()) {
            time = view.readString("Time cannot by empty! Try again: ");
        }

        String priceString;
        while (true) {
            priceString = view.readString("Enter Price: ");
            if (priceString.isEmpty()) {
                view.showMessage("Price cannot be empty!");
                continue;
            }
            if (!Validator.isValid(priceString, Validator.POSITIVE_INT_VALID)) {
                view.showMessage("Price must be positive number integer!");
                continue;
            }
            break;
        }
        int price = Integer.parseInt(priceString);

        String homeId;
        while (true) {
            homeId = view.readString("Enter Home ID: ");
            if (Validator.isValid(homeId, Validator.HOME_ID)) {
                if (homestayController.findById(homeId) == null) {
                    view.showMessage("There is no Homestay with ID: " + homeId);
                } else {
                    break;
                }
            } else {
                view.showMessage("Invalid ID format (e.g. HS0001)!");
            }
        }

        Date departureDate;
        Date endDate;
        while (true) {
            departureDate = view.readDate("Enter Departure Date: ");
            while (departureDate == null) {
                departureDate = view.readDate("Departure Date cannot be empty! Try again: ");
            }

            while (true) {
                endDate = view.readDate("Enter End Date: ");
                if (endDate == null) {
                    view.showMessage("End Date cannot be empty!");
                    continue;
                }

                if (endDate.before(departureDate)) {
                    view.showMessage("End Date must be after the Departure Date!");
                    continue;
                }
                break;
            }

            Tour duplicateDate = tourController.isDuplicateDate(homeId, departureDate, endDate);

            if (duplicateDate != null) {
                view.showMessage("The Homestay " + duplicateDate.getHomeId() + " is already occupied from "
                        + DateUtils.dateToString(duplicateDate.getDepartureDate()) + " to "
                        + DateUtils.dateToString(duplicateDate.getEndDate()) + ". Please choose a different time range.");
                continue;
            }
            break;
        }

        String numberTouristString;
        int numberTourist;
        while (true) {
            numberTouristString = view.readString("Enter Number of Tourist: ");
            if (numberTouristString.isEmpty()) {
                view.showMessage("Number of Tourist cannot by empty!");
                continue;
            }
            if (!Validator.isValid(numberTouristString, Validator.POSITIVE_INT_VALID)) {
                view.showMessage("Number of Tourist must be positive number integer!");
                continue;
            }

            numberTourist = Integer.parseInt(numberTouristString);
            Homestay selectedHome = homestayController.findById(homeId);
            if (numberTourist > homestayController.findById(homeId).getMaximumCapacity()) {
                view.showMessage("Capacity exceeded! This homestay can only host up to "
                        + selectedHome.getMaximumCapacity() + " tourists.");
                continue;
            }
            break;
        }

        Tour newTour = new Tour(id, name, time, price, homeId, departureDate, endDate, numberTourist, false);
        tourController.addNew(newTour);
        isChanged = true;
        view.showMessage("Add a new tour successfully!");
    }

    private void updateTour() {
        String id;
        while (true) {
            id = view.readString("Enter Tour ID to update: ");
            if (!Validator.isValid(id, Validator.TOUR_ID)) {
                view.showMessage("Invalid ID format (e.g. T12345)!");
                continue;
            }
            break;
        }

        Tour t = tourController.findById(id);

        if (t == null) {
            view.showMessage("This tour does not exist!");
            return;
        }

        //Declare variables contain old values
        String newName = t.getName();
        String newTime = t.getTime();
        int newPrice = t.getPrice();
        String newHomeId = t.getHomeId();
        Date newStart = t.getDepartureDate();
        Date newEnd = t.getEndDate();
        int newTourist = t.getNumberTourist();
        boolean newBooking = t.isBooking();

        //Inputting
        //Name
        String str = view.readString("Enter Name (Current: " + t.getName() + "): ");
        if (!str.isEmpty()) {
            newName = str;
        }

        //Time
        str = view.readString("Enter Time (Current: " + t.getTime() + "): ");
        if (!str.isEmpty()) {
            newTime = str;
        }

        //Price
        while (true) {
            str = view.readString("Enter Price (Current: " + t.getPrice() + "): ");
            if (str.isEmpty()) {
                break; // Skip
            }
            if (Validator.isValid(str, Validator.POSITIVE_INT_VALID)) {
                newPrice = Integer.parseInt(str);
                break;
            }
            view.showMessage("Price must be a positive integer!");
        }

        //HomeId
        while (true) {
            str = view.readString("Enter Home ID (Current: " + t.getHomeId() + "): ");
            if (str.isEmpty()) {
                break; // Skip
            }
            if (Validator.isValid(str, Validator.HOME_ID) && homestayController.findById(str) != null) {
                newHomeId = str;
                break;
            }
            view.showMessage("Invalid or Non-existent Homestay ID!");
        }

        //Dates
        while (true) {
            Date inStart = view.readDate("Enter Departure (Current: " + DateUtils.dateToString(t.getDepartureDate()) + "): ");
            Date inEnd = view.readDate("Enter End Date (Current: " + DateUtils.dateToString(t.getEndDate()) + "): ");

            Date checkStart = (inStart != null) ? inStart : newStart;
            Date checkEnd = (inEnd != null) ? inEnd : newEnd;

            if (checkEnd.before(checkStart)) {
                view.showMessage("End Date must be after the Departure Date!");
                continue;
            }

            //Check user change to the home that is busy in range of their date.
            Tour dup = tourController.isDuplicateDate(newHomeId, checkStart, checkEnd, t.getId());

            if (dup != null) {
                view.showMessage("Overlap detected! Homestay " + newHomeId + " is busy from "
                        + DateUtils.dateToString(dup.getDepartureDate()) + " to " + DateUtils.dateToString(dup.getEndDate()));

                //Because of changing to busy home
                if (inStart == null && inEnd == null) {
                    view.showMessage("You changed the Homestay but keeping old dates causing conflict. Please enter new dates!");
                }
                continue;
            }

            newStart = checkStart;
            newEnd = checkEnd;
            break;
        }

        //Number Tourist
        while (true) {
            str = view.readString("Enter Number of Tourist (Current: " + t.getNumberTourist() + "): ");
            if (str.isEmpty()) {
                break; // Skip            
            }

            if (Validator.isValid(str, Validator.POSITIVE_INT_VALID)) {
                int val = Integer.parseInt(str);

                //check capacity of new Home
                int cap = homestayController.findById(newHomeId).getMaximumCapacity();
                if (val <= cap) {
                    newTourist = val;
                    break;
                } else {
                    view.showMessage("Exceeds capacity! Max is " + cap);
                }
            } else {
                view.showMessage("Must be positive integer!");
            }
        }

        //Booking status
        Boolean newBookingInput = view.readBoolean("Is Booked? (Current: " + t.isBooking() + ") [Y/N]: ");
        if (newBookingInput != null) {
            newBooking = newBookingInput;
        }

        Tour tourUpdate = new Tour(id, newName, newTime, newPrice, newHomeId, newStart, newEnd, newTourist, newBooking);

        tourController.update(tourUpdate);
        isChanged = true;
        view.showMessage("Update Tour with ID: " + id + " successfully!");
    }

    private void addNewBooking() {
        Tour selectedTour;

        String id;
        while (true) {
            id = view.readString("Enter Booking ID: ");
            if (Validator.isValid(id, Validator.BOOKING_ID)) {
                if (bookingController.findById(id) == null) {
                    break;
                }
                view.showMessage("ID already exists!");
            } else {
                view.showMessage("Invalid ID format (e.g. B12345)!");
            }
        }

        String fullName = view.readString("Enter Full Name: ");
        while (fullName.isEmpty()) {
            fullName = view.readString("Name cannot by empty! Try again: ");
        }

        String tourId;
        while (true) {
            tourId = view.readString("Enter Tour ID: ");
            if (Validator.isValid(tourId, Validator.TOUR_ID)) {
                if (tourController.findById(tourId) == null) {
                    view.showMessage("There is no Tour with ID: " + tourId);
                } else {
                    //Check available tour
                    selectedTour = tourController.findById(tourId);
                    if (selectedTour.isBooking()) {
                        view.showMessage("This tour has already been booked!");
                        continue;
                    }
                    break;
                }
            } else {
                view.showMessage("Invalid ID format (e.g. T12345)!");
            }
        }

        Date bookingDate;
        while (true) {
            bookingDate = view.readDate("Enter Booking Date: ");
            if (bookingDate == null) {
                view.showMessage("Booking Date cannot be empty!");
                continue;
            }

            if (!bookingDate.before(selectedTour.getDepartureDate())) {
                view.showMessage("Booking Date must be before the Departure Date: " + DateUtils.dateToString(selectedTour.getDepartureDate()));
                continue;
            }
            break;
        }

        String phone;
        while (true) {
            phone = view.readString("Enter Phone: ");
            if (phone.isEmpty()) {
                view.showMessage("Phone cannot be empty!");
                continue;
            }

            if (!Validator.isValid(phone, Validator.PHONE_VALID)) {
                view.showMessage("Invalid Phone! (Must be valid format).");
                continue;
            }
            break;
        }

        bookingController.addNew(new Booking(id, fullName, tourId, bookingDate, phone));
        selectedTour.setBooking(true);

        isChanged = true;
        view.showMessage("Add a new booking successfully!");
    }

    private void updateBooking() {
        String id;
        while (true) {
            id = view.readString("Enter Booking ID to update: ");
            if (!Validator.isValid(id, Validator.BOOKING_ID)) {
                view.showMessage("Invalid ID format (e.g. B12345)!");
                continue;
            }
            break;
        }

        Booking b = bookingController.findById(id);

        if (b == null) {
            view.showMessage("This Booking does not exist!");
            return;
        }

        //Declare variables contain old values
        String newFullName = b.getFullName();
        String newTourId = b.getTourId();
        Date newBookingDate = b.getBookingDate();
        String newPhone = b.getPhone();
        

        //Full Name
        String str = view.readString("Enter Full Name (Current: " + b.getFullName() + "): ");
        if (!str.isEmpty()) {
            newFullName = str;
        }

        //Tour id
        while (true) {
            str = view.readString("Enter Tour Id (Current: " + b.getTourId() + "): ");
            if (str.isEmpty()) {
                break;
            }
            if (!Validator.isValid(str, Validator.TOUR_ID)) {
                view.showMessage("Invalid ID format (e.g. T12345)!");
                continue;
            }
            if (tourController.findById(str) == null) {
                view.showMessage("This Tour id does not exist!");
                continue;
            }

            //Ignore updating booking when check booking status
            Tour t = tourController.findById(str);
            if (!str.equals(b.getTourId()) && t.isBooking()) {
                view.showMessage("This tour has already been booked!");
                continue;
            }
            newTourId = str;
            break;
        }

        Tour updatedTour = tourController.findById(newTourId);
        //Booking Date
        while (true) {
            Date d = view.readDate("Enter Booking Date (Current: " + DateUtils.dateToString(b.getBookingDate()) + "): ");

            if (d == null) {
                if (!newBookingDate.before(updatedTour.getDepartureDate())) {
                    view.showMessage("Booking Date must be before the Departure Date: " + DateUtils.dateToString(updatedTour.getDepartureDate()));
                    continue;
                }
                break;
            }
            if (!d.before(updatedTour.getDepartureDate())) {
                view.showMessage("Booking Date must be before the Departure Date: " + DateUtils.dateToString(updatedTour.getDepartureDate()));
                continue;
            }
            newBookingDate = d;
            break;
        }

        //Phone
        while (true) {
            str = view.readString("Enter Phone (Current: " + b.getPhone() + "): ");
            if (str.isEmpty()) {
                break;
            }
            if (!Validator.isValid(str, Validator.PHONE_VALID)) {
                view.showMessage("Invalid Phone! (Must be valid format).");
                continue;
            }
            newPhone = str;
            break;
        }

        Booking bookingUpdate = new Booking(id, newFullName, newTourId, newBookingDate, newPhone);
        bookingController.update(bookingUpdate);
        // release old tour
        tourController.findById(b.getTourId()).setBooking(false);

        // book new tour
        tourController.findById(newTourId).setBooking(true);
        isChanged = true;
        view.showMessage("Update Booking with ID: " + id + " successfully!");
    }

    private void deleteBooking() {
        String id;
        while (true) {
            id = view.readString("Enter Booking ID to delete: ");
            if (!Validator.isValid(id, Validator.BOOKING_ID)) {
                view.showMessage("Invalid ID format (e.g. B12345)!");
                continue;
            }
            break;
        }

        Booking b = bookingController.findById(id);

        if (b == null) {
            view.showMessage("This Booking does not exist!");
            return;
        }

        Tour t = tourController.findById(b.getTourId());
        if (t != null) {
            t.setBooking(false);
        }

        bookingController.deleteById(id);
        isChanged = true;
        view.showMessage("Remove booking with ID: " + id + " successfully!");
    }

    private void searchByFullName() {
        String str = view.readString("Enter a partial Full Name to search: ");

        List<Booking> searchList = bookingController.searchByFullName(str);

        if (searchList.isEmpty()) {
            view.showMessage("No Booking found!");
            return;
        }

        view.showMessage("===== Search Booking =====");
        System.out.print(bookingController.getBookingHeader());
        view.showList(searchList);
    }

    private void showCompletedTour() {
        List<Tour> l = tourController.getCompletedTour();

        if (l.isEmpty()) {
            view.showMessage("No Completed Tour!");
            return;
        }
        view.showMessage("===== Completed Tours =====");
        System.out.print(tourController.getTourHeader());
        view.showList(l);
    }

    private void showTotalAmountFutureTours() {
        List<Tour> l = tourController.getTotalAmountFutureTours();

        if (l.isEmpty()) {
            view.showMessage("No Future Tour!");
            return;
        }
        view.showMessage("===== Total Amount of Future Tours =====");
        view.showMessage("------------------------------------");
        view.showMessage("| Tour ID  | Start        | Total  |");
        view.showMessage("------------------------------------");
        for (Tour tour : l) {
            System.out.printf("| %-8s | %-12s | %6d |\n", tour.getId(), DateUtils.dateToString(tour.getDepartureDate()), tour.getTotalAmount());
        }
    }

    private void showStatistic() {
        view.showMessage("===== Statistics =====");
        view.showMessage("---------------------------------------------");
        view.showMessage("| Homestay                 | Number Tourist |");
        view.showMessage("---------------------------------------------");

        for (Homestay h : homestayController.getAll()) {
            int n = 0;
            for (Tour t : tourController.getAll()) {
                if (t.isBooking() && t.getHomeId().equals(h.getId())) {
                    n += t.getNumberTourist();
                }
            }

            System.out.printf("| %-24s | %14d |\n", h.getName(), n);
        }
    }

    private void save() {
        if (tourController.save() && bookingController.save()) {
            isChanged = false;
            view.showMessage("Data saved successfully.");
        }
    }

    private void exit() {
        if (isChanged) {
            boolean confirm = view.readBoolean("You have unsaved changes. Save before exiting? (Y/N): ");
            if (confirm) {
                save();
            }
        }
        view.showMessage("Exiting program...");
        System.exit(0);
    }
}
