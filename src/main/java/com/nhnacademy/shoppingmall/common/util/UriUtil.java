package com.nhnacademy.shoppingmall.common.util;
public class UriUtil {

    public static final String THUMBNAIL_PREFIX = "/resources/thumbnail/";
    public static final String IMAGE_PREFIX = "/resources/image/";
    public static final String DEFAULT_POSTFIX = ".png";
    public static final String NO_IMAGE = "/resources/no-image.png";

    private UriUtil(){
    }

    public static String toThumbnailUri(int id){
        return THUMBNAIL_PREFIX + id + DEFAULT_POSTFIX;
    }

    public static String toImageUri(int id){
        return IMAGE_PREFIX + id + DEFAULT_POSTFIX;
    }

}
