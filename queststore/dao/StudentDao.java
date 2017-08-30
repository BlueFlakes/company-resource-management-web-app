package queststore.dao;

import queststore.models.Student;

public class StudentDao{
    public Student getStudent(Integer id){
        
        return new Student("","","","");
    }
    public Student getStudent(String login){
        return new Student("","","","");
    }

    public void save(){
        
    }
}