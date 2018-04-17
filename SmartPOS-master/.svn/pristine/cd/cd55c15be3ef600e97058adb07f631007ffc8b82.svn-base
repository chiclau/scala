package com.yada.spos.db.test;

import com.yada.spos.db.dao.PospOrgZmkDao;
import com.yada.spos.db.model.PospOrgZmk;
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
 * 机构密钥dao测试
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SpringConfig.class)
@Transactional
@ActiveProfiles("test")
public class PospOrgZmkDaoTest {

    @Autowired
    private PospOrgZmkDao pospOrgZmkDao;

    /**
     * 测试根据机构id查询
     */
    @Test
    @Rollback
    public void testFindByOrgId() {
        PospOrgZmk pospOrgZmk = new PospOrgZmk();
        pospOrgZmk.setOrgId("000");
        pospOrgZmk.setZmkLmk("1111111111111111111");
        pospOrgZmkDao.saveAndFlush(pospOrgZmk);
        PospOrgZmk newPospOrgZmk = pospOrgZmkDao.findByOrgId("000");
        //断言newPospOrgZmk非空
        Assert.assertNotNull(newPospOrgZmk);
        //断言机构号是000
        Assert.assertEquals("000", newPospOrgZmk.getOrgId());
    }
}
