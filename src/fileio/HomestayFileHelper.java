package fileio;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import model.Homestay;

public class HomestayFileHelper implements IFileReadWrite<Homestay> {

    private final String FILE_NAME = "src/fileio/Homestays.txt";
    

    @Override
    public List<Homestay> read() throws Exception {
        List<Homestay> list = new ArrayList<>();
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
                Homestay home = convertToHomestay(line);
                list.add(home);
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
    public boolean write(List<Homestay> list) throws Exception {
        return false;
    }

    private Homestay convertToHomestay(String str) throws ParseException {
        if (str == null || str.trim().isEmpty()) {
            return null;
        }
        Homestay home = null;

        String[] p = str.split("-");
        
        if (p.length < 5) {
            System.err.println("Data format error (not enough separators): " + str);
            return null; 
        }
        String id = p[0].trim();
        String name = p[1].trim();
        
        int roomNumber = 0;
        try {
            roomNumber = Integer.parseInt(p[2].trim());
        } catch (NumberFormatException e) {
            System.err.println("Error parsing room number: " + p[2]);
        }
        
        int maximumCapacity = 0;
        try {
            maximumCapacity = Integer.parseInt(p[p.length - 1].trim());
        } catch (NumberFormatException e) {
            System.err.println("Error parsing capacity: " + p[p.length - 1]);
        }
        
        
        StringBuilder addressBuilder = new StringBuilder();
        for (int i = 3; i < p.length - 1; i++) {
            addressBuilder.append(p[i]).append("-");
        }
        
        //Remove "-" in the end after concat string
        String address = "";
        if (addressBuilder.length() > 0) {
            address = addressBuilder.substring(0, addressBuilder.length() - 1).trim();
        }

        home = new Homestay(id, name, roomNumber, address, maximumCapacity);
        return home;
    }
}
