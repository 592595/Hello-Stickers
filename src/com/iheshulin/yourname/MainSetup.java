package com.iheshulin.yourname;

import com.iheshulin.yourname.bean.User;
import org.nutz.dao.Dao;
import org.nutz.dao.util.Daos;
import org.nutz.ioc.Ioc;
import org.nutz.mvc.NutConfig;
import org.nutz.mvc.Setup;

public class MainSetup implements Setup{
    public static Ioc ioc;
    @Override
    public void destroy(NutConfig arg0) {

    }

    @Override
    public void init(NutConfig conf) {
        MainSetup.ioc = conf.getIoc();
        Dao dao = ioc.get(Dao.class);
        Daos.createTablesInPackage(dao, "com.iheshulin.yourname", false);

        if (dao.count(User.class) == 0){
            User user = new User();
            user.setUsername("heshulin");
            user.setPassword("123");
            dao.insert(user);
        }
    }

}