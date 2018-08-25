package org.ws.j2ee.spring_jdbc.dao;

import java.io.IOException;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class SpringJdbc 
{
	@Autowired
    private JdbcTemplate jt;
	
	@Value("${update_user_sql}")
	private String update_user_sql;
	
	public int updateUser(Object[] args)
	{
		return jt.update(update_user_sql, args);
	}
	
	public void updateUser2(Object[] args)
	{
		jt.update(update_user_sql, args);
		throw new RuntimeException();
	}
	
	@Transactional(noRollbackFor={java.io.IOException.class})
	public void updateUser3(Object[] args) throws Exception
	{
		jt.update(update_user_sql, args);
		throw new IOException("自定义异常单");
	}
}
