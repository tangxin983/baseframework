package com.tx.framework.web.common.persistence.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "blog")
public class Blog {
	
	@Id
	private String id;
	
	@Column(name = "blog_author")
	private String author;

}
