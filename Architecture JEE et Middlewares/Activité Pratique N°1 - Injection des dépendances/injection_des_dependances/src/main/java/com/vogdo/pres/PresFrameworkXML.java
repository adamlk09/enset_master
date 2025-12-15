package com.vogdo.pres;

import com.vogdo.framework.context.XmlClassPathApplicationContext;
import com.vogdo.metier.IMetier;

public class PresFrameworkXML {
    public static void main(String[] args) {
        XmlClassPathApplicationContext context = new XmlClassPathApplicationContext("my-config.xml");
        IMetier metier = (IMetier) context.getBean("metier");

        System.out.println("Résultat (Framework XML) : " + metier.calcul());
    }
}