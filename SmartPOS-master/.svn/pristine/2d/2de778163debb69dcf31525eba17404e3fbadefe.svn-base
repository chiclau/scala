package com.yada.spos.db.test;

import com.yada.spos.db.dao.TermWorkKeyDao;
import com.yada.spos.db.model.TermWorkKey;
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
 * 终端密钥dao测试
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SpringConfig.class)
@Transactional
@ActiveProfiles("test")
public class TermWorkKeyDaoTest {

    @Autowired
    private TermWorkKeyDao termWorkKeyDao;

    /**
     * 测试 根据商户号和终端号查询终端密钥
     */
    @Test
    @Rollback
    public void testFindByMerChantIdAndTerminalId() {
        TermWorkKey termWorkKey = new TermWorkKey();
        termWorkKey.setMerChantId("11111111111111");
        termWorkKey.setTerminalId("11111111");
        termWorkKeyDao.saveAndFlush(termWorkKey);
        TermWorkKey newTermWorkKey = termWorkKeyDao.findByMerChantIdAndTerminalId("11111111111111", "11111111");
        // 断言newTermWorkKey非空
        Assert.assertNotNull(newTermWorkKey);
        // 断言商户号和终端号分别是11111111111111   11111111
        Assert.assertEquals("11111111111111", newTermWorkKey.getMerChantId());
        Assert.assertEquals("11111111", newTermWorkKey.getTerminalId());
    }
}
