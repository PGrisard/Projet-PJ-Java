import java.awt.Color;

public class Joueur {

	private int name;
	private Color color;

	public Joueur(int name, Color color) {
		this.name = name;
		this.color=color;
	}

	public int getName() {
		return name;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

}
