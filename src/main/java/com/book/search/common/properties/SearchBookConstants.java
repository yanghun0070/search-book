package com.book.search.common.properties;

import java.util.Locale;

public class SearchBookConstants {

    public static final Locale DEFAULT_LOCALE = new Locale("en", "US");

    public static final String DEFAULT_CHARSET = "UTF-8";
    public static final String REALM_NAME = "search-book";

    public static final long TOKEN_EXPIRE_SEC = 86400 * 2L; //이틀
}
