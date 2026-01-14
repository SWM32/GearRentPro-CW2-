package lk.cmjd.service.custom;

import lk.cmjd.dto.signUpDto;
import lk.cmjd.service.superService;

public interface signUpService extends superService {
    public boolean saveUser(signUpDto dto) throws Exception;
}
