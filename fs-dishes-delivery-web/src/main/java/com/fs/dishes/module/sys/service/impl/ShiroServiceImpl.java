package com.fs.dishes.module.sys.service.impl;

import com.fs.dishes.base.common.Constant;
import com.fs.dishes.module.sys.dao.SysMenuDao;
import com.fs.dishes.module.sys.dao.SysUserDao;
import com.fs.dishes.module.sys.dao.SysUserTokenDao;
import com.fs.dishes.module.sys.entity.SysMenu;
import com.fs.dishes.module.sys.entity.SysUser;
import com.fs.dishes.module.sys.entity.SysUserToken;
import com.fs.dishes.module.sys.service.ShiroService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class ShiroServiceImpl implements ShiroService {
    @Autowired
    private SysMenuDao sysMenuDao;
    @Autowired
    private SysUserDao sysUserDao;
    @Autowired
    private SysUserTokenDao sysUserTokenDao;

    @Override
    public Set<String> getUserPermissions(String userId) {
        List<String> permsList;

        //系统管理员，拥有最高权限
        if (userId == Constant.SUPER_ADMIN) {
            List<SysMenu> menuList = sysMenuDao.queryList(new HashMap<>());
            permsList = new ArrayList<>(menuList.size());
            for (SysMenu menu : menuList) {
                permsList.add(menu.getPerms());
            }
        } else {
            permsList = sysUserDao.queryAllPerms(userId);
        }
        //用户权限列表
        Set<String> permsSet = new HashSet<>();
        for (String perms : permsList) {
            if (StringUtils.isBlank(perms)) {
                continue;
            }
            permsSet.addAll(Arrays.asList(perms.trim().split(",")));
        }
        return permsSet;
    }

    @Override
    public SysUserToken queryByToken(String token) {
        return sysUserTokenDao.queryByToken(token);
    }

    @Override
    public SysUser queryUser(String userId) {
        return sysUserDao.queryObject(userId);
    }
}
