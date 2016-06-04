import java.awt.Color;
import java.util.Scanner;

public class Jeu {
	private int modeDeJeu;
	private int difficulteIA;
	private int forbiddenColor = 1;
	private int meilleureCouleur;
	private int obstacles;
	private int nbObstacles;
	private int [] presenceCouleur = new int [7];
	private int meilleurePresenceCouleur;
	private Color oldColorIA;
	private int totalJ1 = 0;
	private int totalJ2 = 0;
	
	private int tailleGrille;
	private Color[] sixCouleurs = { Color.RED, Color.BLUE, Color.MAGENTA, Color.GREEN, Color.YELLOW, Color.GRAY };
	private Color[] sixCouleursObstacles={Color.RED,Color.BLUE,Color.MAGENTA,Color.GREEN,Color.YELLOW,Color.GRAY,Color.BLACK};
	Joueur joueur1 = new Joueur(1, Color.BLUE);
	Joueur joueur2 = new Joueur(2, Color.RED);
	private Grille grille;

	public void lancerJeu() {
		choisirTailleGrille();
		choisirObstacles();
		grille.grilleInit(joueur1, joueur2);
		choisirModeDeJeu();
		jouerJeu(joueur1, joueur2);
	}
	
	public void choisirTailleGrille(){
		Scanner scanner = new Scanner(System.in);
		System.out.println("Choisissez la taille de la grille (compris entre 4 et 20) :");
		System.out.println("Exemple : 5 -> creation d une grille de taille 5 x 5");
		try {
			int tailleG = scanner.nextInt();
			if (tailleG < 4 || tailleG > 20){ 
				System.out.println("Saisie incorrect, selectionnez une autre valeur");
				choisirTailleGrille();
			}
			tailleGrille = tailleG;
		} catch (Exception e) {
		System.out.println("Saisie incorrect, selectionnez une autre valeur");
		choisirTailleGrille();
		}
		grille = new Grille(tailleGrille);
	}
	
	public void choisirObstacles(){
		Scanner scanner = new Scanner(System.in);
		System.out.println("Jouer avec des obstacles : NON (0) OUI (1)");
		try {
			int obs = scanner.nextInt();
			if (obs < 0 || obs > 1){ 
				System.out.println("Saisie incorrect, selectionnez une autre valeur");
				choisirObstacles();
			}
			obstacles = obs;
			if (obstacles == 1){
				modifierCaseObstacles();
			}
		} catch (Exception e) {
		System.out.println("Saisie incorrect, selectionnez une autre valeur");
		choisirObstacles();
		}
	}
	
	public void modifierCaseObstacles(){
		nbObstacles = 0;
		for (int  i = 0; i < tailleGrille; i++) {
			for (int j = 0; j < tailleGrille;j++) {
				grille.getGrille()[i][j].setColor(Case.getRandomColor(sixCouleursObstacles));
				if (grille.getGrille()[i][j].getColor() == Color.BLACK){
					nbObstacles ++;
				}
			}
		}
	}
	
	public void choisirModeDeJeu() {
		Scanner scanner = new Scanner(System.in);
		System.out.println("Choisissez le mode de jeu :");
		System.out.println("J1 VS J2 (1)");
		System.out.println("J1 VS IA (2)");
		try {
			int mode = scanner.nextInt();
			if (mode < 1 || mode > 2){ 
				System.out.println("Saisie incorrect, selectionnez une autre valeur");
				choisirModeDeJeu();
			}
			modeDeJeu = mode;
			if (modeDeJeu == 2){
				choisirDifficulteIA();
			}
		} catch (Exception e) {
		System.out.println("Saisie incorrect, selectionnez une autre valeur");
		choisirModeDeJeu();
		}	
	}
	
	public void choisirDifficulteIA() {
		Scanner scanner = new Scanner(System.in);
		System.out.println("Choisissez le niveau de difficulte :");
		System.out.println("Facile (1) Moyen (2) Difficile (3)");
		try {
			int difficulte = scanner.nextInt();
			if (difficulte < 1 || difficulte > 3){ 
				System.out.println("Saisie incorrect, selectionnez une autre valeur");
				choisirDifficulteIA();
			}
			difficulteIA = difficulte;
		} catch (Exception e) {
		System.out.println("Saisie incorrect, selectionnez une autre valeur");
		choisirDifficulteIA();	
		}
	}

