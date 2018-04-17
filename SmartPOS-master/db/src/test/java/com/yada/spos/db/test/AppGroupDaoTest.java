package com.yada.spos.db.test;

import com.yada.spos.db.dao.AppGroupDao;
import com.yada.spos.db.model.AppGroup;
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
 * Created by pangChangSong on 2016/9/11.
 * 应用分组dao测试类
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SpringConfig.class)
@Transactional
@ActiveProfiles("test")
public class AppGroupDaoTest {

    @Autowired
    private AppGroupDao appGroupDao;

    @Before
    public void init() {
        AppGroup appGroup = new AppGroup();
        appGroup.setAppGroupName("应用分组1");
        appGroup.setOrgId("000");
        appGroup.setIsDefaultGroup("0");
        appGroup.setOrgType("2");
        appGroupDao.saveAndFlush(appGroup);
    }

    /**
     * 查询机构类型下，机构的默认分组
     */
    @Test
    @Rollback
    public void testFindByOrgIdAndOrgTypeAndIsDefaultGroup() {
        AppGroup appGroup = appGroupDao.findByOrgIdAndOrgTypeAndIsDefaultGroup("000", "2", "0");
        Assert.assertNotNull(appGroup);
    }

    @Test
    @Rollback
    public void testFindByAppGroupNameAndOrgIdAndOrgType() {
        Assert.assertNotNull(appGroupDao.findByAppGroupNameAndOrgIdAndOrgType("应用分组1", "000", "2"));
    }

    @Test
    @Rollback
    public void testUpdateAppGroup() {
        AppGroup appGroup = new AppGroup();
        appGroup.setAppGroupName("应用分组1");
        appGroup.setOrgId("00011");
        appGroup.setIsDefaultGroup("0");
        appGroup.setOrgType("2");
        AppGroup newAppGroup = appGroupDao.saveAndFlush(appGroup);
        int count = appGroupDao.updateAppGroup(newAppGroup.getAppGroupId() + "", "应用分组2", "sdfdf");
        AppGroup newAppGroup1 = appGroupDao.findOne(newAppGroup.getAppGroupId());
        Assert.assertEquals(1, count);
        Assert.assertEquals("应用分组2", newAppGroup1.getAppGroupName());
        Assert.assertEquals("sdfdf", newAppGroup1.getAppGroupDesc());
    }
}
