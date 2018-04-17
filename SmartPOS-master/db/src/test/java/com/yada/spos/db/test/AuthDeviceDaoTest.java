package com.yada.spos.db.test;

import com.yada.spos.db.dao.AuthDeviceDao;
import com.yada.spos.db.model.AuthDevice;
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
 * Created by pangChangSong on 2016/9/11.
 * 母pos dao测试
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SpringConfig.class)
@Transactional
@ActiveProfiles("test")
public class AuthDeviceDaoTest {

    @Autowired
    private AuthDeviceDao authDeviceDao;

    /**
     * 测试根据母pod sn号查询
     */
    @Test
    @Rollback
    public void testFindByAuthPosSn() {
        AuthDevice authDevice = new AuthDevice();
        authDevice.setAuthPosSn("123456789");
        authDevice.setOrgId("000");
        authDeviceDao.saveAndFlush(authDevice);
        AuthDevice newAuthDevice = authDeviceDao.findByAuthPosSn("123456789");
        //断言newAuthDevice非空
        Assert.assertNotNull(newAuthDevice);
        //断言母pod sn是123456789
        Assert.assertEquals("123456789", newAuthDevice.getAuthPosSn());
    }
}
