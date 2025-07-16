import java.util.*;
import java.io.*;

public class CategoryManager {
    private Map<String, String> categoryToCode = new HashMap<>(); // Maps category name to expenditure code
    private Map<String, String> codeToCategory = new HashMap<>(); // Maps expenditure code to category name

    private String capitalizeCategory(String category) {
        if (category == null || category.isEmpty()) {
            return category;
        }
        
        StringBuilder capitalized = new StringBuilder();
        String[] words = category.split("\\s+");
        
        for (String word : words) {
            if (word.length() > 0) {
                capitalized.append(Character.toUpperCase(word.charAt(0)))
                          .append(word.substring(1).toLowerCase());
            }
            capitalized.append(" ");
        }
        
        return capitalized.toString().trim();
    }

    public boolean addCategory(String category, String expenditureCode) {
        String normalizedCategory = category.trim().toLowerCase();
        String normalizedCode = expenditureCode.trim().toUpperCase();
        
        if (categoryToCode.containsKey(normalizedCategory) || codeToCategory.containsKey(normalizedCode)) {
            return false;
        }
        
        categoryToCode.put(normalizedCategory, normalizedCode);
        codeToCategory.put(normalizedCode, normalizedCategory);
        return true;
    }

    public boolean removeCategory(String category) {
        String normalizedCategory = category.trim().toLowerCase();
        String code = categoryToCode.get(normalizedCategory);
        
        if (code != null) {
            categoryToCode.remove(normalizedCategory);
            codeToCategory.remove(code);
            return true;
        }
        return false;
    }

    public boolean containsCategory(String category) {
        return categoryToCode.containsKey(category.trim().toLowerCase());
    }

    public List<String> getAllCategoriesWithCodes() {
        List<String> result = new ArrayList<>();
        for (Map.Entry<String, String> entry : categoryToCode.entrySet()) {
            String formattedName = capitalize(entry.getKey());
            result.add(formattedName + " (" + entry.getValue() + ")");
        }
        Collections.sort(result);
        return result;
    }
    private String capitalize(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        return str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase();
    }
    
    public String getExpenditureCode(String category) {
        return categoryToCode.get(category.trim().toLowerCase());
    }

    public String getCategoryByCode(String code) {
        return codeToCategory.get(code.trim().toUpperCase());
    }

    public List<String> getAllCategories() {
        List<String> categories = new ArrayList<>();
        for (String category : categoryToCode.keySet()) {
            categories.add(capitalizeCategory(category));
        }
        Collections.sort(categories);
        return categories;
    }

    public List<String> getAllExpenditureCodes() {
        return new ArrayList<>(codeToCategory.keySet());
    }

    public void saveToFile(String filename) throws IOException {
        try (PrintWriter writer = new PrintWriter(filename)) {
            for (Map.Entry<String, String> entry : categoryToCode.entrySet()) {
                writer.printf("%s,%s%n", capitalizeCategory(entry.getKey()), entry.getValue());
            }
        }
    }

    public void loadFromFile(String filename) throws IOException {
        categoryToCode.clear();
        codeToCategory.clear();
        
        try (Scanner scanner = new Scanner(new File(filename))) {
            while (scanner.hasNextLine()) {
                String[] parts = scanner.nextLine().split(",");
                if (parts.length >= 2) {
                    String category = parts[0].trim().toLowerCase();
                    String code = parts[1].trim().toUpperCase();
                    categoryToCode.put(category, code);
                    codeToCategory.put(code, category);
                }
            }
        }
    }
}