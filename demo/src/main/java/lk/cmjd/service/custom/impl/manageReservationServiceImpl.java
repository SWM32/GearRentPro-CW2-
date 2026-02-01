package lk.cmjd.service.custom.impl;

import java.util.ArrayList;

import lk.cmjd.dao.DaoFactory;
import lk.cmjd.dao.DaoFactory.DaoTypes;
import lk.cmjd.dao.custom.manageReservationDao;
import lk.cmjd.dto.reservationDto;
import lk.cmjd.entity.reservationEntity;
import lk.cmjd.service.custom.manageReservationService;

public class manageReservationServiceImpl implements manageReservationService {

    private manageReservationDao dao = (manageReservationDao) DaoFactory.getInstance()
            .getDao(DaoTypes.MANAGE_RESERVATION);

    @Override
    public ArrayList<reservationDto> getAll() throws Exception {
        ArrayList<reservationDto> dtos = new ArrayList<>();
        ArrayList<reservationEntity> entities = dao.getAll();

        for (reservationEntity entity : entities) {
            dtos.add(new reservationDto(entity.getReservation_id(), entity.getCustomer_id(), entity.getEquipment_id(),
                    entity.getBranch_id(), entity.getRevervation_date(), entity.getStart_date(), entity.getEnd_date(),
                    entity.getStatus()));
        }

        return dtos;
    }

    @Override
    public boolean save(reservationDto dto) throws Exception {
        reservationEntity entity = new reservationEntity(dto.getReservation_id(), dto.getCustomer_id(),
                dto.getEquipment_id(), dto.getBranch_id(), dto.getRevervation_date(), dto.getStart_date(),
                dto.getEnd_date(), dto.getStatus());

        return dao.save(entity);
    }

    @Override
    public reservationDto search(String id) throws Exception {
        reservationEntity entity = dao.search(id);

        if (entity != null) {
            return new reservationDto(entity.getReservation_id(), entity.getCustomer_id(), entity.getEquipment_id(),
                    entity.getBranch_id(), entity.getRevervation_date(), entity.getStart_date(), entity.getEnd_date(),
                    entity.getStatus());
        }

        return null;
    }

    @Override
    public boolean delete(String id) throws Exception {
        return dao.delete(id);
    }

    @Override
    public boolean update(reservationDto dto) throws Exception {
        reservationEntity entity = new reservationEntity(dto.getReservation_id(), dto.getCustomer_id(),
                dto.getEquipment_id(), dto.getBranch_id(), dto.getRevervation_date(), dto.getStart_date(),
                dto.getEnd_date(), dto.getStatus());
        return dao.update(entity);
    }

}
