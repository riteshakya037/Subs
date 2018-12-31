package com.riteshakya.domain;

import java.io.File;

/**
 * @author Ritesh Shakya
 */

@SuppressWarnings("unused") public class DatabaseNames {
    public static final String TABLE_SUBSCRIPTIONS = "subs";
    public static final String TABLE_USER_DATA = "users";
    public static final String PATH_DEFAULT_IMAGE = "generic/default.png";
    public static final String DELETED_FLAG = "isDeleted";
    public static final String POPULAR_FLAG = "isPopular";
    public static final String USER_PROFILE = "user_profile";

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
