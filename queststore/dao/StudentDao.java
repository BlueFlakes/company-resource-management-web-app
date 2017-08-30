package queststore.dao;

import queststore.models.Student;

public class StudentDao{
    public Student getStudent(Integer id){
        
        return new Student("","kuba","kuba","");
    }
    public Student getStudent(String login){
        if (login.equals("kuba")){
            return new Student("","kuba","kuba","");
        }
        else {
            return null;
        }
    }

    public void save(){
        
    }
}