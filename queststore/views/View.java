package queststore.views;

import java.util.Scanner;

public class View{
    private Scanner in = new Scanner(System.in);

    public String getInput(String question){
        System.out.print(question + ": ");
        return in.nextLine().trim();
    }

    public void print(String text){
        System.out.println(text);
    }
}