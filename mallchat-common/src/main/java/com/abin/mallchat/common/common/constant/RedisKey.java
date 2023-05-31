package com.abin.mallchat.common.common.constant;

/**
 * <p>
 * Redis key
 * </p>
 *
 * @author <a href="https://github.com/zongzibinbin">abin</a>
 * @since 2021-06-10
 */
public class RedisKey {

    private static final String BASE_KEY = "mallchat:";

    /**
     * 在线用户列表
     */
    public static final String ONLINE_UID_ZET = "online";

    /**
     * 离线用户列表
     */
    public static final String OFFLINE_UID_ZET = "offline";

    /**
     * 用户信息
     */
    public static final String USER_INFO_STRING = "userInfo:uid_%d";

    /**
     * 用户token存放
     */
    public static final String USER_TOKEN_STRING = "userToken:uid_%d";

    public static String getKey(String key, Object... objects) {
        return BASE_KEY + String.format(key, objects);
    }

}
