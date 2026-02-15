package lk.cmjd.service.custom.impl;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;

import lk.cmjd.dao.DaoFactory;
import lk.cmjd.dao.DaoFactory.DaoTypes;
import lk.cmjd.dao.custom.manageRentalDao;
import lk.cmjd.dto.equipmentDto;
import lk.cmjd.dto.itemCategoryDto;
import lk.cmjd.dto.rentalDto;
import lk.cmjd.entity.rentalEntity;
import lk.cmjd.service.custom.manageRentalService;

public class manageRentalServiceImpl implements manageRentalService {

    private manageRentalDao dao = (manageRentalDao) DaoFactory.getInstance().getDao(DaoTypes.MANAGE_RENTAL);

    @Override
    public ArrayList<rentalDto> getAll() throws Exception {
        ArrayList<rentalDto> dtos = new ArrayList<>();
        ArrayList<rentalEntity> entities = dao.getAll();

        for (rentalEntity entity : entities) {
            dtos.add(new rentalDto(entity.getRental_id(), entity.getCustomer_id(), entity.getEquipment_id(),
                    entity.getBranch_id(), entity.getStart_date(), entity.getDue_date(),
                    entity.getActual_return_date(), entity.getTotal_rent(), entity.getSdh(), entity.getMdh(),
                    entity.getLrd(), entity.getFinal_pay(), entity.getPayment_status(), entity.getRental_status()));
        }

        return dtos;
    }

    @Override
    public boolean save(rentalDto dto) throws Exception {
        rentalEntity entity = new rentalEntity(dto.getRental_id(), dto.getCustomer_id(), dto.getEquipment_id(),
                dto.getBranch_id(), dto.getStart_date(), dto.getDue_date(), dto.getActual_return_date(),
                dto.getTotal_rent(), dto.getSdh(), dto.getMdh(), dto.getLrd(), dto.getFinal_pay(),
                dto.getPayment_status(), dto.getRental_status());

        return dao.save(entity);
    }

    @Override
    public rentalDto search(String id) throws Exception {
        rentalEntity entity = dao.search(id);

        if (entity != null) {
            return new rentalDto(entity.getRental_id(), entity.getCustomer_id(), entity.getEquipment_id(),
                    entity.getBranch_id(), entity.getStart_date(), entity.getDue_date(),
                    entity.getActual_return_date(), entity.getTotal_rent(), entity.getSdh(), entity.getMdh(),
                    entity.getLrd(), entity.getFinal_pay(), entity.getPayment_status(), entity.getRental_status());
        }

        return null;
    }

    @Override
    public boolean delete(String id) throws Exception {
        return dao.delete(id);
    }

    @Override
    public boolean update(rentalDto dto) throws Exception {
        rentalEntity entity = new rentalEntity(dto.getRental_id(), dto.getCustomer_id(), dto.getEquipment_id(),
                dto.getBranch_id(), dto.getStart_date(), dto.getDue_date(), dto.getActual_return_date(),
                dto.getTotal_rent(), dto.getSdh(), dto.getMdh(), dto.getLrd(), dto.getFinal_pay(),
                dto.getPayment_status(), dto.getRental_status());
        return dao.update(entity);
    }

    @Override
    public float totalRent(equipmentDto eq, itemCategoryDto cat, LocalDate start_date, LocalDate due_date)
            throws Exception {

        float bdp = eq.getBdp();
        float bpf = cat.getBPF();
        float wm = cat.getWM();

        float total = 0;
        LocalDate currentDate = start_date;
        float fbdp;

        while (!currentDate.isAfter(due_date)) {
            fbdp = bdp + bdp * bpf / 100;

            if (isWeekend(currentDate)) {
                fbdp += bdp * wm / 100;
            }

            total += fbdp;
            currentDate = currentDate.plusDays(1);
        }

        return total;

    }

    private boolean isWeekend(LocalDate date) {
        DayOfWeek day = date.getDayOfWeek();
        return day == DayOfWeek.SATURDAY || day == DayOfWeek.SUNDAY;
    }

    @Override
    public String getLastID() throws Exception {
        return dao.getLastID();
    }

}
