package com.tx.framework.web.dao.area;

import com.tx.framework.web.common.persistence.annotation.MyBatisDao;
import com.tx.framework.web.common.persistence.dao.BaseDaoNew;
import com.tx.framework.web.common.persistence.entity.Area;


@MyBatisDao
public interface AreaDao extends BaseDaoNew<Area, String> {

	Area findByName(String areaName);
}