	public void jouerJeu(Joueur joueur1, Joueur joueur2) {
		int compteur = 1;
		int arret = 0;
		grille.affichage2D();
		while (arret == 0) {
			if (compteur % 2 == 0) {
				if (modeDeJeu == 1){
					jouer(joueur2);
				}
				else {
					jouerIA(joueur2);
				}
				grille.actualiserCouleur(joueur2);
				grille.stepAppartenance(joueur2);
			} else {
				jouer(joueur1);
				grille.actualiserCouleur(joueur1);
				grille.stepAppartenance(joueur1);
			}
			grille.affichage2D();
			compteur++;
			actualiserScore();
			arret = verifierFinDuJeu(arret);
		}
	}

	public void jouer(Joueur joueur) {
		Color oldColor = joueur.getColor();
		Scanner scanner = new Scanner(System.in);
		System.out.println("\n"+"Joueur " + joueur.getName() + " : Couleur : "+ joueur.getColor());
		System.out.println("Choisissez une couleur : Rouge(1) Bleu(2) Violet(3) Jaune(4) Gris(5) Vert(6)");
		try {
			int couleur = scanner.nextInt();

			if (couleur == 1 && couleur != forbiddenColor && oldColor != Color.RED) {
				joueur.setColor(Color.RED);
			} else if (couleur == 2 && couleur != forbiddenColor && oldColor != Color.BLUE) {
				joueur.setColor(Color.BLUE);
			} else if (couleur == 3 && couleur != forbiddenColor && oldColor != Color.MAGENTA) {
				joueur.setColor(Color.MAGENTA);
			} else if (couleur == 4 && couleur != forbiddenColor && oldColor != Color.YELLOW) {
				joueur.setColor(Color.YELLOW);
			} else if (couleur == 5 && couleur != forbiddenColor && oldColor != Color.ORANGE) {
				joueur.setColor(Color.GRAY);
			} else if (couleur == 6 && couleur != forbiddenColor && oldColor != Color.GREEN) {
				joueur.setColor(Color.GREEN);
			} else {
				System.out.println("Saisie incorrect, selectionnez une nouvelle couleur");
				jouer(joueur);
			}
			forbiddenColor = couleur;
		} catch (Exception e) {
			System.out
					.println("Saisie incorrect, selectionnez une nouvelle couleur");
			jouer(joueur);
		}
	}
	
	public void jouerIA(Joueur joueur){
		Color oldColorIA = joueur.getColor();
		if (difficulteIA == 1){
			jouerIAFacile(joueur);
		}
		if (difficulteIA == 2){
			jouerIAMoyen(joueur);
		}
		if (difficulteIA == 3){
			jouerIADifficile(joueur);
		}
		System.out.println("\n"+"IA a choisi la couleur "+joueur.getColor()+"\n");
	}
	
	public void jouerIAFacile(Joueur joueur) {
		while (joueur.getColor() == oldColorIA){
			joueur.setColor(Case.getRandomColor(sixCouleurs));
		}
		if (joueur.getColor() == Color.RED && forbiddenColor != 1) {
			forbiddenColor = 1;
		} else if (joueur.getColor() == Color.BLUE && forbiddenColor != 2) {
			forbiddenColor = 2;
		} else if (joueur.getColor() == Color.MAGENTA && forbiddenColor != 3) {
			forbiddenColor = 3;
		} else if (joueur.getColor() == Color.YELLOW && forbiddenColor != 4) {
			forbiddenColor = 4;
		} else if (joueur.getColor() == Color.GRAY && forbiddenColor != 5) {
			forbiddenColor = 5;
		} else if (joueur.getColor() == Color.GREEN && forbiddenColor != 6) {
			forbiddenColor = 6;
		}
		else {
			joueur.setColor(oldColorIA);
			jouerIAFacile(joueur);
		}
	}
	
	public void jouerIAMoyen(Joueur joueur){
		reinitialiserPresenceCouleur();
		for (int  i = 0; i < tailleGrille; i++) {
			for (int j = 0; j < tailleGrille;j++) {
				if (grille.getGrille()[i][j].getAppartientA() == joueur.getName()){
					etudierVoisins(i,j,joueur);
				}			
			}
		}
		choisirMeilleureCouleur(joueur);
		if (meilleurePresenceCouleur == 0){
			jouerIAFacile(joueur);
		}
	}
	
