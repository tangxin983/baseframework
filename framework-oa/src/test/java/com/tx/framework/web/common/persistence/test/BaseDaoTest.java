package com.tx.framework.web.common.persistence.test;

import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.google.common.collect.Maps;
import com.tx.framework.web.common.persistence.entity.Area;
import com.tx.framework.web.common.persistence.entity.Page;
import com.tx.framework.web.dao.area.AreaDao;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/applicationContext*.xml" })
public class BaseDaoTest extends AbstractJUnit4SpringContextTests {
	
	@Autowired
	private AreaDao areaDao;

	private static Logger logger = LoggerFactory.getLogger(BaseDaoTest.class);

	@Test
	public void select() {
		System.out.println("=====select=====");
		List<Area> select = areaDao.select(Area.class);
		System.out.println("select count=" + select.size());
		
		System.out.println("=====select1=====");
		List<Area> select1 = areaDao.select(Area.class);
		System.out.println("select1 count=" + select1.size());
		
//		System.out.println("=====selectByPage=====");
//		Page<Area> p = new Page<Area>();
//		p.setCurrentPage(1);
//		p.setSize(5);
//		List<Area> page = areaDao.selectByPage(Area.class, p);
//		System.out.println("selectByPage count=" + page.size());
//		
//		System.out.println("=====selectByPageAndCondition=====");
//		Map<String, Object> map1 = Maps.newHashMap();
//		map1.put("areaName", "包厢333");
//		Page<Area> p1 = new Page<Area>();
//		p1.setCurrentPage(1);
//		p1.setSize(5);
//		List<Area> page1 = areaDao.selectByPageAndCondition(Area.class, p1, map1);
//		System.out.println("selectByPageAndCondition count=" + page1.size());
//		
//		
//		System.out.println("=====count=====");
//		long count = areaDao.count(Area.class);
//		System.out.println("count=" + count);
//		
//		System.out.println("=====countByCondition=====");
//		Map<String, Object> map = Maps.newHashMap();
//		map.put("areaName", "包厢333");
//		long countByCondition = areaDao.countByCondition(Area.class, map);
//		System.out.println("countByCondition=" + countByCondition);
	}

}
