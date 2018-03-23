package com.tongdao.base.service;

import com.tongdao.base.model.User.Userinfo;

import java.util.List;

public interface userInfoService {

    public int save(Userinfo userinfo);

    public List<Userinfo> check(String appopenid);
}
