package goosegame;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

class Domandiere {
    public static List<String> readFile(String fileName) {
        List<String> lines = new ArrayList<String>();
        try {
            File file = new File(fileName);
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader); //domandiere che legge il file 
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                lines.add(line);
            }
            fileReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lines;
    }
}

