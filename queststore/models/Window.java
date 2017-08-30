package queststore.models;

import java.io.IOException;

public class Window {
    
    public void clearWindow() {
        for(int i = 0; i < 50; i ++) {
            System.out.println();
        }
    }

    private void clearWindowUnSafeAlternative() {
        System.out.print("\033[H\033[2J");
    }
}
