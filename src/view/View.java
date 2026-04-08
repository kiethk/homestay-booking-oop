package view;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import utils.Validator;

public class View {

    private final Scanner sc;

    public View(Scanner sc) {
        this.sc = sc;
    }

    public int showMainMenu() {

        System.out.println("===== Homestay Booking =====");

        String[] options = {
            "Add a new Tour",
            "Update a Tour by ID",
            "List Completed Tours",
            "Show Upcoming Tours",
            "Add a new Booking",
            "Remove a Booking by ID",
            "Update a Booking by ID",
            "List all Booking by Name",
            "Show Tourist Statistics",
            "Quit",};

        Menu.showMenu(options);
        return Integer.parseInt(sc.nextLine());
    }

    public <T> void showList(List<T> list) {
        for (T item : list) {
            System.out.print(item.toString());
        }
    }

    public int readInt(String prompt, int min, int max) {
        while (true) {
            try {
                System.out.print(prompt);
                int val = Integer.parseInt(sc.nextLine().trim());
                if (val < min || val > max) {
                    throw new Exception();
                }
                return val;
            } catch (Exception e) {
                System.out.println("Invalid number, try again.");
            }
        }
    }

    public int readInt(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                int val = Integer.parseInt(sc.nextLine().trim());
                return val;
            } catch (NumberFormatException e) {
                System.out.println("Invalid number, try again.");
            }
        }
    }

    public int readInt(String prompt, int oldValue) {
        while (true) {
            try {
                int val;
                System.out.print(prompt);
                String line = sc.nextLine().trim();
                if (line.trim().isEmpty()) {
                    val = oldValue;
                } else {
                    val = Integer.parseInt(line.trim());
                }
                return val;
            } catch (NumberFormatException e) {
                System.out.println("Invalid number, try again.");
            }
        }
    }

    public double readDouble(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                double val = Double.parseDouble(sc.nextLine().trim());
                return val;
            } catch (NumberFormatException e) {
                System.out.println("Invalid number, try again.");
            }
        }
    }

    public double readDouble(String prompt, double oldValue) {
        while (true) {
            try {
                double val;
                System.out.print(prompt);
                String line = sc.nextLine().trim();
                if (line.trim().isEmpty()) {
                    val = oldValue;
                } else {
                    val = Double.parseDouble(sc.nextLine().trim());
                }
                return val;
            } catch (NumberFormatException e) {
                System.out.println("Invalid number, try again.");
            }
        }
    }

    public Date readDate(String prompt) {
        SimpleDateFormat sdf = new SimpleDateFormat(Validator.DATE_VALID);
        sdf.setLenient(false);
        do {
            String input = readString(prompt);
            if (input.isEmpty()) {
                return null;
            }

            try {
                Date date = sdf.parse(input);
                return date;
            } catch (ParseException e) {
                System.out.println("Invalid date! Please enter again (dd/MM/yyyy).");
            }

        } while (true);
    }

    // use Boolean to return null value
    public Boolean readBoolean(String prompt) {
        while (true) {
            String str = readString(prompt);

            if (str.isEmpty()) {
                return null;
            }

            if (Validator.isValid(str, Validator.BOOLEAN_VALID)) {
                return str.equalsIgnoreCase("Y");
            } else {
                System.out.println("You must enter Y or N!");
            }
        }
    }

    public String readString(String prompt) {
        System.out.print(prompt);
        return sc.nextLine().trim();
    }

    public String readStringAllowEmpty(String prompt) {
        System.out.print(prompt);
        return sc.nextLine().trim();
    }

    public void showMessage(String msg) {
        System.out.println(msg);
    }
}
