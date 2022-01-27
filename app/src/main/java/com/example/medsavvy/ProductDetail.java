package com.example.medsavvy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.medsavvy.RecycleView.adapter.RecommendAdapter;
import com.example.medsavvy.RecycleView.model.ApiProduct;

import java.util.ArrayList;
import java.util.List;

public class ProductDetail extends AppCompatActivity implements RecommendAdapter.IApiResponseClick {
    int count=0;
    String merchantName[] = {"India", "China", "australia", "Portugle", "America", "NewZealand"};
    String merchantDescription[] = {"India1", "China1", "australia", "Portugle", "America", "NewZealand"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        //displayLocalRecyclerView();
        ImageView imageurl=findViewById(R.id.iv_prod_detail);
        TextView productname=findViewById(R.id.tv_product_name);

        Intent intent = getIntent();
        String url = intent.getExtras().getString("imageUrl");
        String name = intent.getExtras().getString("productName");
        productname.setText(name);
        Glide.with(imageurl.getContext()).load(url).placeholder(R.drawable.ic_login).into(imageurl);

        findViewById(R.id.iv_home_login).setOnClickListener(v -> {
            Intent i=new Intent(ProductDetail.this,Login.class);
            startActivity(i);
        });

        findViewById(R.id.iv_home_cart).setOnClickListener(v -> {
            Intent i=new Intent(ProductDetail.this,Cart.class);
            startActivity(i);
        });

//        Button increment=findViewById(R.id.bn_increment);
//        Button decrement=findViewById(R.id.bn_decrement);
//        TextView display=findViewById(R.id.tv_quant);
//
//        increment.setOnClickListener(v -> {
//            count++;
//            display.setText(""+count);
//        });
//
//        decrement.setOnClickListener(v -> {
//                count--;
//            if(count<0)count=0;
//            display.setText(""+count);
//        });
        ListView listView= (ListView)findViewById(R.id.id_list);
        CustomAdapter customAdapter=new CustomAdapter();
        listView.setAdapter(customAdapter);

    }
//    private  void displayLocalRecyclerView(){
//        List<ApiProduct> userDataList=new ArrayList<>();
//        generateUserData(userDataList);
//        //RecyclerView recyclerView=findViewById(R.id.recycle_merch);
//        RecommendAdapter recycleViewAdapter=new RecommendAdapter(userDataList,ProductDetail.this);
//        LinearLayoutManager HorizontalLayout= new LinearLayoutManager(ProductDetail.this,LinearLayoutManager.HORIZONTAL,false);
//
//
//        //recyclerView.setLayoutManager(HorizontalLayout);
//        //recyclerView.setAdapter(recycleViewAdapter);
//
//
//    }


    private void generateUserData(List<ApiProduct> userDataList) {
        userDataList.add(new ApiProduct("Employee 1", "https://fortmyersradon.com/wp-content/uploads/2019/12/dummy-user-img-1.png", 10.0));
        userDataList.add(new ApiProduct("Employee 2", "https://d2cax41o7ahm5l.cloudfront.net/mi/speaker-photo/appliedmicrobiology-minl-2018-Rucha-Ridhorkar-62007-7135.png", 100.0));
        userDataList.add(new ApiProduct("Employee 3", "https://img.icons8.com/bubbles/2x/user-male.png", 23.98));
        userDataList.add(new ApiProduct("Employee 4", "https://cdn4.iconfinder.com/data/icons/small-n-flat/24/user-alt-512.png", 76.9));
        userDataList.add(new ApiProduct("Employee 5", "https://toppng.com/uploads/preview/user-font-awesome-nuevo-usuario-icono-11563566658mjtfvilgcs.png", 7665.0));
        userDataList.add(new ApiProduct("Employee 6", "https://image.flaticon.com/icons/png/512/149/149071.png", 123.56));
        userDataList.add(new ApiProduct("Employee 7", "https://www.winhelponline.com/blog/wp-content/uploads/2017/12/user.png", 141.00));
        userDataList.add(new ApiProduct("Employee 8", "https://avatarfiles.alphacoders.com/791/79102.png", 1656.00));
        userDataList.add(new ApiProduct("Employee 9", "https://i.pinimg.com/originals/7c/c7/a6/7cc7a630624d20f7797cb4c8e93c09c1.png", 181.0));
        userDataList.add(new ApiProduct("Employee 10", "https://cdn1.iconfinder.com/data/icons/avatar-2-2/512/Casual_Man_2-512.png", 817.0));
    }

    @Override
    public void onUserClick(ApiProduct userDatamodel) {
        Intent intent=new Intent(ProductDetail.this,ProductDetail.class);
        intent.putExtra("imageUrl",userDatamodel.getImage());
        startActivity(intent);
    }

    class CustomAdapter extends BaseAdapter
    {

        @Override
        public int getCount() {
            return merchantName.length;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            view=getLayoutInflater().inflate(R.layout.custom_listview,null);
            TextView textView_name=(TextView)view.findViewById(R.id.li_name);
            TextView textView_price=(TextView)view.findViewById(R.id.li_price);
            textView_name.setText(merchantName[i]);
            textView_price.setText(merchantDescription[i]);
            return view;
        }

    }
}