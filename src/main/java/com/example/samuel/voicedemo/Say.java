package com.example.samuel.voicedemo;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.util.HashMap;
import java.util.Map;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * A simple {@link Fragment} subclass.
 */
public class Say extends Fragment {

    private EditText NumberField,Word_Field;
    private static final String BASE_URL = "https://voice.africastalking.com/";
    private static final String TAG = "SayFragment";
    private ProgressDialog dialog;
    private Button button;

    public Say() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_say, container, false);
        dialog = new ProgressDialog(getActivity());
        NumberField = view.findViewById(R.id.number_field);
        Word_Field = view.findViewById(R.id.word_field);
        NumberField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(editable.length() >= 11 ){
                    Word_Field.requestFocus();
                }

            }
        });

        button = view.findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!NumberField.getText().toString().isEmpty()&& !Word_Field.getText().toString().isEmpty()){

                    String numberToCall = NumberField.getText().toString();
                    dialog.setTitle("Making call");
                    dialog.setMessage("Please wait while we make your call to " + numberToCall);
                    dialog.show();
                    dialog.setCanceledOnTouchOutside(false);
                    CallNumber(numberToCall.replaceFirst("0","+234"));
                    PostToMyApi(Word_Field.getText().toString());

                }

            }
        });
        return view ;

    }

    private void PostToMyApi(String s) {
        String myUrl = "https://db7b4cc2.ngrok.io/";
        Retrofit retrofit = new Retrofit.Builder().baseUrl(myUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        MakeCall makeCall = retrofit.create(MakeCall.class);
        Map<String,String> headers = new HashMap<>();
        headers.put("Accept","application/json");
        Call<ResponseBody> requestBodyCall = makeCall.sayWords(s);
        requestBodyCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.d(TAG, "onResponse: response is " + response.toString());
                dialog.dismiss();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.getMessage() );

            }
        });
    }

    private void CallNumber(String s) {
        Log.d(TAG, "CallNumber: Number to call " + s);
        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        String username = "ejiro4precious";
        String Number = "+23417006109";
        String apiKey   = "c888efe82543c002794e3d54935e6370cdada52209d72ed4447d5f8d9c3c17cd";


        MakeCall makeCall = retrofit.create(MakeCall.class);
        Map<String,String> headers = new HashMap<>();
        headers.put ("Content-Type","application/x-www-form-urlencoded");
        headers.put("Apikey",apiKey);
        headers.put("Accept","application/json");

        Call<RequestBody> responseCall = makeCall.makeCall(headers, username, Number, s);
        responseCall.enqueue(new Callback<RequestBody>() {
            @Override
            public void onResponse(Call<RequestBody> call, Response<RequestBody> response) {
                dialog.dismiss();
                Log.d(TAG, "onResponse: response gotten is " + response.toString());
            }

            @Override
            public void onFailure(Call<RequestBody> call, Throwable t) {
                dialog.dismiss();
            }
        });

    }


}
