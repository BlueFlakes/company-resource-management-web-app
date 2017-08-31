package queststore.dao;

import java.util.Scanner;
import java.util.ArrayList;
import java.io.FileNotFoundException;
import java.io.File;
import java.util.Formatter;

import queststore.models.Manager;

public class ManagerDao {

    private ArrayList<Manager> managers;

    public ManagerDao() {
        this.managers = readManagersData();
    }

    private ArrayList<Manager> readManagersData() {

        ArrayList<Manager> loadedManagers= new ArrayList<>();
        String[] managerData;

        try (Scanner fileScan = new Scanner(new File("bin/queststore/csv/manager.csv"))) {

            while(fileScan.hasNextLine()) {
                managerData = fileScan.nextLine().split("\\|");
                String name = managerData[1];
                Integer id = Integer.parseInt(managerData[0]);
                String login = managerData[2];
                String password = managerData[3];
                String email = managerData[4];

                loadedManagers.add(new Manager(name, login, password, email, id));
            }

        } catch (FileNotFoundException e) {
            System.out.println("File manager.csv not found!");
        }

        return loadedManagers;
    }

    public Manager getManager(Integer id) {

        for(Manager manager : this.managers) {
            if (manager.getId() == id) {
                return manager;
            }
        }

        return null;
    }

    public Manager getManager(String login) {

        for(Manager manager : this.managers) {
            if(manager.getLogin().equals(login)) {
                return manager;
            }
        }

        return null;
    }

    public void addManager(Manager manager) {
        this.managers.add(manager);
    }

    public void save() {
        try (Formatter writer = new Formatter("bin/queststore/csv/manager.csv")) {

            for(Manager manager: this.managers) {
                String lineToSave = manager.getManagerSaveString();
                writer.format(lineToSave);
            }

        } catch (Exception e) {
            System.out.println("File not found");
        }
    }

    public static void main(String[] args) {
        ManagerDao managerDao = new ManagerDao();
        Manager manager = managerDao.getManager(2);
        System.out.println(manager.getName());
        //System.out.println(classDao.classes.get(1).getId());
    }
}
