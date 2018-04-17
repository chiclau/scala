package com.yada.spos.db.test;

import com.yada.spos.db.dao.DeviceDao;
import com.yada.spos.db.model.Device;
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
 * 设备dao测试
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SpringConfig.class)
@Transactional
@ActiveProfiles("test")
public class DeviceDaoTest {

    @Autowired
    private DeviceDao deviceDao;

    /**
     * 测试根据厂商编号和设备sn查询设备
     */
    @Test
    @Rollback
    public void testFindByDevSnAndFirmCode() {
        Device device = new Device();
        device.setFirmCode("AAAA");
        device.setDevSn("12345689");
        deviceDao.saveAndFlush(device);
        Device newDevice = deviceDao.findByDevSnAndFirmCode("12345689", "AAAA");
        //断言newDevice非空
        Assert.assertNotNull(newDevice);
        //断言设备sn是12345689， 厂商编号是AAAA
        Assert.assertEquals("12345689", newDevice.getDevSn());
        Assert.assertEquals("AAAA", newDevice.getFirmCode());
    }
}
