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
 * @date: 2021/6/9 19:39
 */
public class UserByName {

    Future<User> result;

    public UserByName(String name) {
        this.initUser(name);
    }

    /**
     * 初始化User
     */
    private void initUser(String name) {
        sendRequest("http://121.43.145.130:9898/user/queryByUsername?username=" + name);
    }

    /**
     * 向服务器发送请求
     *
     * @param address
     */
    private void sendRequest(final String address) {
        ExecutorService pool = Executors.newFixedThreadPool(2);
        result = pool.submit(new Callable<User>() {
            @Override
            public User call() throws Exception {
                try {
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder()
                            .url(address)
                            .build();
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    return parseJSON(responseData);
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
    private User parseJSON(String responseData) {
        return JSON.parseObject(responseData, User.class);
    }

    /**
     * 得到User
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
