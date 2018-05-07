import edu.duke.FileResource;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class LogAnalyzer {
    private ArrayList<LogEntry> records;

    public LogAnalyzer() {
        records = new ArrayList<>();
    }

    public void readFile(String filename) {
        FileResource fr = new FileResource(filename);
        for (String line : fr.lines()) {
            LogEntry entry = WebLogParser.parseEntry(line);
            records.add(entry);
        }
    }

    public void printAll() {
        for (LogEntry le: records) {
            System.out.println(le);
        }
    }

    public int countUniqueIps() {
        ArrayList<String> uniqueIps = new ArrayList<>();

        for (LogEntry record: records) {
            String ip = record.getIpAddress();
            if (!uniqueIps.contains(ip)) {
                uniqueIps.add(ip);
            }
        }
        return uniqueIps.size();
    }

    public void printAllHigherThanNum(int num) {
        for (LogEntry record: records) {
            int status = record.getStatusCode();

            if (status > num) {
                System.out.println(record);
            }
        }
    }

    public ArrayList<String> uniqueIPVisitsOnDay(String someday) {
        // someday format = "MMM DD"
        // MMM - first 3 characters of month, first letter capitalized
        // DD - day in 2 digits

        // This method accesses the web logs in records and returns
        // an ArrayList of Strings of unique IP addresses that had access on the given day

        ArrayList<String> uniqueIpsOnDay = new ArrayList<>();

        for (LogEntry record: records) {
            String date = record.getAccessTime().toString();
            //System.out.println(date);
            date = date.trim();
            String[] dateComposition = date.split(" ");
            String dateVisited = dateComposition[1] + " " + dateComposition[2];

            if (dateVisited.equals(someday)) {
                String ip = record.getIpAddress();
                if (!uniqueIpsOnDay.contains(ip)) {
                    uniqueIpsOnDay.add(ip);
                }
            }
        }
        return uniqueIpsOnDay;
    }

    public int countUniqueIPsInRange(int low, int high) {
        // This method returns the number of unique IP addresses in
        // records that have a status code in the range from low to high, inclusive
        ArrayList<String> uniqueIps = new ArrayList<>();

        for (LogEntry record: records) {
            int statusCode = record.getStatusCode();

            if (statusCode >= low && statusCode <= high) {
                String ip = record.getIpAddress();
                if (!uniqueIps.contains(ip)) {
                    uniqueIps.add(ip);
                }
            }
        }
        return uniqueIps.size();
    }

    public HashMap<String, Integer> countVisitsPerIP() {
        // maps the ip address to the number of times it appears in the records
        HashMap<String, Integer> counts = new HashMap<>();

        for (LogEntry le: records) {
            String ip = le.getIpAddress();

            if (!counts.containsKey(ip)) {
                counts.put(ip, 1);
            } else {
                counts.put(ip, counts.get(ip)+1);
            }
        }
        return counts;
    }

    public int mostNumberVisitsByIP(HashMap<String, Integer> map) {
        // max number of visits to a website by a single ip address
        int max = 0;
        for (String ip: map.keySet()) {
            if (map.get(ip) > max) {
                max = map.get(ip);
            }
        }
        return max;
    }

    public ArrayList<String> iPsMostVisits(HashMap<String, Integer> map) {
        int max = mostNumberVisitsByIP(map);

        ArrayList<String> list = new ArrayList<>();
        for (String ip: map.keySet()) {
            if (map.get(ip) == max) {
                list.add(ip);
            }
        }
        return list;
    }

    public HashMap<String, ArrayList<String>> ipsForDays() {
        // that uses records and
        // maps days from web logs to an ArrayList of IP addresses that occurred on that day

        HashMap<String, ArrayList<String>> map = new HashMap<>();

        for (LogEntry record: records) {
            String date = record.getAccessTime().toString();
            //System.out.println(date);
            date = date.trim();
            String[] dateComposition = date.split(" ");
            String dateVisited = dateComposition[1] + " " + dateComposition[2];

            ArrayList<String> ipList = new ArrayList<>();
            String ipAddress = record.getIpAddress();

            if (!map.containsKey(dateVisited)) {
                ipList.add(ipAddress);
                map.put(dateVisited, ipList);
            } else {
                ipList = map.get(dateVisited);
                ipList.add(ipAddress);
                map.put(dateVisited, ipList);
            }
        }
        return map;
    }

    public String dayWithMostIpVisits(HashMap<String, ArrayList<String>> map) {
        int max = 0;
        String maxDay = "";

        for (String day : map.keySet()) {
            int currListSize = map.get(day).size();
            if (currListSize > max) {
                max = currListSize;
                maxDay = day;
            }
        }
        return maxDay;
    }

    public ArrayList<String> ipsWithMostVisitsOnDay(HashMap<String, ArrayList<String>> map, String day) {
        ArrayList<String> ips = new ArrayList<>();

        ips = map.get(day);

        HashMap<String, Integer> countForGivenDay = new HashMap<>();
        for (String ip: ips) {
            if (!countForGivenDay.containsKey(ip)) {
                countForGivenDay.put(ip, 1);
            } else {
                countForGivenDay.put(ip, countForGivenDay.get(ip)+1);
            }
        }
        ips = iPsMostVisits(countForGivenDay);

        return ips;
    }
}
