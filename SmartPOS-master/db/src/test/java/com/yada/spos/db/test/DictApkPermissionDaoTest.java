package com.yada.spos.db.test;

import com.yada.spos.db.dao.DictApkPermissionDao;
import com.yada.spos.db.model.DictApkPermission;
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
 * apk涉及到的权限字典dao测试
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SpringConfig.class)
@Transactional
@ActiveProfiles("test")
public class DictApkPermissionDaoTest {

    @Autowired
    private DictApkPermissionDao dictApkPermissionDao;

    @Test
    @Rollback
    public void testFindByApkPermissionCode() {
        DictApkPermission dictApkPermission = new DictApkPermission();
        dictApkPermission.setApkPermissionId(1L);
        dictApkPermission.setApkPermissionCode("asdfg");
        dictApkPermissionDao.saveAndFlush(dictApkPermission);
        DictApkPermission newDictApkPermission = dictApkPermissionDao.findByApkPermissionCode("asdfg");
        //断言newDictApkPermission存在
        Assert.assertNotNull(newDictApkPermission);
        //断言apk权限编码是asdfg
        Assert.assertEquals("asdfg", newDictApkPermission.getApkPermissionCode());
    }
}
