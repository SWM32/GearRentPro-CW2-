package lk.cmjd.service.custom;

import java.util.ArrayList;
import lk.cmjd.dto.itemCategoryDto;
import lk.cmjd.service.superService;

public interface itemCategoryService extends superService {
    public ArrayList<itemCategoryDto> getAll() throws Exception;

    public boolean save(itemCategoryDto dto) throws Exception;

    public itemCategoryDto search(String id) throws Exception;

    public boolean delete(String id) throws Exception;

    public boolean update(itemCategoryDto dto) throws Exception;
}
