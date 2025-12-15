package com.vogdo.pres;

import com.vogdo.dao.IDao;
import com.vogdo.metier.IMetier;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Scanner;

public class Pres2 {
    public static void main(String[] args) throws FileNotFoundException, ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        Scanner sc = new Scanner(new File("config.txt"));

        String daoClassName = sc.nextLine();
        Class cDao = Class.forName(daoClassName);
        IDao dao = (IDao) cDao.newInstance();
        //System.out.println(dao.getData());

        String metierClassName = sc.nextLine();
        Class cMetier = Class.forName(metierClassName);
        //IMetier metier = (IMetier) cMetier.getConstructor(IDao.class).newInstance(dao); //le constructor avec parametre
        IMetier metier = (IMetier) cMetier.getConstructor().newInstance(); //le constructor sans parametres
        Method setDao = cMetier.getDeclaredMethod("setDao", IDao.class);
        setDao.invoke(metier, dao);

        System.out.println("resultat : "+metier.calcul());
    }
}
