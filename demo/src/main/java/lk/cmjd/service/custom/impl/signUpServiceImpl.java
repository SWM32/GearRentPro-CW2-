package lk.cmjd.service.custom.impl;

import lk.cmjd.dao.DaoFactory;
import lk.cmjd.dao.custom.signUpDao;
import lk.cmjd.dto.signUpDto;
import lk.cmjd.entity.signUpEntity;
import lk.cmjd.service.custom.signUpService;

public class signUpServiceImpl implements signUpService {

    private signUpDao dao = (signUpDao) DaoFactory.getInstance().getDao(DaoFactory.DaoTypes.SIGNUP);

    @Override
    public boolean saveUser(signUpDto dto) throws Exception {
        signUpEntity entity = new signUpEntity(dto.getUserId(), dto.getUsername(), dto.getPassword(), dto.getRole());

        return dao.save(entity);

    }

}
