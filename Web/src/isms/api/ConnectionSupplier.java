package isms.api;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.tomcat.jdbc.pool.PoolProperties;

import isms.common.Supplier;

public class ConnectionSupplier implements Supplier<Connection> {

	private static final DataSource source = init();

	private static DataSource init() {
		PoolProperties p = new PoolProperties();
		p.setUrl("jdbc:mysql://localhost:3306/isms");
		p.setUsername("isms_user");
		p.setPassword("isms");

		return new org.apache.tomcat.jdbc.pool.DataSource(p);
	}

	public Connection get() {
		Connection conn = null;
		try {
			conn = source.getConnection();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return conn;
	}
}
