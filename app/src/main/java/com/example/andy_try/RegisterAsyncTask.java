package com.example.andy_try;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class RegisterAsyncTask extends AsyncTask<String, Void, String> {

    private static final String TAG = "RegisterAsyncTask";
    private RegisterCallback callback;

    public RegisterAsyncTask(RegisterCallback callback) {
        this.callback = callback;
    }

    @Override
    protected String doInBackground(String... params) {
        String apiUrl = "https://apip.trifrnd.com/mahesh/sample.php?apicall=signup";
        String username = params[0];
        String email = params[1];
        String gender = params[2];
        String mobile = params[3];
        String password = params[4];

        try {
            URL url = new URL(apiUrl);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

            urlConnection.setRequestMethod("POST");
            urlConnection.setDoOutput(true);

            OutputStream os = urlConnection.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));

            String data = "username=" + username +
                    "&email=" + email +
                    "&gender=" + gender +
                    "&mobile=" + mobile +
                    "&password=" + password;

            writer.write(data);
            writer.flush();
            writer.close();
            os.close();

            int responseCode = urlConnection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                InputStream is = urlConnection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();
                is.close();
                return response.toString();
            } else {
                return "Error: " + responseCode;
            }
        } catch (IOException e) {
            Log.e(TAG, "Error in registration: " + e.getMessage());
            return "Error: " + e.getMessage();
        }
    }

    @Override
    protected void onPostExecute(String result) {
        if (result.equals("User registered successfully")) {
            callback.onSuccess();
        } else {
            callback.onError(result);
        }
    }

    public interface RegisterCallback {
        void onSuccess();

        void onError(String errorMessage);
    }
}
