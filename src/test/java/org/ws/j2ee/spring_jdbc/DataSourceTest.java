package org.ws.j2ee.spring_jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Savepoint;
import java.util.concurrent.TimeUnit;

import javax.sql.DataSource;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import junit.framework.Assert;

public class DataSourceTest 
{
	AbstractApplicationContext ctx;
	private Connection conn1;
	private Connection conn2;
	@Before
	public void init()
	{
		ctx = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
	}
	
//    @Test
    public void ds() throws Exception
    {
    	DataSource ds = ctx.getBean("ds2",DataSource.class);
    	Assert.assertNotNull(ds);
    	System.out.println(ds.getConnection().toString());
    	Connection conn = ds.getConnection();
    	conn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
    	conn.setAutoCommit(false);
    	Assert.assertTrue(conn.getMetaData().supportsTransactions());
    	Assert.assertTrue(conn.getMetaData().supportsTransactionIsolationLevel(
    			Connection.TRANSACTION_SERIALIZABLE));
    	Assert.assertFalse(conn.getAutoCommit());
    	Savepoint a = conn.setSavepoint();
    	PreparedStatement pstmt = conn.prepareStatement("delete from sys_user where id=?");
    	pstmt.setInt(1,222);
    	int result = pstmt.executeUpdate();
    	Assert.assertTrue(result >0);
    	conn.rollback(a);
    	ctx.close();
    }
    
    @Test
    public void tx() throws Exception
    {
    	PlatformTransactionManager ptm = ctx.getBean("tx",PlatformTransactionManager.class);
    	DefaultTransactionDefinition td = new DefaultTransactionDefinition();
    	TransactionStatus ts = ptm.getTransaction(td);
    	Assert.assertFalse(ts.hasSavepoint());
    	Assert.assertFalse(ts.isCompleted());
    	Assert.assertTrue(ts.isNewTransaction());
    	Object sp = ts.createSavepoint();
    	Assert.assertTrue(sp != null);
    	ts.rollbackToSavepoint(sp);
    	final DataSource ds = ctx.getBean("datasource",DataSource.class);
    	conn1 = DataSourceUtils.doGetConnection(ds);
    }
    
//    @Test
    public void tx2() throws Exception
    {
    	final DataSource ds = ctx.getBean("datasource",DataSource.class);
    	conn2 = DataSourceUtils.doGetConnection(ds);
    	TimeUnit.SECONDS.sleep(5);
    	Assert.assertTrue(conn1==conn2);
    }
}