package queststore.dao;

import queststore.models.Manager;

public class ManagerDao{
    public Manager getManager(Integer id){
        
        return new Manager("Jan Kowalski","jkowal","kowal","jkowal@buziaczek.pl");
    }
    public Manager getManager(String login){
        return new Manager("Jan Kowalski","jkowal","kowal","jkowal@buziaczek.pl");
    }

    public void save(){
        
    }
}