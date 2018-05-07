import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class Tester {
    public static void main(String[] args) {
        Tester tester = new Tester();
        System.out.println("-------- test log analyzer --------");
        tester.testLogAnalyzer();

        System.out.println("\n-------- test unique ids --------");
        tester.testUniqueIps();

        System.out.println("\n------- print higher than num ---------");
        tester.testPrintHigherThanNum();

        System.out.println("\n-------- test unique ids on day --------");
        tester.testUniqueIpsOnDay();

        System.out.println("\n-------- test unique ids in range --------");
        tester.testUniqueIpsInRange();

        System.out.println("\n-------- test counts per ip --------");
        tester.testCounts();

        System.out.println("\n-------- test most visits --------");
        tester.testMostVisits();

        System.out.println("\n-------- test ip most visits --------");
        tester.testIpsMostVisits();

        System.out.println("\n-------- test ip for days --------");
        tester.testIpForDays();

        System.out.println("\n-------- test days with most visits --------");
        tester.testDayWithMostVisits();

        System.out.println("\n-------- test ips with most visits on day --------");
        tester.testIpsWithMostVisitsOnDay();
    }

    public void testLogEntry() {
        LogEntry le = new LogEntry("1.2.3.4", new Date(), "example request", 200, 500);
        System.out.println(le);
        LogEntry le2 = new LogEntry("1.2.100.4", new Date(), "example request 2", 300, 400);
        System.out.println(le2);
    }

    public void testLogAnalyzer() {
        // complete method
        LogAnalyzer analyzer = new LogAnalyzer();
        analyzer.readFile("short-test_log");
        analyzer.printAll();
    }

    public void testUniqueIps() {
        LogAnalyzer analyzer = new LogAnalyzer();
        analyzer.readFile("weblog2_log");
        int uniqueIps = analyzer.countUniqueIps();
        System.out.format("there are %d unique ips\n", uniqueIps);
    }

    public void testPrintHigherThanNum() {
        LogAnalyzer analyzer = new LogAnalyzer();
        analyzer.readFile("weblog1_log");
        analyzer.printAllHigherThanNum(400);
    }

    public void testUniqueIpsOnDay() {
        LogAnalyzer analyzer = new LogAnalyzer();
        analyzer.readFile("weblog2_log");

        ArrayList<String> ips = analyzer.uniqueIPVisitsOnDay("Sep 27");
        System.out.println("array list size: " + ips.size());
        for(String ip: ips) {
            System.out.println(ip);
        }
    }

    public void testUniqueIpsInRange() {
        LogAnalyzer analyzer = new LogAnalyzer();
        analyzer.readFile("weblog2_log");
        int count = analyzer.countUniqueIPsInRange(200, 299);
        System.out.println("ips in range: " + count);
    }

    public void testCounts() {
        LogAnalyzer la = new LogAnalyzer();
        la.readFile("short-test_log");
        HashMap<String, Integer> map = la.countVisitsPerIP();
        System.out.println(map);
    }

    public void testMostVisits() {
        LogAnalyzer la = new LogAnalyzer();
        la.readFile("weblog2_log");
        HashMap<String, Integer> map = la.countVisitsPerIP();
        int max = la.mostNumberVisitsByIP(map);
        System.out.println(map);
        System.out.println("max visits: " + max);
    }

    public void testIpsMostVisits() {
        LogAnalyzer la = new LogAnalyzer();
        la.readFile("weblog2_log");
        HashMap<String, Integer> map = la.countVisitsPerIP();

        ArrayList<String> list = la.iPsMostVisits(map);
        System.out.println(list);

    }

    public void testIpForDays() {
        LogAnalyzer la = new LogAnalyzer();
        la.readFile("weblog3-short_log");
        HashMap<String, ArrayList<String>> map = la.ipsForDays();

        System.out.println(map);
    }

    public void testDayWithMostVisits() {
        LogAnalyzer la = new LogAnalyzer();
        la.readFile("weblog2_log");
        HashMap<String, ArrayList<String>> map = la.ipsForDays();

        String day = la.dayWithMostIpVisits(map);

        System.out.println("day with most visits: " + day);
    }

    public void testIpsWithMostVisitsOnDay() {
        LogAnalyzer la = new LogAnalyzer();
        la.readFile("weblog2_log");
        HashMap<String, ArrayList<String>> map = la.ipsForDays();

        ArrayList<String> ipList = la.ipsWithMostVisitsOnDay(map, "Sep 29");
        System.out.println(ipList);
    }
}