package com.example.appofzhejiang.fragment3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.appofzhejiang.R;
import com.example.appofzhejiang.fragment3.hotel.Hotel;

import java.util.List;
import java.util.UUID;

public class TicketDetailActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private String value;
    private int index;
    private ImageView mainImage, image1, image2, image3, image4;
    private TextView detailTitle, detailPrice, detailCompany, detailSales, detailGoods1, detailGoods2, detailPrice1, detailPrice2,
            detailBuy1, detailBuy2, detailContent1, detailContent2, detailContent3, detailContent4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_detail);
        toolbar = findViewById(R.id.toolbar_goods);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        mainImage = findViewById(R.id.image_ticketDetail);
        image1 = findViewById(R.id.detail_image1);
        image2 = findViewById(R.id.detail_image2);
        image3 = findViewById(R.id.detail_image3);
        image4 = findViewById(R.id.detail_image4);
        detailTitle = findViewById(R.id.detail_title);
        detailPrice = findViewById(R.id.detail_price);
        detailCompany = findViewById(R.id.detail_company);
        detailSales = findViewById(R.id.detail_sales);
        detailGoods1 = findViewById(R.id.detail_goods1);
        detailGoods2 = findViewById(R.id.detail_goods2);
        detailPrice1 = findViewById(R.id.detail_price1);
        detailPrice2 = findViewById(R.id.detail_price2);
        detailBuy1 = findViewById(R.id.detail_buy1);
        detailBuy2 = findViewById(R.id.detail_buy2);
        detailContent1 = findViewById(R.id.detail_content1);
        detailContent2 = findViewById(R.id.detail_content2);
        detailContent3 = findViewById(R.id.detail_content3);
        detailContent4 = findViewById(R.id.detail_content4);

        //取得从上一个Activity当中传递过来的Intent对象
        Intent intent = getIntent();
        //从Intent当中根据key取得value
        if (intent != null) {
            value = intent.getStringExtra("index");
            index = Integer.parseInt(value);
        }
        switch (index) {
            case 0:
                String title = null;
                String price = null;
                String company = null;
                String count = null;
                if (intent != null) {
                    title = intent.getStringExtra("title");
                    price = intent.getStringExtra("price");
                    company = intent.getStringExtra("company");
                    count = intent.getStringExtra("count");
                }
                mainImage.setImageResource(R.drawable.songcity);
                detailTitle.setText(title);
                detailPrice.setText(price);
                detailCompany.setText(company);
                detailSales.setText(count);
                detailGoods1.setText("杭州宋城景区");
                detailPrice1.setText("50");
                detailGoods2.setText("杭州宋城景区+千古情演出");
                detailPrice2.setText("160");
                detailContent1.setText(R.string.songcheng);
                detailContent2.setText(R.string.songchengqianguqing);
                image1.setImageResource(R.drawable.songcity);
                image2.setImageResource(R.drawable.songcheng);
                break;
            case 1:
                title = null;
                price = null;
                company = null;
                count = null;
                if (intent != null) {
                    title = intent.getStringExtra("title");
                    price = intent.getStringExtra("price");
                    company = intent.getStringExtra("company");
                    count = intent.getStringExtra("count");
                }
                mainImage.setImageResource(R.drawable.hotel_image);
                detailTitle.setText(title);
                detailPrice.setText(price);
                detailCompany.setText(company);
                detailSales.setText(count);
                detailGoods1.setText("大床房");
                detailPrice1.setText("120");
                detailGoods2.setText("双床房");
                detailPrice2.setText("150");
                detailContent1.setText(R.string.hotel);
                image1.setImageResource(R.drawable.hotel_image);
                break;
            case 2:
                title = null;
                price = null;
                company = null;
                count = null;
                if (intent != null) {
                    title = intent.getStringExtra("title");
                    price = intent.getStringExtra("price");
                    company = intent.getStringExtra("company");
                    count = intent.getStringExtra("count");
                }
                mainImage.setImageResource(R.drawable.taxi_image);
                detailTitle.setText(title);
                detailPrice.setText(price);
                detailCompany.setText(company);
                detailSales.setText(count);
                detailGoods1.setText("一日租");
                detailPrice1.setText("198");
                detailGoods2.setText("三日短期租");
                detailPrice2.setText("498");
                detailContent1.setText(R.string.taxi);
                image1.setImageResource(R.drawable.taxi_image);
                break;
            case 3:
                title = null;
                price = null;
                company = null;
                count = null;
                if (intent != null) {
                    title = intent.getStringExtra("title");
                    price = intent.getStringExtra("price");
                    company = intent.getStringExtra("company");
                    count = intent.getStringExtra("count");
                }
                mainImage.setImageResource(R.drawable.guider_image);
                detailTitle.setText(title);
                detailPrice.setText(price);
                detailCompany.setText(company);
                detailSales.setText(count);
                detailGoods1.setText("西湖导游");
                detailPrice1.setText("200");
                detailGoods2.setText("西湖+雷峰塔+岳王庙");
                detailPrice2.setText("300");
                detailContent1.setText("给导游一点关爱，导游还你一片美景，多点关心多点爱。");
                image1.setImageResource(R.drawable.guider_image);
                break;
            case 4:
                title = null;
                price = null;
                company = null;
                count = null;
                if (intent != null) {
                    title = intent.getStringExtra("title");
                    price = intent.getStringExtra("price");
                    company = intent.getStringExtra("company");
                    count = intent.getStringExtra("count");
                }
                mainImage.setImageResource(R.drawable.farmhouse);
                detailTitle.setText(title);
                detailPrice.setText(price);
                detailCompany.setText(company);
                detailSales.setText(count);
                detailGoods1.setText("小套间");
                detailPrice1.setText("398");
                detailGoods2.setText("大套间");
                detailPrice2.setText("598");
                detailContent1.setText(R.string.farmhouse);
                image1.setImageResource(R.drawable.farmhouse);
                break;
            case 5:
                title = null;
                price = null;
                company = null;
                count = null;
                if (intent != null) {
                    title = intent.getStringExtra("title");
                    price = intent.getStringExtra("price");
                    company = intent.getStringExtra("company");
                    count = intent.getStringExtra("count");
                }
                mainImage.setImageResource(R.drawable.longjingxiaren);
                detailTitle.setText(title);
                detailPrice.setText(price);
                detailCompany.setText(company);
                detailSales.setText(count);
                detailGoods1.setText("龙井虾仁");
                detailPrice1.setText("98");
                detailGoods2.setText("龙井虾仁+龙井茶");
                detailPrice2.setText("128");
                detailContent1.setText(R.string.longjingxiaren);
                image1.setImageResource(R.drawable.longjingxiaren);
                image2.setImageResource(R.drawable.westlakelongjing);
                break;
            case 6:
                title = null;
                price = null;
                company = null;
                count = null;
                if (intent != null) {
                    title = intent.getStringExtra("title");
                    price = intent.getStringExtra("price");
                    company = intent.getStringExtra("company");
                    count = intent.getStringExtra("count");
                }
                mainImage.setImageResource(R.drawable.westlakelongjing);
                detailTitle.setText(title);
                detailPrice.setText(price);
                detailCompany.setText(company);
                detailSales.setText(count);
                detailGoods1.setText("机器西湖龙井");
                detailPrice1.setText("198");
                detailGoods2.setText("手工西湖龙井");
                detailPrice2.setText("358");
                detailContent1.setText(R.string.longjing);
                image1.setImageResource(R.drawable.westlakelongjing);
                break;
            case 7:
                title = null;
                price = null;
                company = null;
                count = null;
                if (intent != null) {
                    title = intent.getStringExtra("title");
                    price = intent.getStringExtra("price");
                    company = intent.getStringExtra("company");
                    count = intent.getStringExtra("count");
                }
                mainImage.setImageResource(R.drawable.westlake);
                detailTitle.setText(title);
                detailPrice.setText(price);
                detailCompany.setText(company);
                detailSales.setText(count);
                detailGoods1.setText("西湖一日游");
                detailPrice1.setText("35");
                detailGoods2.setText("西湖三日游");
                detailPrice2.setText("98");
                detailContent1.setText("西湖的水，我的泪");
                image1.setImageResource(R.drawable.westlake);
                break;
        }
    }
}