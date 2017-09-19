package queststore.dao;

import java.util.Scanner;
import java.util.ArrayList;
import java.io.FileNotFoundException;
import java.io.File;
import java.util.Formatter;

import queststore.models.SchoolClass;


public class ClassDao {

    private ArrayList<SchoolClass> classes;

    public ClassDao() {
        this.classes = readClassesData();
    }

    private ArrayList<SchoolClass> readClassesData() {
        ArrayList<SchoolClass> loadedClasses= new ArrayList<>();
        String[] classData;

        try (Scanner fileScan = new Scanner(new File("bin/queststore/csv/class.csv"))) {

            while(fileScan.hasNextLine()) {
                classData = fileScan.nextLine().split("\\|");
                String name = classData[1];
                Integer id = Integer.parseInt(classData[0]);
                loadedClasses.add(new SchoolClass(name, id));
            }

        } catch (FileNotFoundException e) {
            System.out.println("File class.csv not found!");
        }

        return loadedClasses;
    }

    public SchoolClass getSchoolClass(Integer id) {

        for (SchoolClass clas : this.classes) {
            if(clas.getId() == id) {
                return clas;
            }
        }

        return null;
    }

    public SchoolClass getSchoolClass(String name) {

        for (SchoolClass clas : this.classes) {
            if (clas.getName().equals(name)) {
                return clas;
            }
        }

        return null;
    }

    public ArrayList<SchoolClass> getAllClasses() {
        return this.classes;
    }

    public void save() {
        try (Formatter writer = new Formatter("bin/queststore/csv/class.csv")) {

            for(SchoolClass clas: this.classes) {
                String lineToSave = clas.getClassSaveString();
                writer.format(lineToSave);
            }

        } catch (Exception e) {
            System.out.println("File not found");
        }
    }
}
