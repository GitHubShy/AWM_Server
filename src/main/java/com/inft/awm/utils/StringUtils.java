package com.inft.awm.utils;

import java.util.Random;
/**
 * utils for String
 *
 * @author Yao Shi
 * @version 1.0
 * @date 30/10/2020 11:47 pm
 */
public class StringUtils {

    /** Judge if a String is null
     * @param text
     * @return
     */
    public static boolean isEmpty(String text) {
        return text == null || "".equals(text);
    }

    /**Get a random string
     * @param length
     * @return
     */
    public static String randomStrings(int length) {
        //define（A-Z，a-z，0-9）；
        String str = "zxcvbnmlkjhgfdsaqwertyuiopQWERTYUIOPASDFGHJKLZXCVBNM1234567890";
        //Random generate
        Random random = new Random();
        StringBuffer sb = new StringBuffer();

        for (int i = 0; i < length; ++i) {
            int number = random.nextInt(62);
            sb.append(str.charAt(number));
        }
        return sb.toString();
    }
}
