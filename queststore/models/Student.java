package queststore.models;

import java.util.ArrayList;

public class Student extends User {

	private Integer earnedCoins;
	private Integer possesedCoins;
	private ArrayList<BoughtArtifact> ownedArtifacts;
	private ArrayList<Quest> achievedQuests;
	private Class class_;

    public Student(String name, String login, String password, String email, Class class_) {
        super(name, login, password, email);
		this.earnedCoins = 0;
		this.possesedCoins = 0;
		this.ownedArtifacts = new ArrayList<>();
		this.achievedQuests = new ArrayList<>();
		this.class_ = class_;
    }

	public Student(String name, String login, String password, String email, Class class_, Integer id) {
      super(name, login, password, email, id);
	    this.earnedCoins = 0;
		  this.possesedCoins = 0;
		  this.ownedArtifacts = new ArrayList<>();
		  this.achievedQuests = new ArrayList<>();
		  this.class_ = class_;
    }

	public Integer getEarnedCoins() {
		return this.earnedCoins;
	}

	public Integer getPossesedCoins() {
		return this.possesedCoins;
	}

	public ArrayList<BoughtArtifact> getOwnedArtifacts() {
		return this.ownedArtifacts;
	}

	public ArrayList<Quest> getAchievedQuests() {
		return this.achievedQuests;
	}

	public Integer getLevel(ExperienceLevels experienceLevels) {
		return experienceLevels.computeStudentLevel(this.earnedCoins);
	}


	public Class getClas() {
        return this.class_;
    }

	public void addCoins(Integer ammount) {
		this.earnedCoins += ammount;
		this.possesedCoins += ammount;
	}

	public void substractCoins(Integer ammount) {
		this.possesedCoins -= ammount;
	}
}
