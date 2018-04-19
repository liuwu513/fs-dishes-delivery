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

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;

/**
 * @author Teodor Danciu (teodord@users.sourceforge.net)
 */
public class GradeDataSource implements JRDataSource {

	/**
	 *
	 */
	private Object[][] data = { 
			{ "高一(1)班", new Integer(55), "化学", new Integer(22), new Integer(33), new Integer(20), new Integer(10), new Integer(25) },
			{ "高一(2)班", new Integer(55), "化学", new Integer(22), new Integer(33), new Integer(20), new Integer(10), new Integer(25) },
			{ "高一(3)班", new Integer(55), "生物", new Integer(22), new Integer(33), new Integer(20), new Integer(10), new Integer(25) },
			{ "高一(4)班", new Integer(55), "生物", new Integer(22), new Integer(33), new Integer(20), new Integer(10), new Integer(25) },
			{ "高一(5)班", new Integer(55), "物理", new Integer(22), new Integer(33), new Integer(20), new Integer(10), new Integer(25) },
			{ "高一(6)班", new Integer(55), "物理", new Integer(22), new Integer(33), new Integer(20), new Integer(10), new Integer(25) },
			{ "高一(7)班", new Integer(55), "体育", new Integer(22), new Integer(33), new Integer(20), new Integer(10), new Integer(25) },
			{ "高一(8)班", new Integer(55), "体育", new Integer(22), new Integer(33), new Integer(20), new Integer(10), new Integer(25) },
			{ "高一(9)班", new Integer(55), "美术", new Integer(22), new Integer(33), new Integer(20), new Integer(10), new Integer(25) },
			{ "高一(10)班", new Integer(55), "美术", new Integer(22), new Integer(33), new Integer(20), new Integer(10), new Integer(25) },
			{ "高二(1)班", new Integer(55), "化学", new Integer(22), new Integer(33), new Integer(20), new Integer(10), new Integer(25) },
			{ "高二(2)班", new Integer(55), "化学", new Integer(22), new Integer(33), new Integer(20), new Integer(10), new Integer(25) },
			{ "高二(3)班", new Integer(55), "生物", new Integer(22), new Integer(33), new Integer(20), new Integer(10), new Integer(25) },
			{ "高二(4)班", new Integer(55), "生物", new Integer(22), new Integer(33), new Integer(20), new Integer(10), new Integer(25) },
			{ "高二(5)班", new Integer(55), "物理", new Integer(22), new Integer(33), new Integer(20), new Integer(10), new Integer(25) },
			{ "高二(6)班", new Integer(55), "物理", new Integer(22), new Integer(33), new Integer(20), new Integer(10), new Integer(25) },
			{ "高二(7)班", new Integer(55), "体育", new Integer(22), new Integer(33), new Integer(20), new Integer(10), new Integer(25) },
			{ "高一(8)班", new Integer(55), "体育", new Integer(22), new Integer(33), new Integer(20), new Integer(10), new Integer(25) },
			{ "高二(9)班", new Integer(55), "美术", new Integer(22), new Integer(33), new Integer(20), new Integer(10), new Integer(25) },
			{ "高二(10)班", new Integer(55), "美术", new Integer(22), new Integer(33), new Integer(20), new Integer(10), new Integer(25) },
			{ "高三(1)班", new Integer(55), "化学", new Integer(22), new Integer(33), new Integer(20), new Integer(10), new Integer(25) },
			{ "高三(2)班", new Integer(55), "化学", new Integer(22), new Integer(33), new Integer(20), new Integer(10), new Integer(25) },
			{ "高三(3)班", new Integer(55), "生物", new Integer(22), new Integer(33), new Integer(20), new Integer(10), new Integer(25) },
			{ "高三(4)班", new Integer(55), "生物", new Integer(22), new Integer(33), new Integer(20), new Integer(10), new Integer(25) },
			{ "高三(5)班", new Integer(55), "物理", new Integer(22), new Integer(33), new Integer(20), new Integer(10), new Integer(25) },
			{ "高三(6)班", new Integer(55), "物理", new Integer(22), new Integer(33), new Integer(20), new Integer(10), new Integer(25) },
			{ "高三(7)班", new Integer(55), "体育", new Integer(22), new Integer(33), new Integer(20), new Integer(10), new Integer(25) },
			{ "高三(8)班", new Integer(55), "体育", new Integer(22), new Integer(33), new Integer(20), new Integer(10), new Integer(25) },
			{ "高三(9)班", new Integer(55), "美术", new Integer(22), new Integer(33), new Integer(20), new Integer(10), new Integer(25) },
			{ "高一(1)班", new Integer(55), "化学", new Integer(22), new Integer(33), new Integer(20), new Integer(10), new Integer(25) },
			{ "高一(2)班", new Integer(55), "化学", new Integer(22), new Integer(33), new Integer(20), new Integer(10), new Integer(25) },
			{ "高一(3)班", new Integer(55), "生物", new Integer(22), new Integer(33), new Integer(20), new Integer(10), new Integer(25) },
			{ "高一(4)班", new Integer(55), "生物", new Integer(22), new Integer(33), new Integer(20), new Integer(10), new Integer(25) },
			{ "高一(5)班", new Integer(55), "物理", new Integer(22), new Integer(33), new Integer(20), new Integer(10), new Integer(25) },
			{ "高一(6)班", new Integer(55), "物理", new Integer(22), new Integer(33), new Integer(20), new Integer(10), new Integer(25) },
			{ "高一(7)班", new Integer(55), "体育", new Integer(22), new Integer(33), new Integer(20), new Integer(10), new Integer(25) },
			{ "高一(8)班", new Integer(55), "体育", new Integer(22), new Integer(33), new Integer(20), new Integer(10), new Integer(25) },
			{ "高一(9)班", new Integer(55), "美术", new Integer(22), new Integer(33), new Integer(20), new Integer(10), new Integer(25) },
			{ "高一(10)班", new Integer(55), "美术", new Integer(22), new Integer(33), new Integer(20), new Integer(10), new Integer(25) },
			{ "高三(10)班", new Integer(55), "美术", new Integer(22), new Integer(33), new Integer(20), new Integer(10), new Integer(25) } 
			};

	private int index = -1;

	/**
	 *
	 */
	public GradeDataSource() {
		
	}

	/**
	 *
	 */
	public boolean next() throws JRException {
		index++;

		return (index < data.length);
	}

	/**
	 *
	 */
	public Object getFieldValue(JRField field) throws JRException {
		Object value = null;

		String fieldName = field.getName();

		if ("grade".equals(fieldName)) {
			value = data[index][0];
		} else if ("grade_stu_num".equals(fieldName)) {
			value = data[index][1];
		} else if ("class".equals(fieldName)) {
			value = data[index][2];
		} else if ("boys".equals(fieldName)) {
			value = data[index][3];
		} else if ("girls".equals(fieldName)) {
			value = data[index][4];
		} else if ("basketballNum".equals(fieldName)) {
			value = data[index][5];
		} else if ("footballNum".equals(fieldName)) {
			value = data[index][6];
		} else if ("badmintonNum".equals(fieldName)) {
			value = data[index][7];
		}
		return value;
	}

}
