package org.ws.j2ee.spring_jdbc;

import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.ws.j2ee.spring_jdbc.dao.SpringJdbc;

import junit.framework.Assert;

public class SpringJdbcTest extends DataSourceTest
{
	@Test
    public void test()
    {
    	ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
    	SpringJdbc sj = ctx.getBean(SpringJdbc.class);
    	sj.updateUser2(new Object[]{"555555","beautiful@xx.com",222});
    }
	
	@Test
    public void test2() throws Exception
    {
    	ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
    	SpringJdbc sj = ctx.getBean(SpringJdbc.class);
    	sj.updateUser3(new Object[]{"555555","beautiful@xx.com",222});
    }
}
