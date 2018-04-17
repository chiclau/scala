package com.yada.spos.db.test;


import com.yada.spos.db.dao.AppFileHistoryDao;
import com.yada.spos.db.model.AppFileHistory;
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

import java.util.List;

/**
 * Created by pangChangSong on 2016/9/11.
 * 应用文件历史dao测试
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SpringConfig.class)
@Transactional
@ActiveProfiles("test")
public class AppFileHistoryDaoTest {

    @Autowired
    private AppFileHistoryDao appFileHistoryDao;

    /**
     * 测试根据机构类型，机构号，包名和版本序号查询应用文件历史
     */
    @Test
    @Rollback
    public void testFindByAppPackageNameAndVersionCodeAndOrgIdAndOrgType() {
        AppFileHistory appFileHistory = new AppFileHistory();
        appFileHistory.setAppFileId(1L);
        appFileHistory.setAppPackageName("qq.com");
        appFileHistory.setVersionCode("1.1");
        appFileHistory.setOrgId("000");
        appFileHistory.setOrgType("2");
        appFileHistoryDao.saveAndFlush(appFileHistory);
        AppFileHistory appFileHistory1 = appFileHistoryDao.findByAppPackageNameAndVersionCodeAndOrgIdAndOrgType("qq.com", "1.1", "000", "2");
        Assert.assertNotNull(appFileHistory1);
        //断言包名qq.com，版本序号1.1，机构号000，机构类型2
        Assert.assertEquals("qq.com", appFileHistory1.getAppPackageName());
        Assert.assertEquals("1.1", appFileHistory1.getVersionCode());
        Assert.assertEquals("000", appFileHistory1.getOrgId());
        Assert.assertEquals("2", appFileHistory1.getOrgType());

    }

    @Test
    @Rollback
    public void testFindByAppPackageNameAndOrgIdAndOrgType() {
        AppFileHistory appFileHistory = new AppFileHistory();
        appFileHistory.setAppFileId(1L);
        appFileHistory.setAppPackageName("qq.com");
        appFileHistory.setVersionCode("1.1");
        appFileHistory.setOrgId("000");
        appFileHistory.setOrgType("2");
        appFileHistoryDao.saveAndFlush(appFileHistory);
        List<AppFileHistory> appFileHistorys = appFileHistoryDao.findByAppPackageNameAndOrgIdAndOrgType("qq.com", "000", "2");
        //断言长度是1
        Assert.assertEquals(1, appFileHistorys.size());
        AppFileHistory appFileHistory1 = appFileHistorys.get(0);
        //断言包名qq.com，版本序号1.1，机构号000，机构类型2
        Assert.assertEquals("qq.com", appFileHistory1.getAppPackageName());
        Assert.assertEquals("1.1", appFileHistory1.getVersionCode());
        Assert.assertEquals("000", appFileHistory1.getOrgId());
        Assert.assertEquals("2", appFileHistory1.getOrgType());

    }

    @Test
    @Rollback
    public void testFindByAppPackageNameAndOrgTypeAndOrgIdNot() {
        AppFileHistory appFileHistory = new AppFileHistory();
        appFileHistory.setAppFileId(1L);
        appFileHistory.setAppPackageName("qq.com");
        appFileHistory.setVersionCode("1.1");
        appFileHistory.setOrgId("000");
        appFileHistory.setOrgType("2");
        appFileHistoryDao.saveAndFlush(appFileHistory);

        AppFileHistory appFileHistory1 = new AppFileHistory();
        appFileHistory1.setAppFileId(2L);
        appFileHistory1.setAppPackageName("qq.com");
        appFileHistory1.setVersionCode("1.1");
        appFileHistory1.setOrgId("000011");
        appFileHistory1.setOrgType("2");
        appFileHistoryDao.saveAndFlush(appFileHistory1);

        AppFileHistory appFileHistory2 = new AppFileHistory();
        appFileHistory2.setAppFileId(3L);
        appFileHistory2.setAppPackageName("qq.com");
        appFileHistory2.setVersionCode("1.1");
        appFileHistory2.setOrgId("000011");
        appFileHistory2.setOrgType("2");
        appFileHistoryDao.saveAndFlush(appFileHistory2);

        AppFileHistory appFileHistory3 = new AppFileHistory();
        appFileHistory3.setAppFileId(4L);
        appFileHistory3.setAppPackageName("qq.com");
        appFileHistory3.setVersionCode("1.1");
        appFileHistory3.setOrgId("000011");
        appFileHistory3.setOrgType("2");
        appFileHistoryDao.saveAndFlush(appFileHistory3);

        List<AppFileHistory> list = appFileHistoryDao.findByAppPackageNameAndOrgTypeAndOrgIdNot("qq.com", "2", "000");
        //断言查询出来3条
        Assert.assertEquals(3, list.size());
        for (AppFileHistory app : list) {
            //断言机构是000011， 机构类型是2， 包名qq.com
            Assert.assertEquals("000011", app.getOrgId());
            Assert.assertEquals("qq.com", app.getAppPackageName());
            Assert.assertEquals("2", app.getOrgType());
        }
    }

    @Test
    @Rollback
    public void testFindByAppPackageNameAndVersionCodeAndOrgTypeAndOrgIdNot() {
        AppFileHistory appFileHistory = new AppFileHistory();
        appFileHistory.setAppFileId(1L);
        appFileHistory.setAppPackageName("qq.com");
        appFileHistory.setVersionCode("1.1");
        appFileHistory.setOrgId("000");
        appFileHistory.setOrgType("2");
        appFileHistoryDao.saveAndFlush(appFileHistory);

        AppFileHistory appFileHistory1 = new AppFileHistory();
        appFileHistory1.setAppFileId(2L);
        appFileHistory1.setAppPackageName("qq.com");
        appFileHistory1.setVersionCode("1.2");
        appFileHistory1.setOrgId("000011");
        appFileHistory1.setOrgType("2");
        appFileHistoryDao.saveAndFlush(appFileHistory1);

        AppFileHistory appFileHistory2 = new AppFileHistory();
        appFileHistory2.setAppFileId(3L);
        appFileHistory2.setAppPackageName("qq.com");
        appFileHistory2.setVersionCode("1.2");
        appFileHistory2.setOrgId("000022");
        appFileHistory2.setOrgType("2");
        appFileHistoryDao.saveAndFlush(appFileHistory2);

        AppFileHistory appFileHistory3 = new AppFileHistory();
        appFileHistory3.setAppFileId(4L);
        appFileHistory3.setAppPackageName("qq.com");
        appFileHistory3.setVersionCode("1.4");
        appFileHistory3.setOrgId("000011");
        appFileHistory3.setOrgType("2");
        appFileHistoryDao.saveAndFlush(appFileHistory3);

        List<AppFileHistory> list = appFileHistoryDao.findByAppPackageNameAndVersionCodeAndOrgTypeAndOrgIdNot("qq.com", "1.2", "2", "000");
        //断言查询出来2条
        Assert.assertEquals(2, list.size());
        for (AppFileHistory app : list) {
            //断言机构类型是2， 包名qq.com, 断言版本是1.2，
            Assert.assertEquals("qq.com", app.getAppPackageName());
            Assert.assertEquals("1.2", app.getVersionCode());
            Assert.assertEquals("2", app.getOrgType());
        }
        //断言机构是000011和000022
        Assert.assertEquals("000011", list.get(0).getOrgId());
        Assert.assertEquals("000022", list.get(1).getOrgId());
    }
}
