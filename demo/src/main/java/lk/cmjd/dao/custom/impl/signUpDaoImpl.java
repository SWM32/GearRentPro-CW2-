package lk.cmjd.dao.custom.impl;

import java.util.ArrayList;
import org.mindrot.jbcrypt.BCrypt;
import lk.cmjd.dao.CRUDUtil;
import lk.cmjd.dao.custom.signUpDao;
import lk.cmjd.entity.signUpEntity;

public class signUpDaoImpl implements signUpDao {

    @Override
    public boolean save(signUpEntity t) throws Exception {
        String sql = "INSERT INTO user (user_id,username,password,role) VALUES (?,?,?,?)";
        String hashedPassword = BCrypt.hashpw(t.getPassword(), BCrypt.gensalt());
        return CRUDUtil.executeUpdate(sql, t.getUserId(), t.getUsername(), hashedPassword, t.getRole());
    }

    @Override
    public boolean update(signUpEntity t) throws Exception {
        return false;
    }

    @Override
    public boolean delete(String id) throws Exception {
        return false;
    }

    @Override
    public signUpEntity search(String id) throws Exception {
        return null;
    }

    @Override
    public ArrayList<signUpEntity> getAll() throws Exception {
        return null;
    }

}
