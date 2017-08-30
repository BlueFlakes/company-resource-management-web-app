package queststore.views;

import java.util.List;
import queststore.models.Window;
import queststore.dao.FileLoader;

class MenuPrinter {
    private FileLoader dataLoader = new FileLoader();
    private Window window = new Window();

    public static void main(String[] args) {
        System.out.println("run\n");

        MenuPrinter menuPrinter = new MenuPrinter();
        menuPrinter.printMentorMenu();

    }

    public void printMentorMenu() {
        String fileName = "queststore/csv/mentorMenu.csv";
        List<String> loadedMenu = dataLoader.getDataFromFile(fileName);

        printMenu(loadedMenu);
    }

    public void printStudentMenu() {
        String fileName = "queststore/csv/studentMenu.csv";
        List<String> loadedMenu = dataLoader.getDataFromFile(fileName);

        printMenu(loadedMenu);
    }

    public void printManagerMenu() {
        String fileName = "queststore/csv/managerMenu.csv";
        List<String> loadedMenu = dataLoader.getDataFromFile(fileName);

        printMenu(loadedMenu);
    }

    private void printMenu(List<String> data) {
        window.clearWindow();
        printAllRecordsInMenuStyle(data);
    }

    private void printAllRecordsInMenuStyle(List<String> data) {
        String title = data.get(0);

        System.out.println(title);

        for(int i = 1; i < data.size(); i++) {
            String index = (Integer.toString(i % (data.size() - 1)));
            String row = data.get(i);

            System.out.println("\t" + "(" + index + ") " + row);
        }

        System.out.println();
    }
}
