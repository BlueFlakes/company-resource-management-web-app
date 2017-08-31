package queststore.dao;

import java.util.Scanner;
import java.util.ArrayList;
import java.io.FileNotFoundException;
import java.io.File;
import java.util.Formatter;

import queststore.models.Class;


public class ClassDao {

    private ArrayList<Class> classes;

    public ClassDao() {
        this.classes = readClassesData();
    }

    private ArrayList<Class> readClassesData() {
        ArrayList<Class> loadedClasses= new ArrayList<>();
        String[] classData;

        try (Scanner fileScan = new Scanner(new File("queststore/csv/class.csv"))) {

            while(fileScan.hasNextLine()) {
                classData = fileScan.nextLine().split("\\|");
                String name = classData[1];
                Integer id = Integer.parseInt(classData[0]);
                loadedClasses.add(new Class(name, id));
            }

        } catch (FileNotFoundException e) {
            System.out.println("File class.csv not found!");
        }

        return loadedClasses;
    }

    public Class getClass(Integer id) {

        for (Class clas : this.classes) {
            if(clas.getId() == id) {
                return clas;
            }
        }

        return null;
    }

    public Class getClass(String name) {

        for (Class clas : this.classes) {
            if (clas.getName().equals(name)) {
                return clas;
            }
        }

        return null;
    }

    public ArrayList<Class> getAllClasses() {
        return this.classes;
    }

    public void save() {
        try (Formatter writer = new Formatter("queststore/csv/class.csv")) {

            for(Class clas: this.classes) {
                String lineToSave = clas.getClassSaveString();
                writer.format(lineToSave);
            }

        } catch (Exception e) {
            System.out.println("File not found");
        }
    }
}
