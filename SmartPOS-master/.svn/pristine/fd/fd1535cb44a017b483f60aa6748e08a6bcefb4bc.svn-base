package com.yada.spos.db.test;

import com.yada.spos.db.dao.AppGroupAppsDao;
import com.yada.spos.db.dao.AppGroupDao;
import com.yada.spos.db.model.AppGroup;
import com.yada.spos.db.model.AppGroupApps;
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
 * 应用分组关联应用dao测试
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SpringConfig.class)
@Transactional
@ActiveProfiles("test")
public class AppGroupAppsDaoTest {

    @Autowired
    private AppGroupAppsDao appGroupAppsDao;
    @Autowired
    private AppGroupDao appGroupDao;
    private AppGroup appGroup;

    @Before
    public void init() {
        appGroup = new AppGroup();
        appGroup.setAppGroupName("应用分组1");
        appGroup.setOrgId("000");
        appGroup.setOrgType("2");
        appGroup.setIsDefaultGroup("0");
        appGroupDao.saveAndFlush(appGroup);
    }

    /**
     * 测试根据应用分组查询应用
     */
    @Test
    @Rollback
    public void testFindByAppGroup() {
        AppGroupApps appGroupApps = new AppGroupApps();
        appGroupApps.setAppGroupDetailId(1L);
        appGroupApps.setAppGroup(appGroup);
        appGroupApps.setAppGroupName(appGroup.getAppGroupName());
        appGroupAppsDao.saveAndFlush(appGroupApps);
        //根据应用分组查询应用分组关联应用
        java.util.List<AppGroupApps> list = appGroupAppsDao.findByAppGroup(appGroup);
        //断言集合中有一个元素
        Assert.assertEquals(1, list.size());
    }

    /**
     * 测试根据包名，是否是默认组，机构号和机构类型查询应用分组关联应用
     */
    @Test
    @Rollback
    public void testFindByAppPackageNameAndAppGroupIsDefaultGroupAndAppGroupOrgIdAndAppGroupOrgType() {
        AppGroupApps appGroupApps = new AppGroupApps();
        appGroupApps.setAppGroupDetailId(1L);
        appGroupApps.setAppGroup(appGroup);
        appGroupApps.setAppGroupName(appGroup.getAppGroupName());
        appGroupApps.setAppPackageName("qq.com");
        appGroupAppsDao.saveAndFlush(appGroupApps);
        AppGroupApps result = appGroupAppsDao.findByAppPackageNameAndAppGroupIsDefaultGroupAndAppGroupOrgIdAndAppGroupOrgType("qq.com", "0", "000", "2");
        //断言result不为null
        Assert.assertNotNull(result);
        //断言包名是qq.com, 是否默认组0，机构号000， 机构类型2
        Assert.assertEquals("qq.com", result.getAppPackageName());
        Assert.assertEquals("0", result.getAppGroup().getIsDefaultGroup());
        Assert.assertEquals("000", result.getAppGroup().getOrgId());
        Assert.assertEquals("2", result.getAppGroup().getOrgType());
    }

    @Test
    @Rollback
    public void testFindByAppPackageNameAndAppGroup() {
        AppGroupApps appGroupApps = new AppGroupApps();
        appGroupApps.setAppGroupDetailId(1L);
        appGroupApps.setAppGroup(appGroup);
        appGroupApps.setAppGroupName(appGroup.getAppGroupName());
        appGroupApps.setAppPackageName("qq.com");
        appGroupAppsDao.saveAndFlush(appGroupApps);
        AppGroupApps result = appGroupAppsDao.findByAppPackageNameAndAppGroup("qq.com", appGroup);
        Assert.assertNotNull(result);
        //断言包名是qq.com, 是否默认组0，机构号000， 机构类型2
        Assert.assertEquals("qq.com", result.getAppPackageName());
        Assert.assertEquals("0", result.getAppGroup().getIsDefaultGroup());
        Assert.assertEquals("000", result.getAppGroup().getOrgId());
        Assert.assertEquals("2", result.getAppGroup().getOrgType());
    }

    @Test
    @Rollback
    public void testUpdateAppGroupName() {
        AppGroup appGroup = new AppGroup();
        appGroup.setAppGroupName("应用分组1");
        appGroup.setOrgId("000");
        appGroup.setOrgType("2");
        appGroup.setIsDefaultGroup("0");
        AppGroup newAppGroup = appGroupDao.saveAndFlush(appGroup);

        AppGroupApps appGroupApps = new AppGroupApps();
        appGroupApps.setAppGroupDetailId(1L);
        appGroupApps.setAppGroup(appGroup);
        appGroupApps.setAppGroupName(appGroup.getAppGroupName());
        appGroupAppsDao.saveAndFlush(appGroupApps);
        int count = appGroupAppsDao.updateAppGroupName(newAppGroup.getAppGroupId() + "", "应用分组2");
        List<AppGroupApps> list = appGroupAppsDao.findByAppGroup(newAppGroup);
        //断言更新一条数据
        Assert.assertEquals(1, count);
        //断言应用分组名称变为应用分组2
        Assert.assertEquals("应用分组2", list.get(0).getAppGroupName());
    }
}
