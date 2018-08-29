package org.ws.j2ee.spring_jdbc;

import java.util.List;

import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.ws.j2ee.spring_jdbc.dao.SpringJdbc;
import org.ws.j2ee.spring_jdbc.domain.User;

import junit.framework.Assert;

public class SpringJdbcTest extends DataSourceTest
{
//	@Test
    public void test()
    {
    	ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
    	SpringJdbc sj = ctx.getBean(SpringJdbc.class);
    	sj.updateUser2(new Object[]{"555555","beautiful@xx.com",222});
    }
	
//	@Test
    public void test2() throws Exception
    {
    	ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
    	SpringJdbc sj = ctx.getBean(SpringJdbc.class);
    	sj.updateUser3(new Object[]{"555555","beautiful@xx.com",222});
    }
    
//    @Test
    public void test3() throws Exception
    {
    	ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
    	SpringJdbc sj = ctx.getBean(SpringJdbc.class);
    	int result = sj.updateUser4(new Object[]{"555555","beautiful@beauty.com",222});
    	Assert.assertTrue(result == 1);
    	
    }
    
//    @Test
    public void test4() throws Exception
    {
    	ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
    	SpringJdbc sj = ctx.getBean(SpringJdbc.class);
    	int result = sj.updateUser5(new Object[]{"tom","555555","tom@yahoo.com"});
    	Assert.assertTrue(result > 0);
    	
    }
    
//    @Test
    public void test5() throws Exception
    {
    	ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
    	SpringJdbc sj = ctx.getBean(SpringJdbc.class);
    	List<User> userList = sj.callbackQuery();
//    	Assert.assertTrue(user.getUsername().equals("admin"));
    	Assert.assertTrue(userList.size()>0);
    }
    
    @Test
    public void test6() throws Exception
    {
    	ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
    	SpringJdbc sj = ctx.getBean(SpringJdbc.class);
    	User user = sj.callbackQuery2(1);
    	Assert.assertTrue(user.getUsername().equals("admin"));
    }
}
