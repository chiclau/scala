package com.yada.spos.db.test;

import com.yada.spos.db.dao.DeviceInfoUpDao;
import com.yada.spos.db.model.DeviceInfoUp;
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
 * 终端应用信息上送dao测试
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SpringConfig.class)
@Transactional
@ActiveProfiles("test")
public class DeviceInfoUpDaoTest {

    @Autowired
    private DeviceInfoUpDao deviceInfoUpDao;

    @Before
    public void init() {
        DeviceInfoUp deviceInfoUp = new DeviceInfoUp();
        deviceInfoUp.setDevSn("123456789");
        deviceInfoUp.setFirmCode("AAAA");
        deviceInfoUp.setPkgName("qq.com");
        deviceInfoUp.setModuleType("1");
        deviceInfoUp.setInfoStatus("1");
        deviceInfoUp.setId(1L);
        deviceInfoUpDao.saveAndFlush(deviceInfoUp);
    }

    /**
     * 测试根据设备sn和厂商编号更新终端应用信息的状态
     */
    @Test
    @Rollback
    public void testUpdateInfoStatusByDevSnAndFirmCode() {
        int count = deviceInfoUpDao.updateInfoStatusByDevSnAndFirmCode("2", "123456789", "AAAA");
        //断言count是1-更新一条数据
        Assert.assertEquals(1, count);
    }

    /**
     * 根据sn,厂商代码和包名,包类型通过数据库获取终端应用信息上送
     */
    @Test
    @Rollback
    public void testFindByDevSnAndFirmCodeAndPkgNameAndModuleType() {
        DeviceInfoUp newDevicecInfoUp = deviceInfoUpDao.findByDevSnAndFirmCodeAndPkgNameAndModuleType("123456789", "AAAA", "qq.com", "1");
        //断言newDevicecInfoUp非空
        Assert.assertNotNull(newDevicecInfoUp);
        //断言设备sn是123456789，厂商编号是AAAA，包名是qq.com,类型是1
        Assert.assertEquals("123456789", newDevicecInfoUp.getDevSn());
        Assert.assertEquals("AAAA", newDevicecInfoUp.getFirmCode());
        Assert.assertEquals("qq.com", newDevicecInfoUp.getPkgName());
        Assert.assertEquals("1", newDevicecInfoUp.getModuleType());
    }
}
