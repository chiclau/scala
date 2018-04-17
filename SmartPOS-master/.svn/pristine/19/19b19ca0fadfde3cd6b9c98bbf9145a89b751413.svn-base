package com.yada.spos.db.test;

import com.yada.spos.db.dao.AppGroupDao;
import com.yada.spos.db.dao.AppGroupDevDao;
import com.yada.spos.db.dao.DeviceDao;
import com.yada.spos.db.model.AppGroup;
import com.yada.spos.db.model.AppGroupDev;
import com.yada.spos.db.model.Device;
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

import java.util.List;

/**
 * Created by pangChangSong on 2016/9/11.
 * 应用分组关联设备dao
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SpringConfig.class)
@Transactional
@ActiveProfiles("test")
public class AppGroupDevDaoTest {

    @Autowired
    private AppGroupDao appGroupDao;
    private AppGroup appGroup;
    @Autowired
    private AppGroupDevDao appGroupDevDao;

    @Before
    public void init() {
        appGroup = new AppGroup();
        appGroup.setAppGroupName("应用分组1");
        appGroup.setIsDefaultGroup("0");
        appGroup.setOrgId("000");
        appGroup.setOrgType("2");
        appGroupDao.saveAndFlush(appGroup);
    }

    /**
     * 根据sn和厂商编号查询应用关联设备表
     */
    @Test
    @Rollback
    public void testFindByDevSNAndFirmCode() {
        AppGroupDev appGroupDev = new AppGroupDev();
        appGroupDev.setDevSN("1223");
        appGroupDev.setFirmCode("06");
        appGroupDevDao.saveAndFlush(appGroupDev);
        AppGroupDev appGroupDev1 = appGroupDevDao.findByDevSNAndFirmCode("1223", "06");
        //断言appGroupDev1不为null
        Assert.assertNotNull(appGroupDev1);
        //断言dn是1223， 厂商编号是06
        Assert.assertEquals("1223", appGroupDev1.getDevSN());
        Assert.assertEquals("06", appGroupDev1.getFirmCode());
    }

    @Test
    @Rollback
    public void testFindByAppGroup() {
        AppGroupDev appGroupDev = new AppGroupDev();
        appGroupDev.setAppGroup(appGroup);
        appGroupDev.setDevSN("1223");
        appGroupDev.setFirmCode("06");
        appGroupDevDao.saveAndFlush(appGroupDev);

        List<AppGroupDev> list = appGroupDevDao.findByAppGroup(appGroup);
        Assert.assertEquals(1, list.size());
    }

    @Test
    @Rollback
    public void testDeleteByFirmCodeAndDevSn() {
        AppGroupDev appGroupDev = new AppGroupDev();
        appGroupDev.setDevSN("1223");
        appGroupDev.setFirmCode("06");
        appGroupDevDao.saveAndFlush(appGroupDev);
        int count = appGroupDevDao.deleteByFirmCodeAndDevSn("06", "1223");
        Assert.assertEquals(1, count);
    }
}
