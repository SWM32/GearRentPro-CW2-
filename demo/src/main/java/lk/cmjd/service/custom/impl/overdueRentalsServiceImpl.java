package lk.cmjd.service.custom.impl;

import java.util.ArrayList;

import lk.cmjd.dao.DaoFactory;
import lk.cmjd.dao.DaoFactory.DaoTypes;
import lk.cmjd.dao.custom.overdueRentalsDao;
import lk.cmjd.dto.overdueDto;
import lk.cmjd.entity.overdueEntity;
import lk.cmjd.service.custom.overdueRentalsService;

public class overdueRentalsServiceImpl implements overdueRentalsService {

    private overdueRentalsDao dao = (overdueRentalsDao) DaoFactory.getInstance()
            .getDao(DaoTypes.OVERDUE_RENTALS);

    @Override
    public ArrayList<overdueDto> getAll() throws Exception {
        ArrayList<overdueDto> dtos = new ArrayList<>();
        ArrayList<overdueEntity> entities = dao.getAll();

        for (overdueEntity e : entities) {
            dtos.add(new overdueDto(e.getCusID(), e.getBranchID(), e.getEqID(), e.getDue(), e.getDueDates(),
                    e.getContact(), e.getEmail()));
        }

        return dtos;
    }

}
