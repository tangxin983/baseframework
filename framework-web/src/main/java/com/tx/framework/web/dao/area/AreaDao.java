package com.tx.framework.web.dao.area;

import com.tx.framework.web.common.base.BaseDao;
import com.tx.framework.web.common.base.MyBatisRepository;
import com.tx.framework.web.entity.Area;


@MyBatisRepository
public interface AreaDao extends BaseDao<Area> {

	Area findByName(String areaName);
}
