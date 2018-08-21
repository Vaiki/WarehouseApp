package com.vaiki.android.warehouse.database;

/**
 * Created by E_not on 21.08.2018.
 */

public class DirectDbSchema {
    public static final class DirectTable {
        public static final String NAME = "directs";
         public static final class Cols {
             public static final String UUID = "uuid";
             public static final String DIRECTORY = "directory";
             public static final String PRODUCT = "product";
             public static final String DESCRIPTION ="description";
             public static final String QTY ="qty";
         }

    }
}
