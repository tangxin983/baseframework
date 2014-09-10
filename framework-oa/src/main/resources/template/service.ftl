package ${packageName}.${moduleName}.service${subModuleName};

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.tx.framework.web.common.service.BaseService;
import ${packageName}.${moduleName}.entity${subModuleName}.${ClassName};
import ${packageName}.${moduleName}.dao${subModuleName}.${ClassName}Dao;

/**
 * ${functionName}Service
 * @author ${classAuthor}
 * @since ${classVersion}
 */
@Service
@Transactional
public class ${ClassName}Service extends BaseService<${ClassName}> {

	private ${ClassName}Dao ${className}Dao;

	@Autowired
	public void set${ClassName}Dao(${ClassName}Dao ${className}Dao) {
		super.setDao(${className}Dao);
		this.${className}Dao = ${className}Dao;
	}
	
}
