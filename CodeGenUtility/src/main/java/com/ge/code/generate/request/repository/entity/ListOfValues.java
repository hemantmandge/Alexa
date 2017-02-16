package com.ge.code.generate.request.repository.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "LOV")
public class ListOfValues {
	@Id
	@Column(name = "ID", nullable = false)
	private Integer id;
	@Column(name = "TYPE")
	private String type;
	@Column(name = "NAME")
	private String name;
	@Column(name = "VALUE")
	private String value;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
}
