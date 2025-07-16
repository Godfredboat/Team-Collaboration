package model;

import java.util.*;
import java.util.stream.Collectors;

public class Report {

    // 1. Monthly burn rate (total spending in a specific month/year)
    public static double calculateMonthlyBurnRate(List<Expenditure> expenditures, String month, String year) {
        return expenditures.stream()
                .filter(e -> e.getDate().startsWith(year + "-" + month))
                .mapToDouble(Expenditure::getAmount)
                .sum();
    }

    // 2. Forecast next quarter's spending (based on average expenditure)
    public static double forecastNextQuarterSpending(List<Expenditure> expenditures) {
        if (expenditures.isEmpty()) return 0;
        double total = expenditures.stream().mapToDouble(Expenditure::getAmount).sum();
        double average = total / expenditures.size();
        return average * 3;
    }

    // 3. Total spent on a given category
    public static double totalSpentOnCategory(List<Expenditure> expenditures, String category) {
        return expenditures.stream()
                .filter(e -> e.getCategory().equalsIgnoreCase(category))
                .mapToDouble(Expenditure::getAmount)
                .sum();
    }

    // 4. Group expenditures by bank account
    public static Map<String, List<Expenditure>> groupByAccount(List<Expenditure> expenditures) {
        return expenditures.stream()
                .collect(Collectors.groupingBy(Expenditure::getAccountId));
    }

    // 5. Top-spending category
    public static String getTopCategory(List<Expenditure> expenditures) {
        return expenditures.stream()
                .collect(Collectors.groupingBy(Expenditure::getCategory,
                        Collectors.summingDouble(Expenditure::getAmount)))
                .entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse("None");
    }

    // 6. List all expenditures within a specific date range
    public static List<Expenditure> getExpendituresInRange(List<Expenditure> expenditures, String startDate, String endDate) {
        return expenditures.stream()
                .filter(e -> e.getDate().compareTo(startDate) >= 0 && e.getDate().compareTo(endDate) <= 0)
                .collect(Collectors.toList());
    }

    // 7. Total amount withdrawn per bank account
    public static Map<String, Double> totalWithdrawnPerAccount(List<Expenditure> expenditures) {
        return expenditures.stream()
                .collect(Collectors.groupingBy(Expenditure::getAccountId,
                        Collectors.summingDouble(Expenditure::getAmount)));
    }

    // 8. Average spend per category
    public static Map<String, Double> averageSpendPerCategory(List<Expenditure> expenditures) {
        return expenditures.stream()
                .collect(Collectors.groupingBy(Expenditure::getCategory,
                        Collectors.averagingDouble(Expenditure::getAmount)));
    }
}
