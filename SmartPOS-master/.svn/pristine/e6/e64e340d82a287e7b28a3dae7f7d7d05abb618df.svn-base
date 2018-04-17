package com.yada.spos.db.test;

import com.yada.spos.db.dao.AppFileLatestDao;
import com.yada.spos.db.model.AppFileLatest;
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
 * Created by pangChangSong on 2016/9/24.
 * 应用文件最新版本dao测试
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SpringConfig.class)
@Transactional
@ActiveProfiles("test")
public class AppFileLatestDaoTest {

    @Autowired
    private AppFileLatestDao appFileLatestDao;

    @Before
    public void init() {
        AppFileLatest appFileLatest = new AppFileLatest();
        appFileLatest.setAppFileId(1L);
        appFileLatest.setAppPackageName("qq.com");
        appFileLatest.setVersionCode("1.1");
        appFileLatest.setOrgId("000");
        appFileLatest.setOrgType("2");
        appFileLatestDao.saveAndFlush(appFileLatest);

        AppFileLatest appFileLatest1 = new AppFileLatest();
        appFileLatest1.setAppFileId(2L);
        appFileLatest1.setAppPackageName("qq1.com");
        appFileLatest1.setVersionCode("1.1");
        appFileLatest1.setOrgId("000011");
        appFileLatest1.setOrgType("2");
        appFileLatestDao.saveAndFlush(appFileLatest1);
    }

    /**
     * 测试根据包名查询应用最新版本
     */
    @Test
    @Rollback
    public void testFindByAppPackageNameAndOrgIdAndOrgType() {
        AppFileLatest newAppFileLatest = appFileLatestDao.findByAppPackageNameAndOrgIdAndOrgType("qq.com", "000", "2");
        //断言newAppFileLatest不为null
        Assert.assertNotNull(newAppFileLatest);
        //断言包名qq.com，版本序号1.1，机构号000，机构类型2
        Assert.assertEquals("qq.com", newAppFileLatest.getAppPackageName());
        Assert.assertEquals("1.1", newAppFileLatest.getVersionCode());
        Assert.assertEquals("000", newAppFileLatest.getOrgId());
        Assert.assertEquals("2", newAppFileLatest.getOrgType());
    }

    /**
     * 根据机构号和机构类型查询应用列表
     */
    @Test
    @Rollback
    public void testFindByOrgIdAndOrgType() {
        List<AppFileLatest> appFileLatests = appFileLatestDao.findByOrgIdAndOrgType("000", "2");
        //断言长度是1
        Assert.assertEquals(1, appFileLatests.size());
        //断言包名是qq.com
        Assert.assertEquals("qq.com", appFileLatests.get(0).getAppPackageName());
    }
}
