package com.vogdo.pres;

import com.vogdo.dao.DaoImpl;
import com.vogdo.ext.DaoImplV2;
import com.vogdo.metier.MetierImpl;

public class Pres1 {
    public static void main(String[] args) {
        //DaoImpl dao = new DaoImpl();
        DaoImplV2 dao = new DaoImplV2();
        MetierImpl m = new MetierImpl(dao);
        //m.setDao(dao); //injection par le setter
        System.out.println("Resultat : " + m.calcul());
    }
}
