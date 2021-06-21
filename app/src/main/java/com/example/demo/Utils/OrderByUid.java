package com.example.demo.Utils;

import com.alibaba.fastjson.JSON;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author: JJY
 * @date: 2021/6/9 19:42
 */
public class OrderByUid {

    Future<List<Order>> result;

    public OrderByUid(String id) {
        this.initOrderLists(id);
    }

    /**
     * 初始化OrderLists
     */
    private void initOrderLists(String id) {
        sendRequest("http://121.43.145.130:9898/order/queryAllOrderByUid?u_id=" + id);
    }

    /**
     * 向服务器发送请求
     *
     * @param address
     */
    private void sendRequest(final String address) {
        ExecutorService pool = Executors.newFixedThreadPool(100);
        result = pool.submit(new Callable<List<Order>>() {
            @Override
            public List<Order> call() throws Exception {
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
    private List<Order> parseJSON(String responseData) {
        return JSON.parseArray(responseData, Order.class);
    }

    /**
     * 得到OrderList
     */
    public List<Order> getOrderList() {
        try {
            return result.get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
