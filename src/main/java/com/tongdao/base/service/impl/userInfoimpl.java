package com.tongdao.base.service.impl;

import com.tongdao.base.commonDao.userInfoDao;
import com.tongdao.base.model.User.Userinfo;
import com.tongdao.base.service.userInfoService;
import com.tongdao.core.Dao.beans.Method;
import com.tongdao.core.Dao.sql.where.C;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class userInfoimpl implements userInfoService {

    @Resource
    private userInfoDao userInfodao;

    @Override
    public int save(Userinfo userinfo) {

        return userInfodao.add(userinfo);
    }

    @Override
    public List<Userinfo> check(String appopenid) {

        return userInfodao.list(Method.where("open_id",C.EQ,appopenid));
    }
}
