package io.subs.domain;

import java.io.File;

/**
 * @author Ritesh Shakya
 */

public class DatabaseNames {
    public static final String TABLE_SUBSCRIPTIONS = "subs";
    public static final String TALBE_USER_DATA = "users";
    public static final String PATH_DEFAULT_IMAGE = "generic/default.png";
    public static final String DELETED_FLAG = "isDeleted";

    public static String createPath(String... individualTables) {
        StringBuilder output = new StringBuilder();
        String delimiter = File.separator;
        boolean and = false;
        for (String e : individualTables) {
            if (and) {
                output.append(delimiter);
            }
            output.append(e);
            and = true;
        }
        return output.toString();
    }
}
