package com.yada.spos.db.test;

import com.yada.spos.db.dao.OtaFileLatestDao;
import com.yada.spos.db.model.OtaFileLatest;
import com.yada.spos.db.test.config.SpringConfig;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by pangChangSong on 2016/9/12.
 * ota文件最新版本表dao测试
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SpringConfig.class)
@Transactional
@ActiveProfiles("test")
public class OtaFileLatestDaoTest {

    @Autowired
    private OtaFileLatestDao otaFileLatestDao;

    /**
     * 根据厂商编号，产品编号和机构，机构类型查询ota文件最新版本表
     */
    @Test
    public void testFindByFirmCodeAndProdCodeAndOrgIdAndOrgType() {
        OtaFileLatest otaFileLatest = new OtaFileLatest();
        otaFileLatest.setFirmCode("01");
        otaFileLatest.setProdCode("Ax150");
        otaFileLatest.setOrgId("000");
        otaFileLatest.setOrgType("2");
        otaFileLatestDao.saveAndFlush(otaFileLatest);
        OtaFileLatest entity = otaFileLatestDao.findByFirmCodeAndProdCodeAndOrgIdAndOrgType("01", "Ax150", "000", "2");
        //断言entity不为null
        Assert.assertNotNull(entity);
        //断言厂商编号01，产品编号Ax150， 机构号000， 机构类型2
        Assert.assertEquals("01", entity.getFirmCode());
        Assert.assertEquals("Ax150", entity.getProdCode());
        Assert.assertEquals("000", entity.getOrgId());
        Assert.assertEquals("2", entity.getOrgType());
    }
}
