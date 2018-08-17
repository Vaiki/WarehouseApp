package com.vaiki.android.warehouse;

import android.content.Context;
import android.view.LayoutInflater;

import java.lang.reflect.Array;
import java.util.*;

/**
 * Created by E_not on 26.07.2018.
 */

public class DirectLab {
    private static DirectLab sDirectLab;
    private List<Direct> mDirects;
    private static HashSet<String> dir;
    private static List<String> catName;

    public static DirectLab get(Context context) {
        if (sDirectLab == null) {
            sDirectLab = new DirectLab(context);
        }
        return sDirectLab;
    }

    private DirectLab(Context context) {
        mDirects = new ArrayList<>();
        mDirects.add(new Direct("Болт", "Метизы", "m16x40"));
        mDirects.add(new Direct("Шпилька", "Метизы", "m12"));
        mDirects.add(new Direct("Болт", "Сантехника", "m6x80"));
        mDirects.add(new Direct("Кабель", "Электрика", "6кв"));
        mDirects.add(new Direct("Щиток", "Электрика", "30х40см"));
    }


    public List<Direct> getDirects() {
        return mDirects;
    }

    public static List<String> getCatName(DirectLab directLab) {
        dir = new HashSet<>();
        for (Direct d : directLab.getDirects()) {
            dir.add(d.getName_directory());
        }
        catName = new ArrayList<>(dir);
        return catName;
    }


}