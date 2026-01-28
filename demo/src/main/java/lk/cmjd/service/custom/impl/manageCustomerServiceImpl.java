package lk.cmjd.service.custom.impl;

import java.util.ArrayList;

import lk.cmjd.dao.DaoFactory;
import lk.cmjd.dao.DaoFactory.DaoTypes;
import lk.cmjd.dao.custom.manageCustomerDao;
import lk.cmjd.dto.customerDto;
import lk.cmjd.entity.customerEntity;
import lk.cmjd.service.custom.manageCustomerService;

public class manageCustomerServiceImpl implements manageCustomerService {

    private manageCustomerDao dao = (manageCustomerDao) DaoFactory.getInstance().getDao(DaoTypes.MANAGE_CUSTOMER);

    @Override
    public ArrayList<customerDto> getAll() throws Exception {
        ArrayList<customerDto> dtos = new ArrayList<>();
        ArrayList<customerEntity> entities = dao.getAll();

        for (customerEntity entity : entities) {
            dtos.add(new customerDto(entity.getCusId(), entity.getName(), entity.getNic_pass(), entity.getContact(),
                    entity.getEmail(), entity.getAddress(), entity.getMid()));
        }

        return dtos;
    }

    @Override
    public boolean save(customerDto dto) throws Exception {
        customerEntity entity = new customerEntity(dto.getCusId(), dto.getName(), dto.getNic_pass(), dto.getContact(),
                dto.getEmail(), dto.getAddress(), dto.getMid());

        return dao.save(entity);
    }

    @Override
    public customerDto search(String id) throws Exception {
        customerEntity entity = dao.search(id);

        if (entity != null) {
            return new customerDto(entity.getCusId(), entity.getName(), entity.getNic_pass(), entity.getContact(),
                    entity.getEmail(), entity.getAddress(), entity.getMid());
        }

        return null;
    }

    @Override
    public boolean delete(String id) throws Exception {
        return dao.delete(id);
    }

    @Override
    public boolean update(customerDto dto) throws Exception {
        customerEntity entity = new customerEntity(dto.getCusId(), dto.getName(), dto.getNic_pass(), dto.getContact(),
                dto.getEmail(), dto.getAddress(), dto.getMid());
        return dao.update(entity);
    }

}
