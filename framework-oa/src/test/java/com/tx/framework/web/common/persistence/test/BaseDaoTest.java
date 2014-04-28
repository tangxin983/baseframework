package com.tx.framework.web.common.persistence.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.tx.framework.web.common.persistence.entity.Area;
import com.tx.framework.web.dao.area.AreaDao;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/applicationContext*.xml" })
public class BaseDaoTest extends AbstractJUnit4SpringContextTests {

	@Autowired
	private AreaDao areaDao;


	private static Logger logger = LoggerFactory.getLogger(BaseDaoTest.class);

	@Test
	public void select() {

//		TestEntity entity = new TestEntity();
//		entity.setName("tangx");
//		testDao.insertByAutoIncId(entity);
//		
//		System.out.println(entity.getId());

		Area area = new Area();
		area.setAreaName("wwww");
		area.setCode("11");
		areaDao.insert(area);
		
		System.out.println(area.getId());

	}

}
