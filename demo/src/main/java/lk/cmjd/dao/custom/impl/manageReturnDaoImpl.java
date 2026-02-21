package lk.cmjd.dao.custom.impl;

import java.sql.ResultSet;
import java.util.ArrayList;

import lk.cmjd.dao.CRUDUtil;
import lk.cmjd.dao.custom.manageReturnDao;
import lk.cmjd.entity.returnEntity;

public class manageReturnDaoImpl implements manageReturnDao {

    @Override
    public boolean save(returnEntity t) throws Exception {
        String sql = "INSERT INTO rental_return VALUES(?,?,?,?,?,?)";
        return CRUDUtil.executeUpdate(sql, t.getRental_Id(), t.getActual_return_date(), t.isDamaged(),
                t.getLate_fee(), t.getDamage_description(), t.getDamage_charge());
    }

    @Override
    public boolean update(returnEntity t) throws Exception {
        String sql = "UPDATE rental_return SET actual_return_date = ?, damaged = ?, late_fee = ?, damage_description = ?, damage_charge = ? WHERE rental_id = ?";
        return CRUDUtil.executeUpdate(sql, t.getActual_return_date(), t.isDamaged(), t.getLate_fee(),
                t.getDamage_description(), t.getDamage_charge(), t.getRental_Id());
    }

    @Override
    public boolean delete(String id) throws Exception {
        String sql = "DELETE FROM rental_return WHERE rental_id = ?";
        return CRUDUtil.executeUpdate(sql, id);
    }

    @Override
    public returnEntity search(String id) throws Exception {
        return null;
    }

    @Override
    public ArrayList<returnEntity> getAll() throws Exception {
        ArrayList<returnEntity> returnEntities = new ArrayList<>();

        String sql = "SELECT * FROM rental_return";
        ResultSet rst = CRUDUtil.executeQuery(sql);

        while (rst.next()) {
            returnEntities
                    .add(new returnEntity(rst.getString("rental_id"), rst.getDate("actual_return_date").toLocalDate(),
                            rst.getBoolean("damaged"), rst.getFloat("late_fee"), rst.getString("damage_description"),
                            rst.getFloat("damage_charge")));
        }

        return returnEntities;
    }

}
