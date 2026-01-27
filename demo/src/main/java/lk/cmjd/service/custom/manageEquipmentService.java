package lk.cmjd.service.custom;

import java.util.ArrayList;
import lk.cmjd.dto.equipmentDto;
import lk.cmjd.service.superService;

public interface manageEquipmentService extends superService {
    public ArrayList<equipmentDto> getAll() throws Exception;

    public boolean save(equipmentDto dto) throws Exception;

    public equipmentDto search(String id) throws Exception;

    public boolean delete(String id) throws Exception;

    public boolean update(equipmentDto dto) throws Exception;
}
