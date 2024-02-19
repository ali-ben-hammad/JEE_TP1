package Presentation;

import Dao.DaoImp;
import Metier.MetierImp;

public class Main {
    public static void main(String[] args) {
        // Injection des dépendances par instanciation statique
        DaoImp dao = new DaoImp();
        MetierImp metier = new MetierImp(dao);
       // metier.setDao(new DaoImp());
        String days = metier.calcul() + " jours restants";
        System.out.println("resultat avec injection statique = "
                + days);
    }
}
