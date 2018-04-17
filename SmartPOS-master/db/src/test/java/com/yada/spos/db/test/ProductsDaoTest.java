package com.yada.spos.db.test;

import com.yada.spos.db.dao.ProductsDao;
import com.yada.spos.db.model.Products;
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
 * 产品dao测试
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SpringConfig.class)
@Transactional
@ActiveProfiles("test")
public class ProductsDaoTest {

    @Autowired
    private ProductsDao productsDao;

    @Before
    public void init() {
        Products products = new Products();
        products.setFirmCode("AAAA");
        products.setProdCode("111111");
        productsDao.saveAndFlush(products);

        Products products1 = new Products();
        products1.setFirmCode("AAAA");
        products1.setProdCode("222222");
        productsDao.saveAndFlush(products1);
    }

    /**
     * 测试根据厂商编号查询所有的产品
     */
    @Test
    @Rollback
    public void testFindByFirmCode() {
        List<Products> list = productsDao.findByFirmCode("AAAA");
        //断言查询出来两条数据
        Assert.assertEquals(2, list.size());
        for (Products p : list) {
            //断言厂商编号是AAAA
            Assert.assertEquals("AAAA", p.getFirmCode());
        }
    }

    /**
     * 测试根据厂商编号和产品型号查询产品
     */
    @Test
    @Rollback
    public void testFindByFirmCodeAndProdCode() {
        Products products = productsDao.findByFirmCodeAndProdCode("AAAA", "222222");
        //断言products存在
        Assert.assertNotNull(products);
        //断言厂商编号和产品类型分别是AAAA 2222
        Assert.assertEquals("AAAA", products.getFirmCode());
        Assert.assertEquals("222222", products.getProdCode());
    }
}

