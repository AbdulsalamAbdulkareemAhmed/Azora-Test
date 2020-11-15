package com.salamy.azora.azoramap;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.salamy.azora.azoramap.Class.DatabaseHelper;
import com.salamy.azora.azoramap.Class.VolleySingleton;
import com.salamy.azora.azoramap.Model.NewsModel;
import com.salamy.azora.azoramap.dummy.DummyContent;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NewsFragment extends Fragment {

    public static final String URL_GetNews = "https://azora-news.000webhostapp.com/azora/getNews.php";
    CircularImageView circularImageView;
    private static final String ARG_COLUMN_COUNT = "column-count";
    private int mColumnCount = 1;
    DatabaseHelper databaseHelper;
    List<NewsModel> newsList = new ArrayList<NewsModel>();

    public NewsFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item_list, container, false);


        databaseHelper = new DatabaseHelper(getContext());

        LoadDataFromApi();

        try {
            newsList.clear();
            Cursor cursor = databaseHelper.getNews();
            if (cursor.moveToFirst()) {
                do {
                    NewsModel newsModel = new NewsModel(
                            0,
                            cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_NAME)),
                            cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_IMAGE)),
                            cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_DATE)),
                            cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_TITLE)),
                            cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_CONTENT))
                    );
                    newsList.add(newsModel);

                } while (cursor.moveToNext());
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            recyclerView.setAdapter(new NewsListAdapter(this, newsList));
        }
        return view;
    }

    //Method To Fetch Data From API Request And Store It In Local DB
    //Mybe In Future I will Be Develop More Actions
    private void LoadDataFromApi() {
        StringRequest request = new StringRequest(Request.Method.POST, URL_GetNews,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject object = null;
                        try {
                            object = new JSONObject(response);
                            JSONArray array = object.getJSONArray("info");
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject jsonObject = array.getJSONObject(i);
                                int id = jsonObject.getInt("id");
                                String name = jsonObject.getString("name");
                                String image = jsonObject.getString("image");
                                String date = jsonObject.getString("date");
                                String title = jsonObject.getString("title");
                                String content = jsonObject.getString("content");

                                NewsModel newsModel = new NewsModel(id, name, image, date, title, content);

                                //Save Data Into LocalDB With Id Primary
                                databaseHelper.insertNews(newsModel);
                            }

                        } catch (Exception exception) {
                           exception.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
               error.printStackTrace();
            }
        });

        //this is Class To Async Request
        VolleySingleton.getInstance(this.getContext()).addToRequestQueue(request);
    }
}