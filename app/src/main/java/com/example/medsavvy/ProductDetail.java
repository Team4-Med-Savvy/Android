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
import android.widget.AdapterView;
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
    int count=1;
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
            init();        });

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
        requestCartDto.setMerchantId("61f4cb27d9f1054bc09615b7");
        requestCartDto.setPrice(new Long(100));

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

                    List<MerchantProductDetailDto> merchantProductDetailDtos=response.body().getMerchantProductDetailDtos();
                    Spinner spinnerMerchantList = findViewById(R.id.merchant_list);
                    List<String> merchants=new ArrayList<>();

                    for (int i=0; i<merchantProductDetailDtos.size(); i++){
                        MerchantProductDetailDto merchantDto = merchantProductDetailDtos.get(i);
                        String merchantSpinnerDetails = "Merchant ID: "+merchantDto.getMerchantId()+",     Price Offered: "+merchantDto.getPrice();
                        merchants.add(merchantSpinnerDetails);
                    }

                    ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<String>(ProductDetail.this, android.R.layout.simple_spinner_dropdown_item, merchants);
                    spinnerMerchantList.setAdapter(stringArrayAdapter);

//                    tvProductNameDetails.setText(response.body().getProductName());
//                    tvProductDetailsDescription.setText(response.body().getDescription());
//                    tvProductDetailsProductPrice.setText(response.body().getPrice()+"");
//                    Glide.with(ivProductDetailsImage).load(response.body().getImage()).placeholder(R.drawable.logo_afa).into(ivProductDetailsImage);

                    spinnerMerchantList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        }
                    });

                    spinnerMerchantList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                            tvProductDetailsDescription.setText(merchants.get(i).);
//                            tvProductDetailsProductPrice.setText(merchants.get(i).getPrice()+"");
//                            intentMerchantId = merchantList.get(i).getMerchantId();
//                            currentMerchantQuantity = me.get(i).getQuantity();
//                            Log.d("soham",intentMerchantId+"");
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });
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



    @Override
    public void onUserClick(ApiProduct userDatamodel) {
        Intent intent=new Intent(ProductDetail.this,ProductDetail.class);
        intent.putExtra("imageUrl",userDatamodel.getImage());
        startActivity(intent);
    }

    class CustomAdapter extends ArrayAdapter<MerchantProductDetailDto>
    {


        public CustomAdapter(@NonNull Context context, @NonNull List<MerchantProductDetailDto> items) {
            super(context, 0, items);
//            this.context=context;
//            this.items=items;
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
            return convertview;
        }

    }
}