//package com.yada.spos.db.test;
//
//import com.yada.spos.db.dao.AppFileLatestDao;
//import com.yada.spos.db.dao.AppGroupAppsDao;
//import com.yada.spos.db.dao.AppGroupDao;
//import com.yada.spos.db.dao.AppsAssociateAppGroupDao;
//import com.yada.spos.db.model.AppFileLatest;
//import com.yada.spos.db.model.AppGroup;
//import com.yada.spos.db.model.AppGroupApps;
//import com.yada.spos.db.test.config.SpringConfig;
//import org.junit.Assert;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//import org.springframework.transaction.annotation.Transactional;

/**
 * Created by pangChangSong on 2016/10/19.
 * 应用分组关联应用dao测试
 * （h2数据库with关键字有问题，为避免影响打包，注掉代码）
 */
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(classes = SpringConfig.class)
//@Transactional
//@ActiveProfiles("sit")
//public class AppsAssociateAppGroupDaoTest {
//
//    @Autowired
//    private AppsAssociateAppGroupDao dao;
//
//    @Test
//    public void testFindAll() {
//        dao.findAll("000011", "2", "225" + "", "", "20161019", new PageRequest(0, 2));
//    }
//}
