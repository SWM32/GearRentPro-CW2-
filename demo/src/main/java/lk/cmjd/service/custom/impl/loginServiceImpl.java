package lk.cmjd.service.custom.impl;

import lk.cmjd.dao.DaoFactory;
import lk.cmjd.dao.custom.loginDao;
import lk.cmjd.dto.loginDto;
import lk.cmjd.entity.loginEntity;
import lk.cmjd.service.custom.loginService;

public class loginServiceImpl implements loginService {

    private loginDao dao = (loginDao) DaoFactory.getInstance().getDao(DaoFactory.DaoTypes.LOGIN);

    @Override
    public loginDto loginUser(String id) throws Exception {
        loginEntity entity = dao.search(id);

        if (entity != null) {
            return new loginDto(entity.getUserId(), entity.getUsername(), entity.getPassword(), entity.getRole(),
                    entity.getBranch());
        }

        return null;
    }

}
