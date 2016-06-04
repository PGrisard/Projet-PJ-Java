import java.awt.Color;
import java.awt.Font;
import java.util.Random;

import edu.princeton.cs.introcs.StdDraw;

public class Grille {
	private Case[][] Grille;
	private int tailleP;
	private int forbiddenColor = 1;
	private int totalJ1 = 0;
	private int totalJ2 = 0;
	private int cycle;
	private Color[] sixCouleurs = { Color.RED, Color.BLUE, Color.MAGENTA,
			Color.GREEN, Color.YELLOW, Color.ORANGE};

	public Case[][] getGrille() {
		return Grille;
	}

	public void setGrille(Case[][] grille) {
		Grille = grille;
	}

	public Grille(int tailleP) {
		this.tailleP = tailleP;
		Grille = new Case[tailleP][tailleP];
		int i, j;
		for (i = 0; i < tailleP; i++) {
			for (j = 0; j < tailleP; j++) {
				Grille[i][j] = new Case();
			}
		}
	}

	public void grilleInit(Joueur joueur1, Joueur joueur2) {
		int ordonneeColorJoueur1 = 0, abscisseColorJoueur1 = 0; // J1 en haut a gauche
		int ordonneeColorJoueur2 = tailleP - 1, abscisseColorJoueur2 = tailleP - 1; // J2 en bas a droite

		// Joueur 1
		getGrille()[ordonneeColorJoueur1][abscisseColorJoueur1].setColor(joueur1.getColor());
		getGrille()[ordonneeColorJoueur1][abscisseColorJoueur1].setAppartientA(joueur1.getName());

		// Joueur 2
		getGrille()[ordonneeColorJoueur2][abscisseColorJoueur2].setColor(joueur2.getColor());
		getGrille()[ordonneeColorJoueur2][abscisseColorJoueur2].setAppartientA(joueur2.getName());

		// Couleurs voisines differentes de la couleur initiale des joueurs
		// Joueur 1
		verifierCasesVoisines(joueur1, 0, 1); // case a droite
		verifierCasesVoisines(joueur1, 1, 0); // case en dessous

		// Joueur 2
		verifierCasesVoisines(joueur2, tailleP - 1, tailleP - 2); // case a gauche
		verifierCasesVoisines(joueur2, tailleP - 2, tailleP - 1); // case en haut

	}

	public void verifierCasesVoisines(Joueur joueur, int i, int j) {
		while (getGrille()[i][j].getColor() == joueur.getColor()) {
			getGrille()[i][j].setColor(Case.getRandomColor(sixCouleurs));
		}
	}

	public int getTailleP() {
		return tailleP;
	}

	public void setTailleP(int tailleP) {
		this.tailleP = tailleP;
	}

	public Color setColor(Color color) {
		return color;
	}
	
	public void affichage2D() {		
		StdDraw.setCanvasSize(750, 710);
		Font font = new Font("Arial", Font.BOLD, 30);
		StdDraw.setFont(font);
		StdDraw.text(0.45, 0.9, "Jeu des six couleurs");
		int i, j;
		StdDraw.setScale(1.5 * tailleP, -tailleP);
		for (i = 0; i < Grille.length; i++) {
			for (j = 0; j < Grille[i].length; j++) {
				StdDraw.setPenColor(Grille[i][j].getColor());
				StdDraw.filledSquare(i, j, 0.5);
			}
			affichageGrilleVide();
		}
	}
	public void affichageGrilleVide() {

		StdDraw.setScale(1.5 * tailleP, -tailleP);
		StdDraw.setPenColor(Color.BLACK);
		for (int x = 0; x < Grille.length; x++) {
			for (int y = 0; y < Grille[x].length; y++) {
				StdDraw.square(x, y, 0.5);
			}
		}
	}
	public void affichage() {
		int i, j;
		for (i = 0; i < Grille.length; i++) {
			for (j = 0; j < Grille[i].length; j++) {
				System.out.print(Grille[i][j].getColor() + "\t");
			}
			System.out.println();
		}
	}
	
	public void actualiserCouleur(Joueur joueur){
		for (int  i = 0; i <Grille.length ; i++){
			for (int j = 0; j < Grille.length;j++){
				if (getGrille()[i][j].getAppartientA() == joueur.getName()){
					getGrille()[i][j].setColor(joueur.getColor());
				}
			}
		}
	}
	
	public void stepAppartenance(Joueur joueur){
		cycle = 0;
		for (int  i = 0; i < Grille.length; i++) {
			for (int j = 0; j < Grille.length;j++) {
				if (getGrille()[i][j].getAppartientA() == joueur.getName()){
					actualiserAppartenance(i,j,joueur);
				}			
			}
		}
		if (cycle == 1){
			stepAppartenance(joueur);
		}
	}
	
	public void actualiserAppartenance(int i, int j, Joueur joueur){
		for (int x = i - 1; x <= i + 1; x++) {
			if (x >= 0 && x < Grille.length) {
				if (getGrille()[x][j].getColor() == joueur.getColor() && getGrille()[x][j].getAppartientA() != joueur.getName()){
					getGrille()[x][j].setAppartientA(joueur.getName());
					cycle = 1;
				}
			}
		}
		for (int y = j - 1 ; y <= j + 1; y++) {	
			if (y >= 0 && y < Grille.length) {
				if (getGrille()[i][y].getColor() == joueur.getColor() && getGrille()[i][y].getAppartientA() != joueur.getName()){
					getGrille()[i][y].setAppartientA(joueur.getName());
					cycle = 1;
				}
			}
		}
	}

}
