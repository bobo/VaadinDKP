/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.unknown.entity;

import java.sql.SQLException;

/**
 *
 * @author bobo
 */
class SQLRuntimeException extends RuntimeException {
	private final SQLException ex;

	public SQLRuntimeException(SQLException ex) {
		this.ex = ex;

	}

	@Override
	public void printStackTrace() {
		ex.printStackTrace();
	}




}
