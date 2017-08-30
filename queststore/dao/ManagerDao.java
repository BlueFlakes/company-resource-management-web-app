package queststore.dao;

import queststore.models.Manager;

public class ManagerDao{
    public Manager getManager(Integer id){
        
        return new Manager("Jan Kowalski","jkowal","kowal","jkowal@buziaczek.pl");
    }
    public Manager getManager(String login){
        if (login.equals("jkowal")){
            return new Manager("Jan Kowalski","jkowal","kowal","jkowal@buziaczek.pl");
        }
        else {
            return null;
        }
        
    }

    public void save(){
        
    }
}