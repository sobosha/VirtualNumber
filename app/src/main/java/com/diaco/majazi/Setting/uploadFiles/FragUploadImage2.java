package com.diaco.majazi.Setting.uploadFiles;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.diaco.majazi.MainActivity;
import com.diaco.majazi.Setting.CustomClasses.CustomFragment;
import com.diaco.majazi.Setting.ImageFilePath;
import com.diaco.majazi.Setting.Setting;
import com.diaco.majazi.Setting.mLocalData;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static android.app.Activity.RESULT_OK;


public class FragUploadImage2 extends CustomFragment {
    @Override
    public int layout() {
        return 0;
    }

    public static final int PICKFILE_RESULT_CODE = 1;


    private ImageView mAvatarImage;


    @Override
    public void onCreateMyView() {
//        mAvatarImage = parent.findViewById(R.id.imageView);
//
//
//        parent.findViewById(R.id.buttonUploadImage).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                Intent chooseFile = new Intent(Intent.ACTION_GET_CONTENT);
//                chooseFile.setType("*/*");
//                chooseFile = Intent.createChooser(chooseFile, "Choose a file");
//                startActivityForResult(chooseFile, PICKFILE_RESULT_CODE);
//
//            }
//        });


    }
    String realPath ;
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICKFILE_RESULT_CODE  && resultCode == RESULT_OK && data != null) {

            //getting the image Uri
            realPath = ImageFilePath.getPath(MainActivity.getGlobal(), data.getData());
            Uri imageUri = data.getData();
            try {
                //getting bitmap object from uri
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(MainActivity.getGlobal().getContentResolver(), imageUri);

                //displaying selected image to imageview
                mAvatarImage.setImageBitmap(bitmap);

                //calling the method uploadBitmap to upload image
             //   uploadBitmap(bitmap);
                saveProfileAccount();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    private void saveProfileAccount() {
        // loading or check internet connection or something...
        // ... then
     String url = "https://api.liom-app.ir/help/uploadPic";

        VolleyMultipartRequest multipartRequest = new VolleyMultipartRequest(Request.Method.POST, url, new Response.Listener<NetworkResponse>() {
            @Override
            public void onResponse(NetworkResponse response) {
                String resultResponse = new String(response.data);
                try {
                    JSONObject result = new JSONObject(resultResponse);
                              //  String message = result.getString("message");
                    Toast.makeText(getContext() , "sddds" ,Toast.LENGTH_LONG).show();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                NetworkResponse networkResponse = error.networkResponse;
                String errorMessage = "Unknown error";
                if (networkResponse == null) {
                    if (error.getClass().equals(TimeoutError.class)) {
                        errorMessage = "Request timeout";
                    } else if (error.getClass().equals(NoConnectionError.class)) {
                        errorMessage = "Failed to connect server";
                    }
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
                            errorMessage = message+" Please login again";
                        } else if (networkResponse.statusCode == 400) {
                            errorMessage = message+ " Check your inputs";
                        } else if (networkResponse.statusCode == 500) {
                            errorMessage = message+" Something is getting wrong";
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                Log.i("", errorMessage);
                Toast.makeText(getContext() , errorMessage ,Toast.LENGTH_LONG).show();
                error.printStackTrace();
            }
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
                String imageNAme = s[s.length-1];
              params.put("uploadPic", new DataPart(imageNAme, AppHelper.getFileDataFromDrawable(MainActivity.getGlobal(), mAvatarImage.getDrawable()), "image/png"));
           //     params.put("cover", new DataPart("file_cover.jpg", AppHelper.getFileDataFromDrawable(MainActivity.getGlobal(), mCoverImage.getDrawable()), "image/jpeg"));


                return params;
            }
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Authorization", "Bearer "+ mLocalData.getToken(MainActivity.getGlobal()));
             //   headers.put("Content-Type", "application/json");
                headers.put("Version", Setting.getVersionName());
                return headers;
            }





    };

        VolleySingleton.getInstance(MainActivity.getGlobal()).addToRequestQueue(multipartRequest);
    }
}