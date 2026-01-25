package lk.cmjd.dao.custom.impl;

import java.sql.ResultSet;
import java.util.ArrayList;
import lk.cmjd.dao.CRUDUtil;
import lk.cmjd.dao.custom.itemCategoryDao;
import lk.cmjd.entity.itemCategoryEntity;

public class itemCategoryDaoImpl implements itemCategoryDao {

    @Override
    public boolean save(itemCategoryEntity t) throws Exception {
        String sql = "INSERT INTO equipment_category VALUES(?,?,?,?,?,?,?)";
        return CRUDUtil.executeUpdate(sql, t.getCategoryID(), t.getName(), t.getDescription(), t.getBPF(), t.getWM(),
                t.getLRDR(), t.getLFPD());
    }

    @Override
    public boolean update(itemCategoryEntity t) throws Exception {
        String sql = "UPDATE equipment_category SET name = ?, description = ?, base_price_factor = ?, weekend_multiplier = ?, long_rental_discount_rate = ?, default_late_fee_per_day = ? WHERE category_id = ?";
        return CRUDUtil.executeUpdate(sql, t.getName(), t.getDescription(), t.getBPF(), t.getWM(), t.getLRDR(),
                t.getLFPD(), t.getCategoryID());
    }

    @Override
    public boolean delete(String id) throws Exception {
        String sql = "DELETE FROM equipment_category WHERE category_id = ?";
        return CRUDUtil.executeUpdate(sql, id);
    }

    @Override
    public itemCategoryEntity search(String id) throws Exception {
        String sql = "SELECT * FROM equipment_category WHERE category_id = ?";
        ResultSet rst = CRUDUtil.executeQuery(sql, id);

        if (rst.next()) {
            return new itemCategoryEntity(rst.getString("category_id"),
                    rst.getString("name"),
                    rst.getString("description"),
                    rst.getFloat("base_price_factor"),
                    rst.getFloat("weekend_multiplier"),
                    rst.getFloat("long_rental_discount_rate"),
                    rst.getFloat("default_late_fee_per_day"));
        }

        return null;
    }

    @Override
    public ArrayList<itemCategoryEntity> getAll() throws Exception {
        ArrayList<itemCategoryEntity> itemCategoryEntities = new ArrayList<>();

        String sql = "SELECT * FROM equipment_category";
        ResultSet rst = CRUDUtil.executeQuery(sql);

        while (rst.next()) {
            itemCategoryEntities.add(new itemCategoryEntity(
                    rst.getString("category_id"),
                    rst.getString("name"),
                    rst.getString("description"),
                    rst.getFloat("base_price_factor"),
                    rst.getFloat("weekend_multiplier"),
                    rst.getFloat("long_rental_discount_rate"),
                    rst.getFloat("default_late_fee_per_day")));
        }

        return itemCategoryEntities;
    }

}
