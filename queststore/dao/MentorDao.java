package queststore.dao;

import queststore.models.Mentor;

public class MentorDao{
    public Mentor getMentor(Integer id){
        
        return new Mentor("","","","");
    }
    public Mentor getMentor(String login){
        return new Mentor("","","","");
    }

    public void save(){
        
    }
}