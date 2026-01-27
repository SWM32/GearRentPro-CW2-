package lk.cmjd.service.custom.impl;

import java.util.ArrayList;

import lk.cmjd.dao.DaoFactory;
import lk.cmjd.dao.DaoFactory.DaoTypes;
import lk.cmjd.dao.custom.manageEquipmentDao;
import lk.cmjd.dto.equipmentDto;
import lk.cmjd.entity.equipmentEntity;
import lk.cmjd.service.custom.manageEquipmentService;

public class manageEquipmentServiceImpl implements manageEquipmentService {

    private manageEquipmentDao dao = (manageEquipmentDao) DaoFactory.getInstance().getDao(DaoTypes.MANAGE_EQUIPMENT);

    @Override
    public ArrayList<equipmentDto> getAll() throws Exception {
        ArrayList<equipmentDto> dtos = new ArrayList<>();
        ArrayList<equipmentEntity> entities = dao.getAll();

        for (equipmentEntity entity : entities) {
            dtos.add(new equipmentDto(entity.getEquipment_id(), entity.getBranch_id(), entity.getCategory_id(),
                    entity.getBrand(), entity.getModel(), entity.getYear(), entity.getBdp(), entity.getSda(),
                    entity.getStatus()));
        }

        return dtos;
    }

    @Override
    public boolean save(equipmentDto dto) throws Exception {
        equipmentEntity entity = new equipmentEntity(dto.getEquipment_id(), dto.getBranch_id(), dto.getCategory_id(),
                dto.getBrand(), dto.getModel(), dto.getYear(), dto.getBdp(), dto.getSda(), dto.getStatus());

        return dao.save(entity);
    }

    @Override
    public equipmentDto search(String id) throws Exception {
        equipmentEntity entity = dao.search(id);

        if (entity != null) {
            return new equipmentDto(entity.getEquipment_id(), entity.getBranch_id(), entity.getCategory_id(),
                    entity.getBrand(), entity.getModel(), entity.getYear(), entity.getBdp(), entity.getSda(),
                    entity.getStatus());
        }

        return null;
    }

    @Override
    public boolean delete(String id) throws Exception {
        return dao.delete(id);
    }

    @Override
    public boolean update(equipmentDto dto) throws Exception {
        equipmentEntity entity = new equipmentEntity(dto.getEquipment_id(), dto.getBranch_id(), dto.getCategory_id(),
                dto.getBrand(), dto.getModel(), dto.getYear(), dto.getBdp(), dto.getSda(), dto.getStatus());
        return dao.update(entity);
    }

}
