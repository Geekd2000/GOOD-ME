package com.example.demo.Utils;

import com.alibaba.fastjson.JSON;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MilkUtil {

    Future<List<Milk>> result;

    public MilkUtil() {
        this.initMilkLists();
    }

    /**
     * 初始化TicketLists
     */
    private void initMilkLists() {
        sendRequest("http://121.43.145.130:9898/milk/queryAllMilk");
    }

    /**
     * 向服务器发送请求
     *
     * @param address
     */
    private void sendRequest(final String address) {
        ExecutorService pool = Executors.newFixedThreadPool(100);
        result = pool.submit(new Callable<List<Milk>>() {
            @Override
            public List<Milk> call() throws Exception {
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
    private List<Milk> parseJSON(String responseData) {
        return JSON.parseArray(responseData, Milk.class);
    }

    /**
     * 得到MilkList
     */
    public List<Milk> getMilkList() {
        try {
            return result.get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
