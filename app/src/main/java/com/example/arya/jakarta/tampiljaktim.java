package com.example.arya.jakarta;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.example.arya.jakarta.Adapter.WisataAdapter;
import com.example.arya.jakarta.api.ApiClient;
import com.example.arya.jakarta.api.ApiService;
import com.example.arya.jakarta.model.WisataItem;
import com.example.arya.jakarta.model.Wisataresponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class tampiljaktim extends AppCompatActivity {
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tampiljaktim);

        recyclerView = (RecyclerView) findViewById(R.id.jaktim);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        tampiljaktim();
    }

    public void tampiljaktim(){
        ApiService api = ApiClient.getService();
        Call<Wisataresponse> wisataresponseCall = api.tampil_jaktim();
        wisataresponseCall.enqueue(new Callback<Wisataresponse>() {
            @Override
            public void onResponse(Call<Wisataresponse> call, Response<Wisataresponse> response) {
                if (response.isSuccessful()){
                    Log.d("response api", response.body().toString());

                    List<WisataItem> data_jaktim = response.body().getWisata();
                    boolean status=response.body().isStatus();
                    if (status){
                        WisataAdapter adapter = new WisataAdapter(tampiljaktim.this, data_jaktim);
                        recyclerView.setAdapter(adapter);
                    } else {
                        Toast.makeText(tampiljaktim.this, "Tidak ada Wisata", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<Wisataresponse> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
}
