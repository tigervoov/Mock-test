package sales;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class SalesApp {
    SalesDao salesDao;

    public SalesApp(){
        salesDao = new SalesDao();
    }

    public void generateSalesActivityReport(String salesId, int maxRow, boolean isNatTrade, boolean isSupervisor) {

//		SalesDao salesDao = new SalesDao();
        SalesReportDao salesReportDao = new SalesReportDao();
//		List<String> headers = null;
//		List<SalesReportData> filteredReportDataList = new ArrayList<SalesReportData>();

        Sales sales = checkSalesDate(salesId);//

        if (sales == null) return;
        List<SalesReportData> reportDataList = salesReportDao.getReportData(sales);

        List<SalesReportData> filteredReportDataList = getFilterReportDataList(maxRow, isSupervisor, reportDataList);//

        List<String> headers = getHeaders(isNatTrade);//

        SalesActivityReport report = this.generateReport(headers, reportDataList);

        EcmService ecmService = new EcmService();
        ecmService.uploadDocument(report.toXml());

    }

    protected List<SalesReportData> getFilterReportDataList(int maxRow, boolean isSupervisor, List<SalesReportData> reportDataList) {
        List<SalesReportData> filteredReportDataList = new ArrayList<SalesReportData>();
        for (SalesReportData data : reportDataList) {
            if ("SalesActivity".equalsIgnoreCase(data.getType())) {
                if (data.isConfidential()) {
                    if (isSupervisor) {
                        filteredReportDataList.add(data);
                    }
                } else {
                    filteredReportDataList.add(data);
                }
            }
        }

        List<SalesReportData> tempList = new ArrayList<SalesReportData>();
        for (int i = 0; i < reportDataList.size() || i < maxRow; i++) {
            tempList.add(reportDataList.get(i));
        }
        filteredReportDataList = tempList;
        return filteredReportDataList;
    }

    protected List<String> getHeaders(boolean isNatTrade) {
        List<String> headers = null;
        if (isNatTrade) {
            headers = Arrays.asList("Sales ID", "Sales Name", "Activity", "Time");
        } else {
            headers = Arrays.asList("Sales ID", "Sales Name", "Activity", "Local Time");
        }
        return headers;
    }

    protected Sales checkSalesDate(String salesId) {
        if (salesId == null) {
            return null;
        }

        Sales sales = salesDao.getSalesBySalesId(salesId);

        Date today = new Date();
        if (today.after(sales.getEffectiveTo())
                || today.before(sales.getEffectiveFrom())) {
            return null;
        }
        return sales;
    }

    private SalesActivityReport generateReport(List<String> headers, List<SalesReportData> reportDataList) {
        // TODO Auto-generated method stub
        return null;
    }

}
