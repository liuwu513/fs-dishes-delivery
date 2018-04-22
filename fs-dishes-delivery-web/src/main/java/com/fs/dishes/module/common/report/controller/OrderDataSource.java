/*
 * JasperReports - Free Java Reporting Library.
 * Copyright (C) 2001 - 2014 TIBCO Software Inc. All rights reserved.
 * http://www.jaspersoft.com
 *
 * Unless you have purchased a commercial license agreement from Jaspersoft,
 * the following license terms apply:
 *
 * This program is part of JasperReports.
 *
 * JasperReports is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * JasperReports is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with JasperReports. If not, see <http://www.gnu.org/licenses/>.
 */
package com.fs.dishes.module.common.report.controller;

import com.fs.dishes.module.order.entity.PlsOrderFood;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;

import java.util.List;
import java.util.Map;

/**
 * 刘武
 */
public class OrderDataSource implements JRDataSource {

	private Map<String,Object> modelMap;

	private List<PlsOrderFood> data;

	private int index = -1;

	public OrderDataSource() {
	}

	/**
	 *
	 */
	public OrderDataSource(Map<String,Object> modelMap) {
		this.modelMap = modelMap;
	}

	/**
	 * 获取下一个数据
	 */
	public boolean next() throws JRException {
		index++;
		return (index < data.size());
	}

	/**
	 *
	 */
	public Object getFieldValue(JRField field) throws JRException {
		Object value = null;

		String fieldName = field.getName();
		return value;
	}

}
