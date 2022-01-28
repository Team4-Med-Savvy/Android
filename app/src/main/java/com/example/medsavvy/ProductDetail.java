package com.example.medsavvy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.medsavvy.RecycleView.adapter.RecommendAdapter;
import com.example.medsavvy.RecycleView.model.ApiProduct;
import com.example.medsavvy.retrofit.model.MerchantDto;
import com.example.medsavvy.retrofit.model.MerchantProductDetailDto;
import com.example.medsavvy.retrofit.model.ProductDetailDto;
import com.example.medsavvy.retrofit.model.ProductDto;
import com.example.medsavvy.retrofit.model.RequestCartDto;
import com.example.medsavvy.retrofit.model.ResponseProductDto;
import com.example.medsavvy.retrofit.network.IPostCartApi;
import com.example.medsavvy.retrofit.network.IPostProductApi;
import com.example.medsavvy.retrofit.networkmanager.CartRetrofilBuilder;
import com.example.medsavvy.retrofit.networkmanager.ProductRetrofitBuilder;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ProductDetail extends AppCompatActivity implements RecommendAdapter.IApiResponseClick {
    int count=0;
    String merchantName[] = {"India", "China", "australia", "Portugle", "America", "NewZealand"};
    String merchantDescription[] = {"India1", "China1", "australia", "Portugle", "America", "NewZealand"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        ImageView imageurl=findViewById(R.id.iv_prod_detail);
        TextView productname=findViewById(R.id.tv_product_name);




        Intent intent = getIntent();
        String url = intent.getExtras().getString("imageUrl");
        String name = intent.getExtras().getString("productName");
        String prodId=intent.getExtras().getString("productId");
        System.out.println("productId"+prodId);
        System.out.println("url "+ url);
        productname.setText(name);
        Glide.with(imageurl.getContext()).load(url).placeholder(R.drawable.ic_login).into(imageurl);
        displayLocalRecyclerView();

        findViewById(R.id.iv_home_login).setOnClickListener(v -> {
            Intent i=new Intent(ProductDetail.this,Login.class);
            startActivity(i);
        });

        findViewById(R.id.iv_home_cart).setOnClickListener(v -> {
            Intent i=new Intent(ProductDetail.this,Cart.class);
            startActivity(i);
        });

        findViewById(R.id.bn_add_to_cart).setOnClickListener(v -> {
            init();
        });

        findViewById(R.id.bn_buy_now).setOnClickListener(v -> {
            init();
            Intent i=new Intent(ProductDetail.this,Cart.class);
            startActivity(i);
        });

    }

    private void init()
    {
        Retrofit retrofit= CartRetrofilBuilder.getInstance();
        IPostCartApi iPostCartApi=retrofit.create(IPostCartApi.class);
        RequestCartDto requestCartDto=new RequestCartDto();

        SharedPreferences sharedPreferences = getSharedPreferences("com.example.medsavvy", Context.MODE_PRIVATE);        Intent intent=getIntent();
        String prodId=intent.getExtras().getString("productId");

        requestCartDto.setProductId(prodId);
        requestCartDto.setMerchantId("61eae2b14d72a238cfa62364");
        requestCartDto.setPrice(new Long(0));
        Call<Void> responsecart=iPostCartApi.addProduct(sharedPreferences.getString("em","default"),requestCartDto);

        responsecart.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Toast.makeText(ProductDetail.this,"Added to cart",Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(ProductDetail.this,"Failure",Toast.LENGTH_SHORT).show();

            }
        });

   }
    private  void displayLocalRecyclerView(){

        Retrofit retrofit= ProductRetrofitBuilder.getInstance();
        IPostProductApi iPostProductApi=retrofit.create(IPostProductApi.class);
        String pid=getIntent().getExtras().getString("productId");
        Call<ResponseProductDto> productresponse=iPostProductApi.findmerchantlist(pid);
            productresponse.enqueue(new Callback<ResponseProductDto>() {
                @Override
                public void onResponse(Call<ResponseProductDto> call, Response<ResponseProductDto> response) {
                    TextView decsrip=findViewById(R.id.tv_prod_descrip);
                    decsrip.setText(response.body().getDescription());
                    ListView listView= (ListView)findViewById(R.id.id_list);

                    CustomAdapter customAdapter=new CustomAdapter(ProductDetail.this,response.body().getMerchantProductDetailDtos());
                    listView.setAdapter(customAdapter);
                }
                @Override
                public void onFailure(Call<ResponseProductDto> call, Throwable t) {
                    Toast.makeText(ProductDetail.this,"Failure",Toast.LENGTH_SHORT).show();
                }
            });
//
//        ListView listView= (ListView)findViewById(R.id.id_list);
//        CustomAdapter customAdapter=new CustomAdapter(ProductDetail.this,merchantid);
//        listView.setAdapter(customAdapter);


    }


   private void generateUserData(List<ApiProduct> userDataList) {
//        userDataList.add(new ApiProduct("Employee 1", "https://fortmyersradon.com/wp-content/uploads/2019/12/dummy-user-img-1.png", 10.0));
//        userDataList.add(new ApiProduct("Employee 2", "https://d2cax41o7ahm5l.cloudfront.net/mi/speaker-photo/appliedmicrobiology-minl-2018-Rucha-Ridhorkar-62007-7135.png", 100.0));
//        userDataList.add(new ApiProduct("Employee 3", "https://img.icons8.com/bubbles/2x/user-male.png", 23.98));
//        userDataList.add(new ApiProduct("Employee 4", "https://cdn4.iconfinder.com/data/icons/small-n-flat/24/user-alt-512.png", 76.9));
//        userDataList.add(new ApiProduct("Employee 5", "https://toppng.com/uploads/preview/user-font-awesome-nuevo-usuario-icono-11563566658mjtfvilgcs.png", 7665.0));
//        userDataList.add(new ApiProduct("Employee 6", "https://image.flaticon.com/icons/png/512/149/149071.png", 123.56));
//        userDataList.add(new ApiProduct("Employee 7", "https://www.winhelponline.com/blog/wp-content/uploads/2017/12/user.png", 141.00));
//        userDataList.add(new ApiProduct("Employee 8", "https://avatarfiles.alphacoders.com/791/79102.png", 1656.00));
//        userDataList.add(new ApiProduct("Employee 9", "https://i.pinimg.com/originals/7c/c7/a6/7cc7a630624d20f7797cb4c8e93c09c1.png", 181.0));
//        userDataList.add(new ApiProduct("Employee 10", "https://cdn1.iconfinder.com/data/icons/avatar-2-2/512/Casual_Man_2-512.png", 817.0));
    }

    @Override
    public void onUserClick(ApiProduct userDatamodel) {
        Intent intent=new Intent(ProductDetail.this,ProductDetail.class);
        intent.putExtra("imageUrl",userDatamodel.getImage());
        startActivity(intent);
    }

    class CustomAdapter extends ArrayAdapter<MerchantProductDetailDto>
    {

        private List<MerchantProductDetailDto> items;
        private Context context;
        private LayoutInflater vi;

        public CustomAdapter(@NonNull Context context, @NonNull List<MerchantProductDetailDto> items) {
            super(context, 0, items);
            this.context=context;
            this.items=items;
//            vi=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

//        @Override
//        public int getCount() {
//            return items.size();
//        }
//
//        @Override
//        public MerchantProductDetailDto getItem(int position) {
//            return items.get(position);
//        }
//
//        @Override
//        public long getItemId(int position) {
//            return 0;
//        }

        @Override
        public View getView(int i, View convertview, ViewGroup viewGroup) {
           // View v=convertview
//            if(items.get(i)!=null)
//            {
                try {
//                    vi=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//                    v = vi.inflate(R.layout.custom_listview, null);
                    convertview=getLayoutInflater().inflate(R.layout.custom_listview,null);
                    TextView textView_name = (TextView) findViewById(R.id.li_name);
                    TextView textView_price = (TextView) findViewById(R.id.li_price);

                    textView_name.setText(items.get(i).getMerchantId());
                    textView_price.setText((int) Math.round(items.get(i).getPrice())+"");
                }catch (Exception e){
                    System.out.println(e.getMessage());
                }


//            }
//            view=getLayoutInflater().inflate(R.layout.custom_listview,null);
//            TextView textView_name=(TextView)view.findViewById(R.id.li_name);
//            TextView textView_price=(TextView)view.findViewById(R.id.li_price);
//
//            textView_name.setText(items.get(i).getMerchantId());
//            textView_price.setText((int)items.get(i).getPrice());
            return convertview;
        }

    }
}