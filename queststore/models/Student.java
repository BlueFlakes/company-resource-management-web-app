package queststore.models;

import java.util.ArrayList;

public class Student extends User{
	private Integer earnedCoins;
	private Integer possesedCoins;
	private ArrayList<BoughtArtifact> ownedArtifacts;
	private ArrayList<Quest> achievedQuests;

	public Integer getEarnedCoins(){
		return this.earnedCoins;
	}

	public Integer getPossesedCoins(){
		return this.possesedCoins;
	}

	public ArrayList<BoughtArtifact> getOwnedArtifacts(){
		return this.ownedArtifacts;
	}

	public ArrayList<Quest> getAchievedQuests(){
		return this.achievedQuests;
	}

	public Integer getLevel(ExperienceLevels experienceLevels){
		return experienceLevels.computeStudentLevel(this.earnedCoins);
	}

	public void addCoins(Integer ammount){
		this.earnedCoins += ammount;
		this.possesedCoins += ammount;
	}

	public void substractCoins(Integer ammount){
		this.possesedCoins -= ammount;
	}
}
