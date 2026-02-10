package lk.cmjd.dao.custom.impl;

import java.sql.ResultSet;
import java.util.ArrayList;

import lk.cmjd.dao.CRUDUtil;
import lk.cmjd.dao.custom.membershipDiscountDao;
import lk.cmjd.entity.membershipDiscountEntity;

public class membershipDiscountDaoImpl implements membershipDiscountDao {

    @Override
    public boolean save(membershipDiscountEntity t) throws Exception {
        return false;
    }

    @Override
    public boolean update(membershipDiscountEntity t) throws Exception {
        return false;
    }

    @Override
    public boolean delete(String id) throws Exception {
        return false;
    }

    @Override
    public membershipDiscountEntity search(String id) throws Exception {
        String sql = "SELECT * FROM membership_tier WHERE tier_id = ?";
        ResultSet rst = CRUDUtil.executeQuery(sql, id);

        if (rst.next()) {
            return new membershipDiscountEntity(rst.getString("tier_id"), rst.getString("tier_name"),
                    rst.getFloat("discount"), rst.getFloat("max_deposit"));
        }

        return null;
    }

    @Override
    public ArrayList<membershipDiscountEntity> getAll() throws Exception {
        ArrayList<membershipDiscountEntity> entities = new ArrayList<>();

        String sql = "SELECT * FROM membership_tier";
        ResultSet rst = CRUDUtil.executeQuery(sql);

        while (rst.next()) {
            entities.add(new membershipDiscountEntity(rst.getString("tier_id"), rst.getString("tier_name"),
                    rst.getFloat("discount"), rst.getFloat("max_deposit")));
        }

        return entities;
    }

    @Override
    public boolean assign(String name, Float discount, Float maxDep) throws Exception {
        String sql = "UPDATE membership_tier SET discount = ?, max_deposit = ? WHERE tier_name = ?";
        return CRUDUtil.executeUpdate(sql, discount, maxDep, name);
    }

}
