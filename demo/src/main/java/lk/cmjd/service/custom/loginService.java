package lk.cmjd.service.custom;

import lk.cmjd.dto.loginDto;
import lk.cmjd.service.superService;

public interface loginService extends superService {
    public loginDto loginUser(String id) throws Exception;
}
