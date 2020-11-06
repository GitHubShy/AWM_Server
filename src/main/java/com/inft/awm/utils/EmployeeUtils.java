package com.inft.awm.utils;
/**
 * utils for judge whether a employee is a Super Administrator, manager of engineer
 *
 * @author Yao Shi
 * @version 1.0
 * @date 30/10/2020 11:47 pm
 */
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
