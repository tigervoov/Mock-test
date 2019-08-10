package sales;


import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

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
    @Spy
    SalesDao salesDao;

    @InjectMocks
    SalesApp salesApp = new SalesApp();

    @Test
    public void testCheckSalesDateWithTrue() throws ParseException {
        //given
        DateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
        Date effectiveFrom = dateFormat1.parse("2019-06-01");
        DateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd");
        Date effectiveTo = dateFormat2.parse("2019-10-01");
        //when
        Sales sales = spy(new Sales());
        doReturn(effectiveFrom).when(sales).getEffectiveFrom();
        doReturn(effectiveTo).when(sales).getEffectiveTo();
        doReturn(sales).when(salesDao).getSalesBySalesId(any());
        //then
        Assert.assertEquals(sales, salesApp.checkSalesDate("DUMMY"));
    }
    @Test
    public void testCheckSalesDateWithFalse() throws ParseException {
        //given
        DateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
        Date effectiveFrom = dateFormat1.parse("2019-06-01");
        DateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd");
        Date effectiveTo = dateFormat2.parse("2019-08-01");
        //when
        Sales sales = spy(new Sales());
        doReturn(effectiveFrom).when(sales).getEffectiveFrom();
        doReturn(effectiveTo).when(sales).getEffectiveTo();
        doReturn(sales).when(salesDao).getSalesBySalesId(any());
        //then
        Assert.assertEquals(null, salesApp.checkSalesDate("DUMMY"));
    }
    @Test
    public void testGetHeaders() {
        //given
        Boolean isNatTrade1=true;
        Boolean isNatTrade2=false;
        //when
        List<String> headers1=salesApp.getHeaders(isNatTrade1);
        List<String> headers2=salesApp.getHeaders(isNatTrade2);
        //then
        Assert.assertEquals( Arrays.asList("Sales ID", "Sales Name", "Activity", "Time"),headers1);
        Assert.assertEquals( Arrays.asList("Sales ID", "Sales Name", "Activity", "Local Time"),headers2);
    }
    @Test
    public void testGetFilterReportDataList() {
        //given
        SalesReportData salesReportData=spy(new SalesReportData());
        salesReportData.setConfidential(true);
        //when
        doReturn("SalesActivity").when(salesReportData).getType();
        //then
        Assert.assertEquals(Arrays.asList(salesReportData).subList(0,1),salesApp.getFilterReportDataList(1,true,Arrays.asList(salesReportData)));
    }


}

