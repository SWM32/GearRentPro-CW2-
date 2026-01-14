package lk.cmjd.dao.custom.impl;

import java.sql.ResultSet;
import java.util.ArrayList;
import lk.cmjd.dao.CRUDUtil;
import lk.cmjd.dao.custom.loginDao;
import lk.cmjd.entity.loginEntity;

public class loginDaoImpl implements loginDao {

    @Override
    public boolean save(loginEntity t) throws Exception {
        return false;
    }

    @Override
    public boolean update(loginEntity t) throws Exception {
        return false;
    }

    @Override
    public boolean delete(String id) throws Exception {
        return false;
    }

    @Override
    public loginEntity search(String id) throws Exception {
        String sql = "SELECT * FROM user WHERE user_id = ?";
        ResultSet rst = CRUDUtil.executeQuery(sql, id);

        if (rst.next()) {
            return new loginEntity(
                    rst.getString("user_id"),
                    rst.getString("username"),
                    rst.getString("password"),
                    rst.getString("role"));
        }
        return null;
    }

    @Override
    public ArrayList<loginEntity> getAll() throws Exception {
        return null;
    }

}
