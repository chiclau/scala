package com.yada.spos.db.test;

import com.yada.spos.db.dao.OrgDao;
import com.yada.spos.db.model.Org;
import com.yada.spos.db.test.config.SpringConfig;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by pangChangSong on 2016/9/12.
 * 总对总机构dao
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SpringConfig.class)
@Transactional
@ActiveProfiles("test")
public class OrgDaoTest {

    @Autowired
    private OrgDao orgDao;

    @Before
    public void init() {
        Org org1 = new Org();
        org1.setOrgId("000");
        org1.setOrgLev(0);
        org1.setName("总行");
        org1.setPOrgId(null);
        orgDao.saveAndFlush(org1);

        Org org2 = new Org();
        org2.setOrgId("00011");
        org2.setPOrgId("000");
        org2.setOrgLev(1);
        org2.setName("北京分行");
        orgDao.saveAndFlush(org2);

        Org org3 = new Org();
        org3.setOrgId("00012");
        org3.setPOrgId("000");
        org3.setOrgLev(1);
        org3.setName("上海分行");
        orgDao.saveAndFlush(org3);
    }

    /**
     * 查找下一级的所有机构
     */
    @Test
    public void testFindByPOrgId() {
        List<Org> list = orgDao.findByPOrgId("000");
        //断言list存在
        Assert.assertNotNull(list);
        //断言list有两条数据
        Assert.assertEquals(2, list.size());
        //断言查询出来的机构上级是000
        for (Org org : list) {
            Assert.assertEquals("000", org.getPOrgId());

        }
    }
}
