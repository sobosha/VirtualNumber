package com.diaco.majazi.Core;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.HttpStack;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.diaco.majazi.MainActivity;
import com.diaco.majazi.Setting.Setting;
import com.diaco.majazi.Setting.TLSSocketFactory;
import com.diaco.majazi.Setting.mLocalData;
import com.diaco.majazi.Setting.nValue;
import com.diaco.majazi.Setting.uploadFiles.AppHelper;
import com.diaco.majazi.Setting.uploadFiles.VolleyMultipartRequest;
import com.diaco.majazi.Setting.uploadFiles.VolleySingleton;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Presenter {


    private static Presenter _global;
    RequestQueue QUEUE;
    String URL;
    String SERVER_KEY = "Bearer "+ mLocalData.getToken(MainActivity.getGlobal());

    public Presenter() {
        _global = this;
    }

    public static Presenter get_global() {
        if (_global != null)
            return _global;
        else
            return new Presenter();
    }

    public void OnCreate(Context context, String url, String token) {
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.KITKAT) {
            HttpStack stack = null;
            try {
                stack = new HurlStack(null, new TLSSocketFactory());
            } catch (KeyManagementException e) {
                e.printStackTrace();
                stack = new HurlStack();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
                stack = new HurlStack();
            }
            QUEUE = Volley.newRequestQueue(context, stack);
        } else {
            QUEUE = Volley.newRequestQueue(context);
        }
        URL = url;
        SERVER_KEY = token;
    }


    public <T, U> void PostAction(final IView RelMaster, String controller, String action, String id, U requestModel, final Class<T> responseType) {
        String strJson;

        Gson gson = new Gson();
        try {
            strJson = gson.toJson(requestModel);
        } catch (Exception e) {
            e.printStackTrace();
            RelMaster.OnError("خطا در تشخیص نوع ورودی", 505);
            return;
        }

        if (!id.isEmpty())
            action = action + "/" + id;

        final String actionStr = action;
        JsonRequest jsObjRequest = new JsonRequest<String>(Request.Method.POST, "https://diacoipj.com/shomarehMajazi/"+controller+action, strJson, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson1 = new Gson();

                try {
                    T responseModel = gson1.fromJson(response, responseType);
                    RelMaster.OnSucceed(responseModel);


                } catch (Exception e) {
                    e.printStackTrace();
                    RelMaster.OnError("خطا در تشخیص داده دریافتی", 506);

                }


            }

        }, new Response.ErrorListener() {

            @Override

            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                try {

                    RelMaster.OnError("خطا در برقراری اتصال ، اینترنت خود را بررسی کنید", error.networkResponse.statusCode);
                } catch (Exception e) {
                    e.printStackTrace();
                    RelMaster.OnError("خطا در برقراری اتصال ، اینترنت خود را بررسی کنید", 0);

                }

            }


        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Authorization", "Bearer "+mLocalData.getToken(MainActivity.getGlobal()));
                return headers;
            }

            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                String jsonString = null;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    jsonString = new String(response.data, StandardCharsets.UTF_8);
                } else
                    jsonString = new String(response.data);
                return Response.success(jsonString, HttpHeaderParser.parseCacheHeaders(response));
            }
        };

        jsObjRequest.setTag("cancle");
        QUEUE.add(jsObjRequest);

        jsObjRequest.setRetryPolicy(new DefaultRetryPolicy(15000, 0, 1f));
    }

    public <T, U> void PostVirtualNumber(final IView<T> RelMaster, String action , final Class<T> responseType) {
        StringRequest jsObjRequest = new StringRequest(Request.Method.GET, "https://diacoipj.com/shomarehMajazi/api.php?action=YY", new Response.Listener<String>() {

            Gson gson = new Gson();
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(String response) {

                try {
                    T responseModel = gson.fromJson(response ,responseType);
                    RelMaster.OnSucceed(responseModel);
                } catch (Exception e) {
                    e.printStackTrace();
                    RelMaster.OnError("خطا در تشخیص داده دریافتی", 506);

                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                try {
                    RelMaster.OnError("خطا در برقراری اتصال ، اینترنت خود را بررسی کنید", error.networkResponse.statusCode);
                } catch (Exception e) {
                    e.printStackTrace();
                    RelMaster.OnError("خطا در برقراری اتصال ، اینترنت خود را بررسی کنید", 0);

                }
            }


        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Authorization", "Bearer "+ "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpZCI6IjMzNTgxMTY3In0.CYlGdzLplVHBh4vINHHyJFJNfPiheLNeCkZETsK304c");
                headers.put("Content-Type", "application/json");
                headers.put("Version", Setting.getVersionName());
                headers.put("Liomid", "0");
                return headers;
            }
        };

        jsObjRequest.setTag("cancle");
        QUEUE.add(jsObjRequest);
        jsObjRequest.setRetryPolicy(new DefaultRetryPolicy(15000, 0, 1f));
    }

    public <T, U> void PostIncreaseCost(final IView<T> RelMaster, String action , final Class<T> responseType) {
        StringRequest jsObjRequest = new StringRequest(Request.Method.GET, action, new Response.Listener<String>() {

            Gson gson = new Gson();
            @Override
            public void onResponse(String response) {

                try {
                    T responseModel = gson.fromJson(response ,responseType);
                    RelMaster.OnSucceed(responseModel);
                } catch (Exception e) {
                    e.printStackTrace();
                    RelMaster.OnError("خطا در تشخیص داده دریافتی", 506);

                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                try {
                    RelMaster.OnError("خطا در برقراری اتصال ، اینترنت خود را بررسی کنید", error.networkResponse.statusCode);
                } catch (Exception e) {
                    e.printStackTrace();
                    RelMaster.OnError("خطا در برقراری اتصال ، اینترنت خود را بررسی کنید", 0);

                }
            }


        }) {
        };

        jsObjRequest.setTag("cancle");
        QUEUE.add(jsObjRequest);
        jsObjRequest.setRetryPolicy(new DefaultRetryPolicy(15000, 0, 1f));
    }

    public<T> void PostFileAction(final IView RelMaster , String controller, String action , String realPath , Bitmap mAvatarImage, final Class<T> responseType) {
        String url = "https://api.liom-app.ir/" + controller + "/" + action;
        VolleyMultipartRequest multipartRequest = new VolleyMultipartRequest(Request.Method.POST, url, new Response.Listener<NetworkResponse>() {
            @Override
            public void onResponse(NetworkResponse response) {
                String resultResponse = new String(response.data);
                Gson gson1 = new Gson();
                try {
                    T responseModel = gson1.fromJson(resultResponse, responseType);
                    RelMaster.OnSucceed(responseModel);


                } catch (Exception e) {
                    e.printStackTrace();
                    RelMaster.OnError("خطا در تشخیص داده دریافتی", 506);

                }
            }
        }, error -> {
            int statusCode = 0;
            NetworkResponse networkResponse = error.networkResponse;
            String errorMessage = "Unknown error";
            if (networkResponse == null) {
                if (error.getClass().equals(TimeoutError.class)) {
                    errorMessage = "Request timeout";
                } else if (error.getClass().equals(NoConnectionError.class)) {
                    errorMessage = "Failed to connect server";
                }
                statusCode = 0 ;
            } else {
                String result = new String(networkResponse.data);
                try {
                    JSONObject response = new JSONObject(result);
                    String status = response.getString("status");
                    String message = response.getString("message");

                    Log.e("Error Status", status);
                    Log.e("Error Message", message);

                    if (networkResponse.statusCode == 404) {
                        errorMessage = "Resource not found";
                    } else if (networkResponse.statusCode == 401) {
                        errorMessage = message + " Please login again";
                    } else if (networkResponse.statusCode == 400) {
                        errorMessage = message + " Check your inputs";
                    } else if (networkResponse.statusCode == 500) {
                        errorMessage = message + " Something is getting wrong";
                    }
                    statusCode = networkResponse.statusCode;
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            Log.i("", errorMessage);
            RelMaster.OnError(errorMessage , statusCode);
            error.printStackTrace();
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                //      params.put("Content-Type", "application/json");

                return params;
            }

            @Override
            protected Map<String, DataPart> getByteData() {
                Map<String, DataPart> params = new HashMap<>();
                String[] s = realPath.split("/");
                String imageNAme = s[s.length - 1];
                String format ;
                if(imageNAme.endsWith("png")) {
                    format = "png";
                } else
                    format = "jpg";
                params.put("uploadPic", new DataPart(imageNAme, AppHelper.getFileDataFromDrawable(mAvatarImage), "image/"+format));
                //     params.put("cover", new DataPart("file_cover.jpg", AppHelper.getFileDataFromDrawable(MainActivity.getGlobal(), mCoverImage.getDrawable()), "image/jpeg"));
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Authorization", "Bearer " + mLocalData.getToken(MainActivity.getGlobal()));
                //   headers.put("Content-Type", "application/json");
                headers.put("Version", Setting.getVersionName());
                headers.put("Liomid", "0");
                return headers;
            }
        };
        VolleySingleton.getInstance(MainActivity.getGlobal()).addToRequestQueue(multipartRequest);
    }


    public <T> void GetAction(final IView<T> RelMaster, final String controller, String action, String id, final Class<T> responseType) {

        if (!id.isEmpty())
            action = action + "/" + id;

        final String actionStr = action;
        StringRequest jsObjRequest = new StringRequest(Request.Method.GET,  "https://diacoipj.com/shomarehMajazi/api.php?action=register", new Response.Listener<String>() {

            Gson gson = new Gson();
            @Override
            public void onResponse(String response) {

                try {

                    T responseModel = gson.fromJson(response ,responseType);
                    RelMaster.OnSucceed(responseModel);
                } catch (Exception e) {
                    e.printStackTrace();
                    RelMaster.OnError("خطا در تشخیص داده دریافتی", 506);

                }
            }
        }, new Response.ErrorListener() {

            @Override

            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                try {

                    RelMaster.OnError("خطا در برقراری اتصال ، اینترنت خود را بررسی کنید", error.networkResponse.statusCode);
                } catch (Exception e) {
                    e.printStackTrace();
                    RelMaster.OnError("خطا در برقراری اتصال ، اینترنت خود را بررسی کنید", 0);

                }
            }


        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                /*headers.put("Authorization", "Bearer "+mLocalData.getToken(MainActivity.getGlobal()));
                headers.put("Content-Type", "application/json");
                headers.put("Version", Setting.getVersionName());
                headers.put("Liomid", "0");*/
                return headers;
            }
        };

        jsObjRequest.setTag("cancle");
        QUEUE.add(jsObjRequest);
        jsObjRequest.setRetryPolicy(new DefaultRetryPolicy(15000, 0, 1f));
    }

    public String getSERVER_KEY() {
        return SERVER_KEY;
    }

    public void setSERVER_KEY(String SERVER_KEY) {
        this.SERVER_KEY = SERVER_KEY;
    }


    public  void cancelReq(){
       if(QUEUE!=null)
           QUEUE.cancelAll("cancle");
    }



}
