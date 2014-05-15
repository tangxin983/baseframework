package ${entityPackageName};

import java.util.Date;
import java.sql.*;
import javax.persistence.Column;
import javax.persistence.Table;

/**
 * ${functionName}实体
 * @author ${classAuthor}
 * @since ${classVersion}
 */
@SuppressWarnings("serial")
@Table(name = "${tableName}")
public class ${ClassName} extends BaseEntity {

	<#list entityFields as field>
	@Column(name = "${field.colName}")
	private ${field.type} ${field.name};
	
	</#list>
	
	<#list entityFields as field>
	public ${field.type} get${field.Name}() {
		return ${field.name};
	}

	public void set${field.Name}(${field.type} ${field.name}) {
		this.${field.name} = ${field.name};
	}
	
	</#list>
}


