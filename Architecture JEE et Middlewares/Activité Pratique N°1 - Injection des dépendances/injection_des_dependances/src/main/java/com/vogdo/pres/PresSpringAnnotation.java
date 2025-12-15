package com.vogdo.pres;

import com.vogdo.metier.IMetier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class PresSpringAnnotation {
    public static void main(String[] args) {
        ApplicationContext applicationContext =
                //new AnnotationConfigApplicationContext("com.vogdo.ext", "com.vogdo.metier");
                new AnnotationConfigApplicationContext("com.vogdo");
        IMetier metier = applicationContext.getBean(IMetier.class);
        System.out.println("resultat : "+metier.calcul());
    }
}
