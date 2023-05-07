package com.example.demo.util;

public class RedisKeyUtils {

    public static final String MAP_KEY_CLASS_LIKED = "MAP_CLASS_LIKED";
    public static final String MAP_KEY_USER_UNLIKED = "MAP_USER_UNLIKED";
    public static final String MAP_KEY_USER_LIKED = "MAP_USER_LIKED";

    /**
     * 拼接被收藏的class id和收藏的人的id作为key。格式 222222::333333
     * @param likedClassId 被收藏的class id
     * @param likedPostId 收藏的人的id
     * @return
     */
    public static String getLikedKey(String likedClassId, String likedPostId){
        StringBuilder builder = new StringBuilder();
        builder.append(likedClassId);
        builder.append("::");
        builder.append(likedPostId);
        return builder.toString();
    }
}
