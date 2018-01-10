package com.plsoft.elearn.entity;

import java.io.Serializable;

public class Worksheet implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String rvalue;
	private String lvalue;
	private String totalvalue;
	public String getRvalue() {
		return rvalue;
	}
	public void setRvalue(String rvalue) {
		this.rvalue = rvalue;
	}
	public String getLvalue() {
		return lvalue;
	}
	public void setLvalue(String lvalue) {
		this.lvalue = lvalue;
	}
	public String getTotalvalue() {
		return totalvalue;
	}
	public void setTotalvalue(String totalvalue) {
		this.totalvalue = totalvalue;
	}
}
