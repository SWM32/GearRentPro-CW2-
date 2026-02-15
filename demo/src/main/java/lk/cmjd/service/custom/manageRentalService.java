package lk.cmjd.service.custom;

import java.time.LocalDate;
import java.util.ArrayList;

import lk.cmjd.dto.equipmentDto;
import lk.cmjd.dto.itemCategoryDto;
import lk.cmjd.dto.rentalDto;
import lk.cmjd.service.superService;

public interface manageRentalService extends superService {
    public ArrayList<rentalDto> getAll() throws Exception;

    public boolean save(rentalDto dto) throws Exception;

    public rentalDto search(String id) throws Exception;

    public boolean delete(String id) throws Exception;

    public boolean update(rentalDto dto) throws Exception;

    public float totalRent(equipmentDto eq, itemCategoryDto cat, LocalDate start_date, LocalDate due_date)
            throws Exception;

    public String getLastID() throws Exception;
}
