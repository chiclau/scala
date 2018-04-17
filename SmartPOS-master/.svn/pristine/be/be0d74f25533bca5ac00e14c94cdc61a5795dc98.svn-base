package com.yada.spos.db.test;

import com.yada.spos.db.dao.OnlineParamDao;
import com.yada.spos.db.model.OnlineParam;
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
 * 联机参数下载dao测试
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SpringConfig.class)
@Transactional
@ActiveProfiles("test")
public class OnlineParamDaoTest {

    @Autowired
    private OnlineParamDao onlineParamDao;

    @Before
    public void init() {
        OnlineParam onlineParam = new OnlineParam();
        onlineParam.setOrgId("000");
        onlineParam.setOrgType("2");
        onlineParam.setParamName("qq");
        onlineParam.setOnlineParamId(1L);
        onlineParamDao.saveAndFlush(onlineParam);
    }

    /**
     * 根据机构号和机构类型查询联机参数下载实体列表
     */
    @Test
    @Rollback
    public void testFindByOrgIdAndOrgType() {
        List<OnlineParam> newOnlineParams = onlineParamDao.findByOrgIdAndOrgType("000", "2");
        //断言查询出来一条数据
        Assert.assertEquals(1, newOnlineParams.size());
        //断言机构号和机构类型是000， 2
        Assert.assertEquals("000", newOnlineParams.get(0).getOrgId());
        Assert.assertEquals("2", newOnlineParams.get(0).getOrgType());
    }

    /**
     * 测试根据机构id和机构类型参数名称获取联机参数列表
     */
    @Test
    @Rollback
    public void testFindByOrgIdAndOrgTypeAndParamName() {
        OnlineParam newOnlineParam = onlineParamDao.findByOrgIdAndOrgTypeAndParamName("000", "2", "qq");
        //断言newOnlineParam非空
        //断言机构号和机构类型和包名分别是000， 2， qq
        Assert.assertEquals("000", newOnlineParam.getOrgId());
        Assert.assertEquals("2", newOnlineParam.getOrgType());
        Assert.assertEquals("qq", newOnlineParam.getParamName());
    }

}
