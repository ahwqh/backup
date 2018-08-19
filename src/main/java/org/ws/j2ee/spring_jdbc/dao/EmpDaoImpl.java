package org.ws.j2ee.spring_jdbc.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

@Repository
public class EmpDaoImpl implements EmpDao
{
	@Autowired
    private JdbcTemplate jt;
	
	@Autowired
	private TransactionTemplate transTemp;
	
	public int addEmp(final String name,final double sal)
	{
		return transTemp.execute(new TransactionCallback<Integer>(){
			public Integer doInTransaction(TransactionStatus ts)
			{
				return jt.update("insert into EMPLOYEE(id,name,sal) values(3,'"+name+"',"+sal+")");
			}
		});
	}
}
