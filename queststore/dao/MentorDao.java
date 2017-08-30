package queststore.dao;

import queststore.models.Mentor;

public class MentorDao{
    public Mentor getMentor(Integer id){
        
        return new Mentor("","","","");
    }
    public Mentor getMentor(String login){
        if (login.equals("skubi")){
            return new Mentor("","subi","doo","");
        }
        else {
            return null;
        }
    }

    public void save(){
        
    }
}