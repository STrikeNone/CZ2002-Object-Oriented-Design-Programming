package Instagram.Regex;

import java.util.regex.*;

public class Regex {
    public static void main(String[] args){
        String longString = " Derek Banas CA AK 12345 PA (412)555-1212 johnsmith ";
        String strangeString = " 1Z aaa *** * {{{ {{ { }";
        /*
        [0-9] - Any character you want
        [^A-G]
        */

        // Word that is 2 to 20 characters in length with space before after
        //regexChecker("\\s[A-Za-z]{2,20}\\s", longString);

        // Looking for ZipCodes which are 5 digits long
        //regexChecker("\\s\\d{5}\\s", longString);

        // 2 Characters that start with C or A
        //regexChecker("A[KLRZ]|C[AOT]", longString);

        // {5,} minimum of 5 characters but no maximum

        // Finding weird symbols (. ^ * + ? {} [] \ ()) requires \\ as it is protected
        //regexChecker("(\\{+)", strangeString);

        // Finding anything that appears n number of times
        //regexChecker(".{3}", strangeString);

        // Searching an Email Address
        //regexChecker("[A-Za-z0-9._&-]+@[A-Za-z0-9._]+\\.[A-Za-z]{2,4}", "yihao.puah@gmail.com");

        // Replacing spaces with commas
        regexReplace(longString);
    }

    public static void regexChecker(String theRegex, String str2Check){
        Pattern checkRegex = Pattern.compile(theRegex);
        Matcher regexMatcher = checkRegex.matcher(str2Check);
        while(regexMatcher.find()){
            if(regexMatcher.group().length() != 0){
                System.out.println(regexMatcher.group().trim());
            }
            System.out.printf("Index range: %d to %d\n", regexMatcher.start(), regexMatcher.end());
        }
    }

    public static void regexReplace(String str2Replace){
        // Cleaning up spaces
        Pattern replace = Pattern.compile("\\s+");
        Matcher regexMatcher = replace.matcher(str2Replace.trim());
        // Replaces spaces with a comma
        System.out.println(regexMatcher.replaceAll(", "));
    }
}
