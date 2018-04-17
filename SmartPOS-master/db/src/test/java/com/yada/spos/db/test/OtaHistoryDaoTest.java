package com.yada.spos.db.test;

import com.yada.spos.db.dao.OtaHistoryDao;
import com.yada.spos.db.model.OtaHistory;
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
 * ota历史信息dao测试
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SpringConfig.class)
@Transactional
@ActiveProfiles("test")
public class OtaHistoryDaoTest {

    @Autowired
    private OtaHistoryDao otaHistoryDao;

    /**
     * 测试根据厂商编号、产品编号、版本名称、机构号和机构类型查询ota历史信息表数据
     */
    @Test
    public void testFindByFirmCodeAndProdCodeAndVersionNameAndOrgIdAndOrgType() {
        OtaHistory otaHistory = new OtaHistory();
        otaHistory.setFirmCode("01");
        otaHistory.setProdCode("Ax150");
        otaHistory.setVersionName("1.1...1.5");
        otaHistory.setOrgId("000");
        otaHistory.setOrgType("2");
        otaHistoryDao.saveAndFlush(otaHistory);

        OtaHistory result = otaHistoryDao.findByFirmCodeAndProdCodeAndVersionNameAndOrgIdAndOrgType("01", "Ax150", "1.1...1.5", "000", "2");
        //断言result不为null
        Assert.assertNotNull(result);
        //断言厂商编号是01， 产品编号是Ax150， 版本名称是1.1...1.5, 机构号是000， 机构类型是2
        Assert.assertEquals("01", result.getFirmCode());
        Assert.assertEquals("Ax150", result.getProdCode());
        Assert.assertEquals("1.1...1.5", result.getVersionName());
        Assert.assertEquals("000", result.getOrgId());
        Assert.assertEquals("2", result.getOrgType());
    }
}
