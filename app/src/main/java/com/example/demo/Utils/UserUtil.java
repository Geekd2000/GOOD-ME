package com.example.demo.Utils;

import com.alibaba.fastjson.JSON;

import java.io.IOException;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author: JJY
 * @date: 2021/6/8 12:08
 */
public class UserUtil {

    Future<User> result;

    //登陆时获取密码的构造函数
    public UserUtil(String username){
        sendGetRequest(username);
    }

    /**
     * 使用GET访问网络
     *
     * @param username
     * @return 服务器返回的结果
     */
    private void sendGetRequest(final String username) {
        ExecutorService pool = Executors.newFixedThreadPool(2);
        result = pool.submit(new Callable<User>() {
            @Override
            public User call() throws Exception {
                try {
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder()
                            .url("http://121.43.145.130:9898/user/queryByUsername?username="+username)
                            .build();
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    return parseGetJSON(responseData);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }
        });
    }

    /**
     * 解析json
     *
     * @param responseData
     * @return
     */
    private User parseGetJSON(String responseData) {
        return JSON.parseObject(responseData, User.class);
    }

    /**
     * 得到LoginRegisterBean
     */
    public User getUser() {
        try {
            return result.get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
