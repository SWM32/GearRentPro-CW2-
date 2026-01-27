package lk.cmjd.dao.custom.impl;

import java.sql.ResultSet;
import java.util.ArrayList;

import lk.cmjd.dao.CRUDUtil;
import lk.cmjd.dao.custom.manageEquipmentDao;
import lk.cmjd.entity.equipmentEntity;

public class manageEquipmentDaoImpl implements manageEquipmentDao {

    @Override
    public boolean save(equipmentEntity t) throws Exception {
        String sql = "INSERT INTO equipment VALUES(?,?,?,?,?,?,?,?,?)";
        return CRUDUtil.executeUpdate(sql, t.getEquipment_id(), t.getBranch_id(), t.getCategory_id(),
                t.getBrand(), t.getModel(), t.getYear(), t.getBdp(), t.getSda(), t.getStatus());
    }

    @Override
    public boolean update(equipmentEntity t) throws Exception {
        String sql = "UPDATE equipment SET branch_id = ?, category_id = ?, brand = ?, model = ?, purchase_year = ?, base_daily_price = ?, security_deposit_amount = ?, status = ? WHERE equipment_id = ?";
        return CRUDUtil.executeUpdate(sql, t.getBranch_id(), t.getCategory_id(), t.getBrand(), t.getModel(),
                t.getYear(), t.getBdp(), t.getSda(), t.getStatus(), t.getEquipment_id());
    }

    @Override
    public boolean delete(String id) throws Exception {
        String sql = "DELETE FROM equipment WHERE equipment_id = ?";
        return CRUDUtil.executeUpdate(sql, id);
    }

    @Override
    public equipmentEntity search(String id) throws Exception {
        String sql = "SELECT * FROM equipment WHERE equipment_id = ?";
        ResultSet rst = CRUDUtil.executeQuery(sql, id);

        if (rst.next()) {
            return new equipmentEntity(rst.getString("equipment_id"), rst.getString("branch_id"),
                    rst.getString("category_id"), rst.getString("brand"), rst.getString("model"),
                    rst.getInt("purchase_year"), rst.getFloat("base_daily_price"),
                    rst.getFloat("security_deposit_amount"), rst.getString("status"));
        }

        return null;
    }

    @Override
    public ArrayList<equipmentEntity> getAll() throws Exception {
        ArrayList<equipmentEntity> equipmentEntities = new ArrayList<>();

        String sql = "SELECT * FROM equipment";
        ResultSet rst = CRUDUtil.executeQuery(sql);

        while (rst.next()) {
            equipmentEntities.add(new equipmentEntity(rst.getString("equipment_id"), rst.getString("branch_id"),
                    rst.getString("category_id"), rst.getString("brand"), rst.getString("model"),
                    rst.getInt("purchase_year"), rst.getFloat("base_daily_price"),
                    rst.getFloat("security_deposit_amount"), rst.getString("status")));
        }

        return equipmentEntities;
    }

}
