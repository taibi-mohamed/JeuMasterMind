package jeuMastermind;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class RunMastermind {

	// pour lancer le programme

	public static void main(String[] args) {

		Boolean rejouer;

		// on crée une boucle do while
		do {

			// on genere une combinaison aléatoire de 4 couleurs parmi une liste predefinie
			// de 11 couleurs
			List<String> listeCouleurOrdinateur = donneMoiDesCouleursAuHasard(4);

			// on compare la proposition du joueur avec la combinaison
			// générée par l'ordinateur
			demandeALutilisateurDeDevinerLesCouleurs(listeCouleurOrdinateur);

			// on demande à l'utilisateur de rejouer ou pas
			rejouer = finDePartie();

			// tant que le joueur repond oui
		} while (rejouer);

	}

	private static List<String> donneMoiDesCouleursAuHasard(int nbrDeCouleurSouhaitee) {

		// etape 1 definition des couleurs
		List<String> mesCouleurs = new ArrayList<String>();

		mesCouleurs.add("rouge");

		mesCouleurs.add("bleu");

		mesCouleurs.add("noir");

		mesCouleurs.add("vert");

		mesCouleurs.add("blanc");

		mesCouleurs.add("violet");

		mesCouleurs.add("jaune");

		mesCouleurs.add("orange");

		mesCouleurs.add("beige");

		mesCouleurs.add("gris");

		mesCouleurs.add("fisha");

		System.out.println("Bienvenue dans mon mastermind:\n" + "\n_____ les regles du jeu____"

				+ "\n1.Deviner la combinaison de 4 couleur."
				+ "\n2.Elle peut contenir 1 ou plusieurs couleurs identique."
				+ "\n3.v = bonne couleur a la bonne place ." + "\n4.! = bonne couleur mais pas a la bonne place ."
				+ "\n5.x = mauvaise couleur." + "\n6.Vous avez 12 essais pour gagner.\n");

		System.out.println("_____La liste des couleurs____\n" + mesCouleurs);

		// etape 2 generer chiffre aléatoire
		List<Integer> maCombinaisonAléatoire = new ArrayList<>();

		Random r = new Random();

		nbrDeCouleurSouhaitee--;// on decremente nbre de couleur de -1 pour coller avec l'index 0

		// le nombre de tour dépend du nombre de couleurs à trouver
		for (int i = 0; i <= nbrDeCouleurSouhaitee; i++) {

			// on genere un chiffre aleatoire entre 0 et 10
			int chiffreAleatoireObtenu = r.nextInt(mesCouleurs.size());

			// on stocke le chiffre dans une liste
			maCombinaisonAléatoire.add(chiffreAleatoireObtenu);

		}

		// etape3 on fait le mapping entre chiffres et couleurs
		List<String> couleursAdeviner = new ArrayList<>();

		for (int monchiffre : maCombinaisonAléatoire) {
			// on recupere la couleur a l'index du chiffre
			String couleur = mesCouleurs.get(monchiffre);

			// on stock la couleur dans la liste de couleurs à deviner
			couleursAdeviner.add(couleur);
		}

		// Ligne presente pendant les tests pour s'assurer du bon deroulement de
		// l'application
		System.out.println(couleursAdeviner);

		// a la fin des 4 tours, on renvoie la combinaison de couleurs à deviner
		return couleursAdeviner;

	}

	public static void demandeALutilisateurDeDevinerLesCouleurs(List<String> combinaisonSecrete) {
		// compteur qui decremente
		int essaisRestants = 12;
		// compteur qui incremente
		int numeroEssai = 1;

		ArrayList<String> resultat = new ArrayList<>();

		// on definit les conditions de la victoire
		ArrayList<String> victoire = new ArrayList<String>(Arrays.asList("v", "v", "v", "v"));

		Scanner scanner = new Scanner(System.in);

		// on crée une boucle do while sur la partie
		do {

			// on efface le resultat du precedent passage
			resultat.clear();

			// RECUPERATION SAISIE UTILISATEUR
			System.out.println("Essai N° " + numeroEssai);

			System.out.println("Faites votre combinaison de couleurs avec un espace entre elles :");

			// on recupere la proposition de l'utilisateur
			String couleursUtilisateur = scanner.nextLine();

			// on decoupe la proposition de l'utilisateur en tableau de string
			String[] propositionUtilisateur = couleursUtilisateur.split(" ");

			// CONTROLE DES PARAMETRES
			// on defini la liste des couleurs acceptées
			String pattern = "^(rouge|bleu|noir|vert|blanc|violet|jaune|orange|beige|gris|fisha)$";

			Boolean saisiOK = Boolean.FALSE;
			// Tant que la saisie n'est pas ok, on boucle
			while (!saisiOK) {

				saisiOK = true;
				// on boucle sur les couleurs de l'utilisateur
				for (int i = 0; i < propositionUtilisateur.length; i++) {
					// on compare les couleurs : des qu'une couleur n'est pas presente dans la
					// liste ou mal orthographiée, on sort de cette boucle
					if (!propositionUtilisateur[i].matches(pattern)) {

						saisiOK = false;

						break;

					}

				}

				// On invite l'utilisateur à corriger sa saisie
				if (!saisiOK) {

					System.out.println("Veuillez ecrire la bonne orthographe des couleurs svp :");

					// On recupere la correction
					propositionUtilisateur = scanner.nextLine().split(" ");

				}

			}

			// COMPARAISON ENTRE LA COMBINAISON SECRETE ET LA PROPOSITION DE L'UTILISATEUR
			for (int i = 0; i < propositionUtilisateur.length; i++) {

				String couleurUtilisateurIndexI = propositionUtilisateur[i];

				String couleurOrdinateurIndexI = combinaisonSecrete.get(i);

				if (couleurUtilisateurIndexI.equals(couleurOrdinateurIndexI)) {

					// il a trouvé la bonne couleur à la bonne position

					// faut stocké V à l'indice i dans ma réponse

					resultat.add("v");

				} else if (combinaisonSecrete.contains(couleurUtilisateurIndexI)) {

					// il a trouvé une couleur mais pas au bon emplacement

					// faut stocké ! à l'indice i dans ma réponse

					resultat.add("!");

				} else {

					// la couleur de l'utilisateur ne correspond à aucune couleur de l'ordinateur

					// faut stocké X à l'indice i dans ma réponse

					resultat.add("x");

				}

			}

			System.out.println(resultat);

			essaisRestants--;

			// CONTROLE SUR LA VICTOIRE
			if (resultat.equals(victoire)) {

				System.out.println("Felicitations, vous avez gagne en " + (numeroEssai) + " coup(s) !");

				break;

			}

			// CONTROLE SUR LE NOMBRE D'ESSAIS RESTANTS
			if (essaisRestants == 0) {
				System.out.println(
						"Desole, vous avez epuise tous vos essais. La combinaison etait :" + combinaisonSecrete);

			} else {
				numeroEssai++;
				System.out.println("Il vous reste :" + essaisRestants + " essais");

			}

			// TANT QUE LA VICTOIRE N'EST PAS LA ET QUE
			// LE NOMBRE D'ESSAIS RESTANTS N'EST PAS EGAL A 0
		} while (!resultat.equals(victoire) && !(essaisRestants == 0));

	}

	public static boolean finDePartie() {

		Scanner scanner = new Scanner(System.in);

		boolean nouvellePartie = false;

		String reponse = "";

		System.out.print("Souhaitez-vous refaire une partie ? (o/n)");

		reponse = scanner.nextLine();

		reponse = reponse.toLowerCase();

		if (reponse.equals("o")) {

			nouvellePartie = true;

		} else {

			System.out.println("A bientot !");

		}
		return nouvellePartie;

	}

}
