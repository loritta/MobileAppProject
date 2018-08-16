package com.quizproject.jab.quizproject;

import android.content.Context;
import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class AsyncRestClientCalls {

    private static final String TAG = "REST_CALL";

    String BASE_URL = "https://opentdb.com/";
    JSONArray jsonResponse;
    AsyncHttpClient asyncHttpClient;
    RequestParams requestParams;
    Context context;
    OnCallCompleted callCompleteListener;

    // Class constructor, initialize the required objects
    public AsyncRestClientCalls(Context context, OnCallCompleted listener) {
        asyncHttpClient  = new AsyncHttpClient();
        requestParams = new RequestParams();
        this.context = context;
        this.callCompleteListener = listener;
    }

    public void getAllCategories() throws JSONException {

        AsyncRestClient.get("api_category.php", null, new JsonHttpResponseHandler() {
            // Called on success and when receiving a JSONObject response
            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    jsonResponse = response.getJSONArray("trivia_categories");
                    callCompleteListener.taskCompleted(jsonResponse);
                }
                catch (JSONException e) {
                    // handle the exception
                    e.printStackTrace();
                    return;
                }
                //Log.i(TAG, "onSuccess: " + jsonResponse);

            }

            // In case of a JSONArray response
//            @Override
//            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
//                super.onSuccess(statusCode, headers, response);
//                jsonResponse = response;
//                callCompleteListener.taskCompleted(jsonResponse);
//                Log.i(TAG, "onSuccess: " + jsonResponse);
//            }

            // On failure
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Log.e(TAG, "onFailure: " + errorResponse);
            }
        });

    }
}
