package queststore.dao;

import java.util.Scanner;
import java.util.ArrayList;
import java.io.FileNotFoundException;
import java.io.File;
import queststore.models.Class;

public class ClassDao{
    private ArrayList<Class> classes;

    public ClassDao() {
        this.classes = readClassesData();
    }

    private ArrayList<Class> readClassesData() {
        ArrayList<Class> loadedClasses= new ArrayList<>();
        String[] classData;
        Scanner fileScan;

        try {
            fileScan = new Scanner(new File("queststore/csv/class.csv"));

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

        for(Class clas : this.classes) {

                if(clas.getId() == id) {
                    return clas;
                }
        }

        return null;
    }

    public Class getClass(String name){

        for(Class clas : this.classes) {

                if(clas.getName().equals(name)) {
                    return clas;
                }
        }

        return null;
    }

    public void save(){

    }

    public static void main(String[] args) {
        ClassDao classDao = new ClassDao();
        Class clas = classDao.getClass("klasa druga");
        System.out.println(clas.getName());
        //System.out.println(classDao.classes.get(1).getId());
    }
}
