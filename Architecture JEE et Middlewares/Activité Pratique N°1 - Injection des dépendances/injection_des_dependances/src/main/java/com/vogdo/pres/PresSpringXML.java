package com.vogdo.pres;

import com.vogdo.metier.IMetier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class PresSpringXML {
    public static void main(String[] args) {
        ApplicationContext springContext = new ClassPathXmlApplicationContext("config.xml");
        //IMetier metier = (IMetier) springContext.getBean("metier");
        IMetier metier = springContext.getBean(IMetier.class); //sans faire le casting
        System.out.println("resultat : "+metier.calcul());
    }
}
