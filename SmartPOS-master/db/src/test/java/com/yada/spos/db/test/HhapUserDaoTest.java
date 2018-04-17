package com.yada.spos.db.test;

import com.yada.spos.db.dao.HhapUserDao;
import com.yada.spos.db.model.HhapUser;
import com.yada.spos.db.test.config.SpringConfig;
import org.junit.Assert;
import org.junit.Before;
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
 * 总对总用户dao测试
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SpringConfig.class)
@Transactional
@ActiveProfiles("test")
public class HhapUserDaoTest {

    @Autowired
    private HhapUserDao hhapUserDao;

    @Before
    public void init() {
        HhapUser user = new HhapUser();
        user.setLoginName("admin");
        user.setPwd("111111");
        user.setUserId("1");
        hhapUserDao.saveAndFlush(user);
    }

    /**
     * 根据用户名密码查询用户
     */
    @Test
    @Rollback
    public void testFindByLoginNameAndPwd() {
        HhapUser newHhapUser = hhapUserDao.findByLoginNameAndPwd("admin", "111111");
        //断言newHhapUser存在
        Assert.assertNotNull(newHhapUser);
        //断言用户名是admin 密码是111111
        Assert.assertEquals("admin", newHhapUser.getLoginName());
        Assert.assertEquals("111111", newHhapUser.getPwd());
    }

    /**
     * 根据用户名查询用户
     */
    @Test
    @Rollback
    public void testFindByLoginName() {
        HhapUser newHhapUser = hhapUserDao.findByLoginName("admin");
        //断言newHhapUser存在
        Assert.assertNotNull(newHhapUser);
        //断言用户名是admin
        Assert.assertEquals("admin", newHhapUser.getLoginName());
    }

}
