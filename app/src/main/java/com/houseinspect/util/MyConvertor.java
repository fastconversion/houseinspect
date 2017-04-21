package com.houseinspect.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Lalit on 11/12/2016.
 */
public class MyConvertor {

    public static ArrayList<String> getArrayList(String[] items) {
        ArrayList<String> arrayList = new ArrayList<>();
        List<String> wordList = Arrays.asList(items);
        for (String item : wordList) {
            arrayList.add(item);
        }
        return arrayList;
    }


    public static String getTitle(String title){
        int spaceIndex = title.indexOf(" ");
        title =  title.substring(spaceIndex, title.length());
        return title.trim();
    }
}
