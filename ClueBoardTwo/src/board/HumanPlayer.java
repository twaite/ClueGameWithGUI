package board;

import java.awt.Point;

public class HumanPlayer extends Player {
	
	public HumanPlayer (String name, String color, Point p) {
		this.name = name;
		this.color = color;
		this.location = p;
	}
	
	public HumanPlayer() {
		super();
	}
}
