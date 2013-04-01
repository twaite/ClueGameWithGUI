package board;

public class Solution {

	private String person;
	private String weapon;
	private String room;
	
	public Solution() {
		this.person = "";
		this.weapon = "";
		this.room = "";
	}
	
	public Solution( String person, String weapon, String room ) {
		this.person = person;
		this.weapon = weapon;
		this.room = room;
	}

	public String getPerson() {
		return this.person;
	}

	public String getWeapon() {
		return this.weapon;
	}

	public String getRoom() {
		return this.room;
	}

	public void setPerson(String person) {
		this.person = person;
	}

	public void setWeapon(String weapon) {
		this.weapon = weapon;
	}

	public void setRoom(String room) {
		this.room = room;
	}
	
	
}
