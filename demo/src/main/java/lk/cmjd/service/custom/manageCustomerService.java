package lk.cmjd.service.custom;

import java.util.ArrayList;
import lk.cmjd.dto.customerDto;
import lk.cmjd.service.superService;

public interface manageCustomerService extends superService {
    public ArrayList<customerDto> getAll() throws Exception;

    public boolean save(customerDto dto) throws Exception;

    public customerDto search(String id) throws Exception;

    public boolean delete(String id) throws Exception;

    public boolean update(customerDto dto) throws Exception;
}
