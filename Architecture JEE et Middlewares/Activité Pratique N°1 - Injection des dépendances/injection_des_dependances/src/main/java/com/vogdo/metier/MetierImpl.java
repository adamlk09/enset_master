package com.vogdo.metier;

import com.vogdo.dao.IDao;
import com.vogdo.framework.annotations.Autowired;
import com.vogdo.framework.annotations.Component;
import com.vogdo.framework.annotations.Qualifier;

@Component("metier")
public class MetierImpl implements IMetier {

    private IDao dao;

    @Autowired
    public MetierImpl(@Qualifier("dao") IDao dao) {
        System.out.println("Instanciation via constructeur avec paramètre");
        this.dao = dao;
    }

    public MetierImpl() {
        System.out.println("Instanciation via constructeur SANS paramètre");
    }

    @Override
    public double calcul() {
        double t = dao.getData();
        double result = t * 6 * Math.pow(10, -6);
        return result;
    }

    public void setDao(IDao dao) {
        System.out.println("Injection via le setter");
        this.dao = dao;
    }
}