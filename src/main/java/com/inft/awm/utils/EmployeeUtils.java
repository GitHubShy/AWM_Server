package com.inft.awm.utils;

public class EmployeeUtils {

    public static String getEmployeeType(Integer id) {
        String title;
        if (id == 99) {
            title = "Super Administrator";
        } else if (id == 1) {
            title = "Manager";
        } else {
            title = "Engineer";
        }
        return title;
    }
}
