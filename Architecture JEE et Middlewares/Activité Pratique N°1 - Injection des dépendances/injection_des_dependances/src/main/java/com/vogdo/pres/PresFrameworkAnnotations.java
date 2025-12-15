package com.vogdo.pres;

import com.vogdo.framework.context.AnnotationConfigApplicationContext;
import com.vogdo.metier.IMetier;

public class PresFrameworkAnnotations {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext("com.vogdo");
        IMetier metier = context.getBean(IMetier.class);

        System.out.println("Résultat (Framework Annotations) : " + metier.calcul());
    }
}