package com.vogdo.ext;

import com.vogdo.dao.IDao;
//import org.springframework.stereotype.Component;
import com.vogdo.framework.annotations.Component;

@Component("dao2")
public class DaoImplV2 implements IDao {
    @Override
    public double getData() {
        System.out.println("version capteurs...");
        double result = 10;
        return result;
    }
}
