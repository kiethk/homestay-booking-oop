
package controller;

import fileio.HomestayFileHelper;
import fileio.IFileReadWrite;
import java.util.ArrayList;
import java.util.List;
import model.Homestay;

public class HomestayController {
    private List<Homestay> list = new ArrayList<>();

    public HomestayController() {
        try {
            IFileReadWrite file = new HomestayFileHelper();
            list = file.read();
        } catch (Exception e) {
            System.err.println("Error: Required file Homestays.txt not found!");

            System.exit(1);
        }
    }
    
    public List<Homestay> getAll() {
        return list;
    }
    
    public Homestay findById(String id) {
        for (Homestay m : list) {
            if (m.getId().equalsIgnoreCase(id)) {
                return m;
            }
        }
        return null;
    }
}
