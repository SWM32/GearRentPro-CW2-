package lk.cmjd.service.custom;

import java.util.ArrayList;
import lk.cmjd.dto.returnDto;
import lk.cmjd.service.superService;

public interface manageReturnService extends superService {
    public ArrayList<returnDto> getAll() throws Exception;

    public boolean save(returnDto dto) throws Exception;

    public boolean delete(String id) throws Exception;

    public boolean update(returnDto dto) throws Exception;
}
