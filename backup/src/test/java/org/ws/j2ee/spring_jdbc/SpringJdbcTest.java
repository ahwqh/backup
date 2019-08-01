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
    
//    @Test
    public void test6() throws Exception
    {
    	ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
    	SpringJdbc sj = ctx.getBean(SpringJdbc.class);
    	User user = sj.callbackQuery2(1);
    	Assert.assertTrue(user.getUsername().equals("admin"));
    }
    
//    @Test
    public void test7() throws Exception
    {
    	ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
    	SpringJdbc sj = ctx.getBean(SpringJdbc.class);
    	List<User> userList = 
    			sj.callbackWithRowMapper();
    	Assert.assertTrue(userList.size()==4);
    }
    
//    @Test
    public void test8() throws Exception
    {
    	ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
    	SpringJdbc sj = ctx.getBean(SpringJdbc.class);
    	int result = sj.returnSingleInt();
    	Assert.assertTrue(result == 4);
    }
    
//    @Test
    public void test9() throws Exception
    {
    	ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
    	SpringJdbc sj = ctx.getBean(SpringJdbc.class);
    	List<Integer> list = sj.returnList();
    	Assert.assertTrue(list.get(0)==4);
    }
    
//    @Test
    public void test10() throws Exception
    {
    	ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
    	SpringJdbc sj = ctx.getBean(SpringJdbc.class);
    	List<String> userList = sj.returnList2();
    	Assert.assertTrue(userList.size()==4);
    }
    
//    @Test
    public void test11() throws Exception
    {
    	ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
    	SpringJdbc sj = ctx.getBean(SpringJdbc.class);
    	sj.callableStatement(100);
    }
    
//    @Test
    public void test12() throws Exception
    {
    	ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
    	SpringJdbc sj = ctx.getBean(SpringJdbc.class);
    	sj.updateBlob(new Object[]{1,"e:/pic/tp1.jpg"});   	
    }
    
//    @Test
    public void test13() throws Exception
    {
    	ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
    	SpringJdbc sj = ctx.getBean(SpringJdbc.class);
    	sj.queryBlob(new Object[]{1});
    }
    
//    @Test
    public void test14() throws Exception
    {
    	ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
    	SpringJdbc sj = ctx.getBean(SpringJdbc.class);
    	int result = sj.sequenceIncrement(new Object[]{"Bob","123456","tom@xxx.com"});
    	Assert.assertTrue(result==1);
    }
    
    @Test
    public void test15() throws Exception
    {
    	ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
    	SpringJdbc sj = ctx.getBean(SpringJdbc.class);
    	String result = sj.sequenceIncrement2(new Object[]{"Ben","123456","ben@xxx.com"});
    	System.out.println(result);
    }
}
