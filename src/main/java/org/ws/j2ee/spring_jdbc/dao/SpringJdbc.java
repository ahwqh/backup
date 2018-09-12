package org.ws.j2ee.spring_jdbc.dao;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.support.AbstractLobCreatingPreparedStatementCallback;
import org.springframework.jdbc.core.support.AbstractLobStreamingResultSetExtractor;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.jdbc.support.lob.DefaultLobHandler;
import org.springframework.jdbc.support.lob.LobCreator;
import org.springframework.jdbc.support.lob.LobHandler;
import org.springframework.jdbc.support.nativejdbc.NativeJdbcExtractor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.ws.j2ee.spring_jdbc.domain.User;






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
	
	@Value("${select_user_sql2}")
	private String select_user_sql2;
	
	@Value("${select_user_sql3}")
	private String select_user_sql3;
	
	@Value("${select_single_int}")
	private String select_single_int;
	
	@Value("${callablestatement_sql}")
	private String callablestatement_sql;
	
	@Value("${updateblob_sql}")
	private String updateblob_sql;
	
	@Value("${queryblob_sql}")
	private String queryblob_sql;
	
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
	
	public List<User> callbackWithRowMapper()
	{
		return jt.query(select_user_sql2,
				new RowMapper<User>(){
					public User mapRow(ResultSet rs,int rowNum) 
							throws SQLException
					{
						User user = new User();
						user.setId(rs.getInt(1));
						user.setUsername(rs.getString(2));
						user.setPassword(rs.getString(3));
						user.setEmail(rs.getString(4));
						user.setInfo(rs.getString(5));
						user.setHeadImg(rs.getBytes(6));
						user.setCreateTime(rs.getDate(7));
						return user;
					}
				});
	}
	
	public int returnSingleInt()
	{
		return jt.queryForObject(select_single_int,Integer.class);
	}
	
	public List<Integer> returnList()
	{
		return jt.queryForList(select_single_int,Integer.class);
	}
	
	public List<String> returnList2()
	{
		return jt.queryForList(select_user_sql3,String.class);
	}
	
	public Object callableStatement(final int eno)
	{
		return jt.execute(callablestatement_sql,
				new CallableStatementCallback<Object>(){
			public Object doInCallableStatement(
					CallableStatement cs) throws SQLException
			{
				cs.setInt(1,eno);
				return null;
			}
		});
	}
	
	public void updateBlob(final Object[] args) throws Exception
	{
		LobHandler handler = new DefaultLobHandler();
		jt.execute(updateblob_sql, new AbstractLobCreatingPreparedStatementCallback(handler){
			public void setValues(PreparedStatement ps,LobCreator creator) throws SQLException
			{
				int len=0;
				InputStream is=null;
				try
				{
					File file = new File((String)args[1]);
					len = (int)file.length();
					is = new FileInputStream(file);
				}
				catch(IOException e)
				{
					e.printStackTrace();
				}
				ps.setInt(2,(int)args[0]);
				creator.setBlobAsBinaryStream(ps, 1, is,len);
			}
		});
	}
	
	public void queryBlob(final Object[] args) throws Exception
	{
		final LobHandler handler = new DefaultLobHandler();
		jt.query(queryblob_sql, args, new AbstractLobStreamingResultSetExtractor<byte[]>(){
			public void streamData(ResultSet rs) throws SQLException,IOException
			{
				InputStream is = handler.getBlobAsBinaryStream(rs, 1);
				OutputStream os = new FileOutputStream("d:/test/blob.jpg");
				IOUtils.copy(is, os);
				is.close();
				os.close();
			}
		});
	}
		
}
