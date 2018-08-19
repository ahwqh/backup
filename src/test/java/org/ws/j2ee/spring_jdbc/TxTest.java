package org.ws.j2ee.spring_jdbc;

import org.junit.Test;
import org.ws.j2ee.spring_jdbc.dao.EmpDao;
import org.ws.j2ee.spring_jdbc.dao.EmpDaoImpl;

import junit.framework.Assert;

public class TxTest extends DataSourceTest
{
    @Test
    public void tx()
    {
    	EmpDao dao = ctx.getBean(EmpDao.class);
    	int result = dao.addEmp("demon740603",4.04);
    	Assert.assertTrue(result>0);
    }
}