	public void jouerIADifficile(Joueur joueur){
		reinitialiserPresenceCouleur();
		for (int  i = 0; i < tailleGrille; i++) {
			for (int j = 0; j < tailleGrille;j++) {
				if (grille.getGrille()[i][j].getAppartientA() == joueur1.getName()){
					etudierVoisins(i,j,joueur);
				}			
			}
		}
		choisirMeilleureCouleur(joueur);
		if (meilleurePresenceCouleur == 0){
			jouerIAFacile(joueur);
		}
	}
	
	public void etudierVoisins(int i, int j, Joueur joueur){
		for (int x = i - 1; x <= i + 1; x++) {
			if (x >= 0 && x < tailleGrille){
				if (grille.getGrille()[x][j].getColor() != joueur.getColor() 
						&& grille.getGrille()[x][j].getColor() != joueur1.getColor()){
					actualiserPresenceCouleur(x,j);
				}
			}
		}
		for (int y = j - 1 ; y <= j + 1; y++) {
			if (y >= 0 && y < tailleGrille){
				if (grille.getGrille()[i][y].getColor() != joueur.getColor()
						&& grille.getGrille()[i][y].getColor() != joueur1.getColor()){
					actualiserPresenceCouleur(i,y);
				}
			}
		}
	}

	public void actualiserPresenceCouleur(int i, int j){
		Color couleurIJ = grille.getGrille()[i][j].getColor();
		if (couleurIJ == Color.RED){
			presenceCouleur[1]++;
		}
		if (couleurIJ == Color.BLUE){
			presenceCouleur[2]++;
		}
		if (couleurIJ == Color.MAGENTA){
			presenceCouleur[3]++;
		}
		if (couleurIJ == Color.YELLOW){
			presenceCouleur[4]++;
		}
		if (couleurIJ == Color.GRAY){
			presenceCouleur[5]++;
		}
		if (couleurIJ == Color.GREEN){
			presenceCouleur[6]++;
		}
	}
	
	public void choisirMeilleureCouleur(Joueur joueur){
		meilleurePresenceCouleur = 0;
		for (int i = 1; i <= 6; i++){
			if (presenceCouleur[i] >= meilleurePresenceCouleur){
				meilleurePresenceCouleur = presenceCouleur[i];
				meilleureCouleur = i;
			}
		}
		if (meilleureCouleur == 1){
			joueur.setColor(Color.RED);
			forbiddenColor = 1;
		}
		else if (meilleureCouleur == 2){
			joueur.setColor(Color.BLUE);
			forbiddenColor = 2;
		}
		else if (meilleureCouleur == 3){
			joueur.setColor(Color.MAGENTA);
			forbiddenColor = 3;
		}
		else if (meilleureCouleur == 4){
			joueur.setColor(Color.YELLOW);
			forbiddenColor = 4;
		}
		else if (meilleureCouleur == 5){
			joueur.setColor(Color.GRAY);
			forbiddenColor = 5;
		}
		else if (meilleureCouleur == 6){
			joueur.setColor(Color.GREEN);
			forbiddenColor = 6;
		}
	}

	public void reinitialiserPresenceCouleur(){
		for (int i = 1; i <= 6; i++){
			presenceCouleur[i] = 0;
		}
	}
	
	public void actualiserScore(){
		totalJ1 = 0;
		totalJ2 = 0;
		for (int i = 0; i < tailleGrille; i++) {
			for (int j = 0; j < tailleGrille; j++) {
				if (grille.getGrille()[i][j].getAppartientA() == joueur1.getName()) {
					totalJ1++;
				}
				if (grille.getGrille()[i][j].getAppartientA() == joueur2.getName()) {
					totalJ2++;
				}
			}
		}
		System.out.println("Score joueur " + joueur1.getName() + " : " + totalJ1);
		System.out.println("Score joueur " + joueur2.getName() + " : " + totalJ2);
	}
	
	public int verifierFinDuJeu(int arret){
		if ((Math.pow(tailleGrille,2)-nbObstacles)/totalJ1 < 2){
			arret = 1;
			System.out.println("Fin du jeu");
			System.out.println("Le Joueur " + joueur1.getName() + " a gagne");
		}
		if ((Math.pow(tailleGrille,2)-nbObstacles)/totalJ2 < 2){
			arret = 1;
			System.out.println("Fin du jeu");
			System.out.println("Le Joueur " + joueur2.getName() + " a gagne");
		}
		if ((Math.pow(tailleGrille,2)-nbObstacles)/totalJ1 == 2 && (Math.pow(tailleGrille,2)-nbObstacles)/totalJ2 == 2){
			arret = 1;
			System.out.println("Egalite");
		}
		return arret;
	}
	
}
