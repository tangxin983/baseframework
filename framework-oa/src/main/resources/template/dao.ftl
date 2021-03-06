package ${packageName}.${moduleName}.dao${subModuleName};

import com.tx.framework.web.common.persistence.annotation.MyBatisDao;
import com.tx.framework.web.common.persistence.dao.BaseDao;
import ${packageName}.${moduleName}.entity${subModuleName}.${ClassName};

/**
 * ${functionName}Dao
 * @author ${classAuthor}
 * @since ${classVersion}
 */
@MyBatisDao
public interface ${ClassName}Dao extends BaseDao<${ClassName}> {
	
}
