package com.vaiki.android.warehouse.database;

/**sc
 * Created by E_not on 21.08.2018.
 */

public class DirectDbSchema {
    public static final class TableAll {
        public static final String NAME = "directs";

        public static final class Cols {
            public static final String UUID = "uuid";
            public static final String DIRECTORY = "directory";
            public static final String PRODUCT = "product";
            public static final String DESCRIPTION = "description";
            public static final String QTY = "qty";
        }

    }

    public static final class TableDirectory {
        public static final String DIRECTORY_NAME = "catalog";

        public static final class Cols {
            public static final String DIRECTORY = "namedirectory";

        }

    }
    public static final class TableProduct {
        public static final String PRODUCT_NAME = "product";

        public static final class Cols {
            public static final String PRODUCT = "nameproduct";

        }

    }
    public static final class TableDescription {
        public static final String DESCRIPTION_NAME = "description";

        public static final class Cols {
            public static final String DESCRIPTION = "namedes";

        }

    }
}
