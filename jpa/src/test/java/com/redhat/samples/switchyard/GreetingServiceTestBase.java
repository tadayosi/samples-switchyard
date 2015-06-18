package com.redhat.samples.switchyard;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.naming.InitialContext;
import javax.naming.Name;

import org.h2.jdbcx.JdbcDataSource;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.switchyard.component.test.mixins.naming.NamingMixIn;

public abstract class GreetingServiceTestBase {

    private static final String DB_URL = "jdbc:h2:mem:sample";
    private static NamingMixIn namingMixIn;
    private static Connection connection;

    @BeforeClass
    public static void startDB() throws Exception {
        namingMixIn = new NamingMixIn();
        namingMixIn.initialize();

        connection = DriverManager.getConnection(DB_URL, "sa", "sa");
        JdbcDataSource ds = new JdbcDataSource();
        ds.setURL(DB_URL);
        ds.setUser("sa");
        ds.setPassword("sa");

        InitialContext context = new InitialContext();
        Name name = context.getNameParser("").parse("java:jboss/datasources/Sample");
        context.bind(name, ds);
    }

    @AfterClass
    public static void stopDB() throws SQLException {
        if (!connection.isClosed()) connection.close();
        namingMixIn.uninitialize();
    }

}
