package sales;


import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class SalesAppTest {

    //	@Test
//	public void testGenerateReport() {
//
//		SalesApp salesApp = new SalesApp();
//		salesApp.generateSalesActivityReport("DUMMY", 1000, false, false);
//
//	}
    @Mock
    SalesDao salesDao;

    @Test
    public void should_get_sales_when_check_sales_with_available_data() throws ParseException {

//        System.out.println(new Date());

//	salesApp.generateSalesActivityReport("DUMMY", 1000, false, false);
        DateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
        Date effectiveFrom = dateFormat1.parse("2019-06-01");
        DateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd");
        Date effectiveTo = dateFormat2.parse("2019-10-01");
//        System.out.println(new Date().after(effectiveTo));
//        System.out.println(new Date().before(effectiveFrom));

        Sales sales = mock(Sales.class);
        when(sales.getEffectiveFrom()).thenReturn(effectiveFrom);
        when(sales.getEffectiveTo()).thenReturn(effectiveTo);

//        SalesDao salesDao = spy(new SalesDao());
        when(salesDao.getSalesBySalesId(any())).thenReturn(new Sales());
//        doReturn(new Sales()).when(salesDao).getSalesBySalesId(any());
        //then
        SalesApp salesApp = new SalesApp();
        Assert.assertEquals(sales, salesApp.checkSalesDate("DUMMY"));
    }
}

