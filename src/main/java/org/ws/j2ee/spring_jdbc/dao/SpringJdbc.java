package org.ws.j2ee.spring_jdbc.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.ws.j2ee.spring_jdbc.domain.User;

import com.mysql.jdbc.Statement;

@Repository
@Transactional
public class SpringJdbc 
{
	@Autowired
    private JdbcTemplate jt;
	
	@Autowired
	private NamedParameterJdbcTemplate njt;
	
	@Value("${update_user_sql}")
	private String update_user_sql;
	
	@Value("${update_user_sql2}")
	private String update_user_sql2;
	
	@Value("${select_user_sql}")
	private String select_user_sql;
	
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
	
	public int updateUser4(final Object[] args) throws Exception
	{
		return jt.update(update_user_sql,new PreparedStatementSetter(){
			public void setValues(PreparedStatement ps) throws SQLException
			{
				ps.setString(1, (String)args[0]);
				ps.setString(2, (String)args[1]);
				ps.setInt(3, (int)args[2]);
			}
		});
	}
	
	public int updateUser5(final Object[] args) throws Exception
	{
		final KeyHolder holder = new GeneratedKeyHolder();
		final MapSqlParameterSource source = new MapSqlParameterSource();
		source.addValue("p1",(String)args[0]);
		source.addValue("p2",(String)args[1]);
		source.addValue("p3",(String)args[2]);
		njt.update(update_user_sql2, source, holder, new String[]{"id"});
		return holder.getKey().intValue();
	}
	
	public List<User> callbackQuery() throws Exception
	{
		final List<User> userList = new ArrayList<>();
		jt.query(select_user_sql, new RowCallbackHandler(){
			public void processRow(ResultSet set) throws SQLException
			{
				User user = new User();
				user.setId(set.getInt(1));
				user.setUsername(set.getString(2));
				user.setPassword(set.getString(3));
				user.setEmail(set.getString(4));
				user.setInfo(set.getString(5));
				user.setHeadImg(set.getBytes(6));
				user.setCreateTime(set.getDate(7));
				userList.add(user);
			}
		});
		return userList;
	}
	
	public User callbackQuery2(Integer id) throws Exception
	{
		final User user = new User();
		jt.query(select_user_sql,new Object[]{id},new RowCallbackHandler(){
			public void processRow(ResultSet set) throws SQLException
			{
				user.setId(set.getInt(1));
				user.setUsername(set.getString(2));
				user.setPassword(set.getString(3));
				user.setEmail(set.getString(4));
				user.setInfo(set.getString(5));
				user.setHeadImg(set.getBytes(6));
				user.setCreateTime(set.getDate(7));
			}
		});
		return user;
	}
}
