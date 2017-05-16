package com.example.season.easytolearn;

import android.content.ContentResolver;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.season.easytolearn.GetWork.GetWork;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Season on 2017/5/7.
 */

public class WorkFragment extends Fragment {

    private List<Work> workList = new ArrayList<>();
    private WorkAdapter adapter;
    private SwipeRefreshLayout swipeRefresh;
    private RecyclerView recyclerView;
    private String responseData;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        swipeRefresh = (SwipeRefreshLayout) getView().findViewById(R.id.swipe_refresh);
        swipeRefresh.setColorSchemeResources(R.color.colorPrimary);
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                sendRequestWithOkHttp();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        adapter.notifyDataSetChanged();
                        swipeRefresh.setRefreshing(false);
                    }
                }, 2000);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.work_fragment, container, false);

        sendRequestWithOkHttp();
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 1);
        recyclerView.setLayoutManager(layoutManager);
        Toast.makeText(getContext(),"Getting Work！！",Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //do something
                adapter = new WorkAdapter(workList);
                recyclerView.setAdapter(adapter);
            }
        }, 1000);    //延时1s执行
            return view;
    }

    private void sendRequestWithOkHttp() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder()
                            // 指定访问的服务器地址是电脑本机
                            .url("http://192.168.123.76/get_data.json")
                            .build();
                    Response response = client.newCall(request).execute();
                    responseData = response.body().string();
                    parseJSONWithGSON(responseData);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void parseJSONWithGSON(String jsonData) {

        workList.clear();
        Gson gson = new Gson();
        List<GetWork> getWorkList = gson.fromJson(jsonData, new TypeToken<List<GetWork>>() {}.getType());
        for (GetWork getWork : getWorkList) {
            workList.add(new Work(getWork.getName(),getWork.getImageId(),getWork.getContent(),getWork.getDeadLine()));
        }
    }
}
