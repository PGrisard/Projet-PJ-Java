import java.awt.Color;
import java.util.Random;

public class Case {
	private int appartientA;
	private Color color;
	private Color[] sixCouleurs={Color.RED,Color.BLUE,Color.MAGENTA,Color.GREEN,Color.YELLOW,Color.GRAY};

	public Case() {
		color=getRandomColor(sixCouleurs);
		appartientA=0;
	}
	
	public int getAppartientA() {
		return appartientA;
	}

	public void setAppartientA(int appartientA) {
		if (appartientA == 0 || appartientA == 1 || appartientA == 2) {
			// 0 signifie que la case n'appartient a aucun joueur, 1 appartient
			// au joueur 1, 2 appartient au joueur 2
			this.appartientA = appartientA;
		}
	}
	
	public static Color getRandomColor(Color[] tableauCouleur){
		int j=0;
		for(int i=0;i<tableauCouleur.length;i++){
			Random rand = new Random();
			j = rand.nextInt(tableauCouleur.length);
		} 
		return(tableauCouleur[j]);
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}
}
