package data;

//import raven.application.form.other.bills.Bills;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import models.*;

public class ProductData {

    public static List<String> stringCategories = new ArrayList<>(){};
    public static Map<String, List<Product>> categorizedProducts = new HashMap<>();
    public static List<Category> categories = new ArrayList<>();
    public static List<Staff> employees = new ArrayList<>();
}
