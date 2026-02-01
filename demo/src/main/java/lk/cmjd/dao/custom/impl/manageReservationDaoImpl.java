package lk.cmjd.dao.custom.impl;

import java.sql.ResultSet;
import java.util.ArrayList;

import lk.cmjd.dao.CRUDUtil;
import lk.cmjd.dao.custom.manageReservationDao;
import lk.cmjd.entity.reservationEntity;

public class manageReservationDaoImpl implements manageReservationDao {

    @Override
    public boolean save(reservationEntity t) throws Exception {
        String sql = "INSERT INTO reservation VALUES(?,?,?,?,?,?,?,?)";
        return CRUDUtil.executeUpdate(sql, t.getReservation_id(), t.getCustomer_id(), t.getEquipment_id(),
                t.getBranch_id(), t.getRevervation_date(), t.getStart_date(), t.getEnd_date(), t.getStatus());
    }

    @Override
    public boolean update(reservationEntity t) throws Exception {
        String sql = "UPDATE reservation SET customer_id = ?, equipment_id = ?, branch_id = ?, reservation_date = ?, start_date = ?, end_date = ?, status = ? WHERE reservation_id = ?";
        return CRUDUtil.executeUpdate(sql, t.getCustomer_id(), t.getEquipment_id(), t.getBranch_id(),
                t.getRevervation_date(), t.getStart_date(), t.getEnd_date(), t.getStatus(), t.getReservation_id());
    }

    @Override
    public boolean delete(String id) throws Exception {
        String sql = "DELETE FROM reservation WHERE reservation_id = ?";
        return CRUDUtil.executeUpdate(sql, id);
    }

    @Override
    public reservationEntity search(String id) throws Exception {
        String sql = "SELECT * FROM reservation WHERE reservation_id = ?";
        ResultSet rst = CRUDUtil.executeQuery(sql, id);

        if (rst.next()) {
            return new reservationEntity(rst.getString("reservation_id"), rst.getString("customer_id"),
                    rst.getString("equipment_id"), rst.getString("branch_id"),
                    rst.getDate("reservation_date").toLocalDate(), rst.getDate("start_date").toLocalDate(),
                    rst.getDate("end_date").toLocalDate(), rst.getString("status"));
        }

        return null;
    }

    @Override
    public ArrayList<reservationEntity> getAll() throws Exception {
        ArrayList<reservationEntity> reservationEntities = new ArrayList<>();

        String sql = "SELECT * FROM reservation";
        ResultSet rst = CRUDUtil.executeQuery(sql);

        while (rst.next()) {
            reservationEntities.add(new reservationEntity(rst.getString("reservation_id"),
                    rst.getString("customer_id"), rst.getString("equipment_id"), rst.getString("branch_id"),
                    rst.getDate("reservation_date").toLocalDate(), rst.getDate("start_date").toLocalDate(),
                    rst.getDate("end_date").toLocalDate(), rst.getString("status")));
        }

        return reservationEntities;
    }

}
