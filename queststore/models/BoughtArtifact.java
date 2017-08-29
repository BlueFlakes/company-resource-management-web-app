package queststore.models;

import java.util.ArrayList;

public class BoughtArtifact extends Artifact{
    private ArrayList<Student> ownersList;
    private boolean isUsed;

    public ArrayList<Student> getOwnersList(){
        return this.ownersList;
    }

    public boolean getIsUsed(){
        return this.isUsed;
    }

    public void markAsUsed(){
        if(!this.isUsed) this.isUsed = true;
    }
}