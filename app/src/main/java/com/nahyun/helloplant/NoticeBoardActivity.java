package com.nahyun.helloplant;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.nahyun.helloplant.MainActivity.email;

public class NoticeBoardActivity extends BottomNavigationActivity {

    public static ArrayList<NoticeBoardData> nb_arrayList;
    private NoticeBoardAdapter noticeBoardAdapter;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    public String current_page = "1";
    public String max_page = "1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice_board);

        BottomNavigationView navigation_add = (BottomNavigationView)findViewById(R.id.navigation);
        navigation_add.setOnItemSelectedListener(this);
        navigation_add.setSelectedItemId(R.id.action_talk);
        navigation_add.setOnItemSelectedListener(new BottomNavigationView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_camera:
                        Intent NB_intent_camera = new Intent(NoticeBoardActivity.this, searchPlant.class);
                        //NB_intent_camera.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(NB_intent_camera);
                        overridePendingTransition(0,0);
                        break;
                    case R.id.action_home:
                        Intent NB_intent_home = new Intent(NoticeBoardActivity.this, MyplantListActivity.class);
                        //NB_intent_home.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(NB_intent_home);
                        overridePendingTransition(0,0);
                        break;
                    case R.id.action_talk:
                        break;
                }
                return false;
            }
        });

        recyclerView = (RecyclerView)findViewById(R.id.noticeboard_RecyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));

        nb_arrayList = new ArrayList<>();
        noticeBoardAdapter = new NoticeBoardAdapter(nb_arrayList);
        noticeBoardAdapter.setOnItemClickListener(new NoticeBoardAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                String p = Integer.toString(position);
                //Toast.makeText(NoticeBoardActivity.this, p, Toast.LENGTH_SHORT).show();
                //여기서 Toast를 삭제하고 startActivity를 실행, activity 이름은 position기반

                //Bitmap image, String name
                NoticeBoardData noticeBoardData_clicked = null;
                noticeBoardData_clicked = nb_arrayList.get(Integer.parseInt(p));
                Bitmap clicked_image = noticeBoardData_clicked.getNoticeboard_image();
                String clicked_name = noticeBoardData_clicked.getNoticeboard_name();

                String clicked_family_name = "";
                if (!noticeBoardData_clicked.getNoticeboard_family_name().equals("")) { clicked_family_name = noticeBoardData_clicked.getNoticeboard_family_name(); }

                String clicked_korean_name = "";
                if (!noticeBoardData_clicked.getNoticeboard_korean_name().equals("")) { clicked_korean_name = noticeBoardData_clicked.getNoticeboard_korean_name(); }

                String clicked_water_cycle = "";
                if (!noticeBoardData_clicked.getNoticeboard_water_cycle().equals("")) { clicked_water_cycle = noticeBoardData_clicked.getNoticeboard_water_cycle(); }

                String clicked_height = "";
                if (!noticeBoardData_clicked.getNoticeboard_height().equals("")) { clicked_height = noticeBoardData_clicked.getNoticeboard_height(); }

                String clicked_place = "";
                if (!noticeBoardData_clicked.getNoticeboard_place().equals("")) { clicked_place = noticeBoardData_clicked.getNoticeboard_place(); }

                String clicked_smell = "";
                if (!noticeBoardData_clicked.getNoticeboard_smell().equals("")) { clicked_smell = noticeBoardData_clicked.getNoticeboard_smell(); }

                String clicked_growth_speed = "";
                if (!noticeBoardData_clicked.getNoticeboard_growth_speed().equals("")) { clicked_growth_speed = noticeBoardData_clicked.getNoticeboard_growth_speed(); }

                String clicked_proper_temperature = "";
                if (!noticeBoardData_clicked.getNoticeboard_proper_temperature().equals("")) { clicked_proper_temperature = noticeBoardData_clicked.getNoticeboard_proper_temperature(); }

                String clicked_pest = "";
                if (!noticeBoardData_clicked.getNoticeboard_pest().equals("")) { clicked_pest = noticeBoardData_clicked.getNoticeboard_pest(); }

                String clicked_manage_level = "";
                if (!noticeBoardData_clicked.getNoticeboard_manage_level().equals("")) { clicked_manage_level = noticeBoardData_clicked.getNoticeboard_manage_level(); }

                String clicked_light = "";
                if (!noticeBoardData_clicked.getNoticeboard_light().equals("")) { clicked_light = noticeBoardData_clicked.getNoticeboard_light(); }


                ByteArrayOutputStream stream_clicked = new ByteArrayOutputStream();
                clicked_image.compress(Bitmap.CompressFormat.JPEG, 100, stream_clicked);
                byte[] byteArray_clicked = stream_clicked.toByteArray();

                Intent intent_goto_saveinformation = new Intent(NoticeBoardActivity.this, SaveInformationActivity.class);
                intent_goto_saveinformation.putExtra("image", byteArray_clicked);
                intent_goto_saveinformation.putExtra("scientific_name", clicked_name);
                intent_goto_saveinformation.putExtra("family_name", clicked_family_name);
                intent_goto_saveinformation.putExtra("korean_name", clicked_korean_name);
                intent_goto_saveinformation.putExtra("water_cycle", clicked_water_cycle);
                intent_goto_saveinformation.putExtra("height", clicked_height);
                intent_goto_saveinformation.putExtra("place", clicked_place);
                intent_goto_saveinformation.putExtra("smell", clicked_smell);
                intent_goto_saveinformation.putExtra("growth_speed", clicked_growth_speed);
                intent_goto_saveinformation.putExtra("proper_temperature", clicked_proper_temperature);
                intent_goto_saveinformation.putExtra("pest", clicked_pest);
                intent_goto_saveinformation.putExtra("manage_level", clicked_manage_level);
                intent_goto_saveinformation.putExtra("light", clicked_light);

                startActivity(intent_goto_saveinformation);
            }
        });
        recyclerView.setAdapter(noticeBoardAdapter);

        //=====recyclerView page maker=====//

        findViewById(R.id.noticeboard_before_page_Button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (current_page.equals("1")) {
                    Toast.makeText(NoticeBoardActivity.this, "현재 첫 페이지 입니다.", Toast.LENGTH_SHORT).show();
                }
                else {
                    current_page = Integer.toString(Integer.parseInt(current_page)-1);

                    TextView noticeboard_current_page_TextView = (TextView)findViewById(R.id.noticeboard_current_page_TextView);
                    noticeboard_current_page_TextView.setText(current_page);

                    System.out.println("Before Button is clicked, current page is : " + current_page );
                    nb_arrayList = new ArrayList<>();
                    NoticeBoard_get();
                }
            }
        });

        findViewById(R.id.noticeboard_next_page_Button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (current_page.equals(max_page)) {
                    Toast.makeText(NoticeBoardActivity.this, "현재 마지막 페이지입니다.", Toast.LENGTH_SHORT).show();
                }
                else {
                    current_page = Integer.toString(Integer.parseInt(current_page)+1);

                    TextView noticeboard_current_page_TextView = (TextView)findViewById(R.id.noticeboard_current_page_TextView);
                    noticeboard_current_page_TextView.setText(current_page);

                    System.out.println("Next Button is clicked, current page is : " + current_page );
                    nb_arrayList = new ArrayList<>();
                    NoticeBoard_get();
                }
            }
        });

        NoticeBoard_get();

        TextView noticeboard_total_page_TextView = (TextView)findViewById(R.id.noticeboard_total_page_TextView);
        noticeboard_total_page_TextView.setText(max_page);

        TextView noticeboard_current_page_TextView = (TextView)findViewById(R.id.noticeboard_current_page_TextView);
        noticeboard_current_page_TextView.setText(current_page);
    }

    @Override
    int getContentViewId() {
        return R.layout.activity_notice_board;
    }

    @Override
    int getNavigationMenuItemId() {
        return R.id.action_talk;
    }

    public void NoticeBoard_get() {
        //====notice board server connection code ======//
        SharedPreferences sharedPreferences = getSharedPreferences("login token", MODE_PRIVATE);
        String token = sharedPreferences.getString("accessToken", "");
        System.out.println("Myplant list token = " + token);

        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(new Interceptor() {
            @Override
            public okhttp3.@NotNull Response intercept(@NotNull Chain chain) throws IOException {
                Request newRequest = chain.request().newBuilder()
                        .addHeader("Authorization", "Bearer " + token).build();
                return chain.proceed(newRequest);
            }
        }).build();

        retrofit2.Retrofit retrofit = new retrofit2.Retrofit.Builder()
                .client(client)
                .baseUrl("http://3.12.148.142/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RetrofitInterface service = retrofit.create(RetrofitInterface.class);

        Call<Retrofit_infoplant_GetData> call_infoplant_get = service.get_infoplant_Func(current_page);
        System.out.println("infoplant page = " + current_page);

        final ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(NoticeBoardActivity.this);
        progressDialog.setMax(100);
        progressDialog.setMessage("잠시만 기다려주세요..!");
        progressDialog.setProgressStyle(android.R.style.Widget_ProgressBar_Horizontal);

        progressDialog.show();

        call_infoplant_get.enqueue(new Callback<Retrofit_infoplant_GetData>() {
            @Override
            public void onResponse(Call<Retrofit_infoplant_GetData> call, Response<Retrofit_infoplant_GetData> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()) {
                    response.body();
                    String message = response.body().getMessage();
                    String after_current_page = response.body().getPage().toString();
                    max_page = response.body().getMaxPage().toString();
                    System.out.println("NoticeBoardActivity response message : " + message);

                    List<InfoPlant> plantList = response.body().getInfoPlantList();
                    int list_count = plantList.size();
                    System.out.println("notice board list count of plants = " +list_count);

                    for (InfoPlant plant: plantList ) {
                        NoticeBoardData nbd;
                        String after_id = plant.getId();
                        String after_image = plant.getImage();
                        String after_scientific_name = plant.getScientificName();
                        List<String> after_necessary = plant.getNecessary();

                        String after_family_name = ""; //1
                        if (!after_necessary.contains("family_name")) {after_family_name = plant.getFamilyName();}

                        String after_korean_name = ""; //1
                        if (!after_necessary.contains("korean_name")) {after_korean_name = plant.getKoreanName();}

                        String after_water_cycle = ""; //2
                        if (!after_necessary.contains("water_cycle")) {after_water_cycle = plant.getWaterCycle();}

                        String after_height = "";      //3
                        if (!after_necessary.contains("height")) {after_height = plant.getHeight();}

                        String after_place = "";       //4
                        if (!after_necessary.contains("place")) {after_place = plant.getPlace();}

                        String after_smell = "";       //5
                        if (!after_necessary.contains("smell")) {after_smell = plant.getSmell();}

                        String after_growth_speed = ""; //6
                        if (!after_necessary.contains("growth_speed")) {after_growth_speed = plant.getGrowthSpeed();}

                        String after_proper_temperature = ""; //7
                        if (!after_necessary.contains("proper_temperature")) {after_proper_temperature = plant.getProperTemperature();}

                        String after_pest = "";        //8
                        if (!after_necessary.contains("pest")) {after_pest = plant.getPest();}

                        String after_manage_level = ""; //9
                        if (!after_necessary.contains("manage_level")) {after_manage_level = plant.getManageLevel();}

                        String after_light = "";         //10
                        if (!after_necessary.contains("light")) {after_light = plant.getLight();}

                        Bitmap after_image_bitmap = null;
                        try {
                            byte[] byte_array_image = Base64.decode(after_image, Base64.DEFAULT);
                            after_image_bitmap = BitmapFactory.decodeByteArray(byte_array_image, 0, byte_array_image.length);}
                        catch (Exception e) {
                            e.printStackTrace();
                        }

                        nbd = new NoticeBoardData(after_image_bitmap, after_scientific_name, after_family_name, after_korean_name, after_water_cycle, after_height, after_place, after_smell, after_growth_speed,
                                after_proper_temperature, after_pest, after_manage_level, after_light);

                        add_arraylist(nbd);

                        TextView noticeboard_total_page_TextView = (TextView)findViewById(R.id.noticeboard_total_page_TextView);
                        noticeboard_total_page_TextView.setText(max_page);

                        System.out.println("goooood!!! \nafter_id = " + after_id
                                + " \nafter_scientific_name = " + after_scientific_name
                                + " \nafter_necessary = " + after_necessary
                                + " \ncurrent page = " + after_current_page
                                + " \nmax page = " + max_page);
                    }

                    Log.v("NoticeBoardActivity", "code = " + String.valueOf(response.code()));

                    noticeBoardAdapter.updateNoticeBoardItems(nb_arrayList);
                    System.out.println("update " + nb_arrayList);
                }
                else {
                    Log.v("MyplantListActivity", "error = " + String.valueOf(response.code()));
                    Toast.makeText(NoticeBoardActivity.this, "내 식물 등록에 실패했습니다.", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<Retrofit_infoplant_GetData> call, Throwable t) {
                progressDialog.dismiss();
                Log.v("MyplantListActivity", "Fail");
                Toast.makeText(NoticeBoardActivity.this, "응답에 실패했습니다.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static void add_arraylist(NoticeBoardData noticeBoardData) {
        nb_arrayList.add(noticeBoardData);
        System.out.println("nb_arrayList subfunc : " + nb_arrayList.size());
        System.out.println("NoticeBoardData = " + noticeBoardData);
    }
}