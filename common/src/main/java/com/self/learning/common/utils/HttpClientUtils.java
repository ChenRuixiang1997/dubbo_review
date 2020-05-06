package com.self.learning.common.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by boot on 2020/4/6.
 */
public class HttpClientUtils {
    /**
     * 发送get请求
     *
     * @param str
     * @return
     * @throws IOException
     */
    public static String doGet(String str) throws IOException {
        try {
            URL url = new URL(str);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
            InputStream inputStream = urlConnection.getInputStream();
            String responseStr = ConvertToString(inputStream);
            return responseStr;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }

    /**
     * @todo: 将inputStream转换为字符串
     * @param inputStream
     * @return
     */
    public static String ConvertToString(InputStream inputStream) {
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        StringBuilder result = new StringBuilder();
        String line = "";
        try {
            while ((line = bufferedReader.readLine()) != null) {
                result.append(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                inputStreamReader.close();
                inputStream.close();
                bufferedReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return result.toString();
    }
}
