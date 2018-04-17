package com.yada.spos.db.test;

import com.yada.spos.db.dao.RoleDao;
import com.yada.spos.db.model.Role;
import com.yada.spos.db.test.config.SpringConfig;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by pangChangSong on 2016/9/24.
 * 角色Dao测试
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SpringConfig.class)
@Transactional
@ActiveProfiles("test")
public class RoleDaoTest {

    @Autowired
    private RoleDao roleDao;

    /**
     * 测试根据机构类型和其他系统角色名称查询角色
     */
    @Test
    @Rollback
    public void testFindByOrgTypeAndSysRoleName() {
        Role role = new Role();
        role.setOrgType("2");
        role.setRoleName("gl");
        role.setSysRoleName("bjgl");
        roleDao.saveAndFlush(role);
        Role newRole = roleDao.findByOrgTypeAndSysRoleName("2", "bjgl");
        //断言newRole存在
        Assert.assertNotNull(newRole);
        //断言机构类型是2，系统角色名是bjgl
        Assert.assertEquals("2", newRole.getOrgType());
        Assert.assertEquals("bjgl", newRole.getSysRoleName());
    }
}
