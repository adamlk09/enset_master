package com.vogdo.dao;

//import org.springframework.stereotype.Component;
import com.vogdo.framework.annotations.Component;

@Component("dao")
public class DaoImpl implements IDao {
    @Override
    public double getData() {
        System.out.println("Version base de donnees");
        double t = 6;
        return t;
    }
}
