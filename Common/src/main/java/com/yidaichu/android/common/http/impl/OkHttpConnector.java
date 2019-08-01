package com.yidaichu.android.common.http.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.yidaichu.android.common.http.ActivityFragmentActive;
import com.yidaichu.android.common.http.HttpConnectCallback;
import com.yidaichu.android.common.http.HttpConnector;
import com.yidaichu.android.common.http.HttpRequestEntry;
import com.yidaichu.android.common.http.HttpResponseEntry;
import com.yidaichu.android.common.http.HttpSession;
import com.yidaichu.android.common.http.StatusCode;
import com.yidaichu.android.common.http.error.ErrorCode;
import com.yidaichu.android.common.http.error.HttpError;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by wangdd on 16-11-26.
 */

public class OkHttpConnector implements HttpConnector {

    private OkHttpClient mHttpClient;

    public OkHttpConnector() {
        mHttpClient = new OkHttpClient();
    }

    @Override
    public HttpSession sendHttpRequest(final ActivityFragmentActive host, final HttpRequestEntry requestEntry, final Class clazz, final HttpConnectCallback callback) {
        Request request = buildHttpRequest(requestEntry);
        Call call = mHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if (!host.isActive()) return;
                HttpError error = new HttpError(ErrorCode.NETWORK_ERROR, e.getMessage());
                callback.onRequestFailure(error);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!host.isActive()) return;
                Map<String, String> headers = new HashMap<>();
                Headers okHeaders = response.headers();
                Set<String> headerNames = okHeaders.names();
                for (String headerName : headerNames) {
                    headers.put(headerName, headers.get(headerName));
                }
                if (response.isSuccessful()) {
                    String text = response.body().string();
                    handleResponse(headers, text, clazz, callback);
                } else {
                    HttpError error = new HttpError(ErrorCode.SERVER_ERROR, null);
                    callback.onRequestFailure(error);
                }
            }
        });
        HttpSession session = new OkHttpSession(call, requestEntry);
        return session;
    }

    @Override
    public HttpResponseEntry sendSyncHttpRequest(ActivityFragmentActive host, HttpRequestEntry requestEntry, Class clazz) throws HttpError {
        Request request = buildHttpRequest(requestEntry);
        Call call = mHttpClient.newCall(request);
        try {
            Response response = call.execute();
            Map<String, String> headers = new HashMap<>();
            Headers okHeaders = response.headers();
            Set<String> headerNames = okHeaders.names();
            for (String headerName : headerNames) {
                headers.put(headerName, headers.get(headerName));
            }
            if (response.isSuccessful()) {
                String text = response.body().string();
                JSONObject json = JSONObject.parseObject(text);
                int status = json.getInteger("code");
                if (status == 0) {//请求成功
                    HttpResponseEntry responseEntry = new HttpResponseEntry();
                    responseEntry.addResponseHeaders(headers);
                    responseEntry.setStatusCode(StatusCode.HTTP_OK);

                    Object segment = json.get("data");
                    Object data;
                    if (segment instanceof JSONArray) {//是json数组
                        JSONArray array = (JSONArray) segment;
                        List<Object> list = new ArrayList<>();
                        for (int i = 0; i < array.size(); i++) {
                            list.add(JSONObject.parseObject(array.getString(i), clazz));
                        }
                        data = list;
                    } else {//是json对象
                        data = JSON.parseObject(segment.toString(), clazz);
                    }
                    responseEntry.setData(data);
                    return responseEntry;
                } else {//请求失败
                    HttpError error = new HttpError(status, json.getString("msg"));
                    throw error;
                }
            } else {
                HttpError error = new HttpError(ErrorCode.SERVER_ERROR, null);
                throw error;
            }
        } catch (IOException e) {
            e.printStackTrace();
            HttpError error = new HttpError(ErrorCode.NETWORK_ERROR, e.getMessage());
            throw error;
        }
    }

    @Override
    public HttpSession sendHttpRequest(final ActivityFragmentActive host, final HttpRequestEntry requestEntry, final HttpConnectCallback callback) {
        Request request = buildHttpRequest(requestEntry);
        Call call = mHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if (!host.isActive()) return;
                HttpError error = new HttpError(ErrorCode.NETWORK_ERROR, e.getMessage());
                callback.onRequestFailure(error);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!host.isActive()) return;
                Map<String, String> headers = new HashMap<>();
                Headers okHeaders = response.headers();
                Set<String> headerNames = okHeaders.names();
                for (String headerName : headerNames) {
                    headers.put(headerName, headers.get(headerName));
                }
                if (response.isSuccessful()) {
                    String text = response.body().string();
                    handleResponse(headers, text, callback);
                } else {
                    HttpError error = new HttpError(ErrorCode.SERVER_ERROR, null);
                    callback.onRequestFailure(error);
                }
            }
        });
        HttpSession session = new OkHttpSession(call, requestEntry);
        return session;
    }

    @Override
    public HttpResponseEntry sendSyncHttpRequest(final ActivityFragmentActive host, final HttpRequestEntry requestEntry) throws HttpError {
        Request request = buildHttpRequest(requestEntry);
        Call call = mHttpClient.newCall(request);
        try {
            Response response = call.execute();
            Map<String, String> headers = new HashMap<>();
            Headers okHeaders = response.headers();
            Set<String> headerNames = okHeaders.names();
            for (String headerName : headerNames) {
                headers.put(headerName, headers.get(headerName));
            }
            if (response.isSuccessful()) {
                String text = response.body().string();
                JSONObject json = JSONObject.parseObject(text);
                int status = json.getInteger("code");
                if (status == 0) {//请求成功
                    String data = json.getString("data");
                    HttpResponseEntry responseEntry = new HttpResponseEntry();
                    responseEntry.addResponseHeaders(headers);
                    responseEntry.setStatusCode(StatusCode.HTTP_OK);
                    responseEntry.setData(data);
                    return responseEntry;
                } else {//请求失败
                    HttpError error = new HttpError(status, json.getString("msg"));
                    throw error;
                }
            } else {
                HttpError error = new HttpError(ErrorCode.SERVER_ERROR, null);
                throw error;
            }
        } catch (IOException e) {
            e.printStackTrace();
            HttpError error = new HttpError(ErrorCode.NETWORK_ERROR, e.getMessage());
            throw error;
        }
    }

    private Request buildHttpRequest(HttpRequestEntry requestEntry) {
        Request.Builder builder = new Request.Builder();
        Request request = null;
        if (requestEntry.getMethod() == HttpRequestEntry.Method.GET) {
            String realUrl = generateGetUrl(requestEntry.getUrl(), requestEntry.getRequestParams());
            request = builder.url(realUrl).build();
        } else {
            builder.url(requestEntry.getUrl());
            MediaType mediaType;
            Map<String , Object> params;
            File file;
            switch (requestEntry.getFormat()) {
                case JSON:
                    mediaType = MediaType.parse("text/json; charset=utf-8");
                    String jsonStr = generatePostJson(requestEntry.getRequestParams());
                    request = builder.post(RequestBody.create(mediaType, jsonStr))
                            .build();
                    break;
                case FORM:
                    FormBody.Builder formBuilder = new FormBody.Builder();
                    params = requestEntry.getRequestParams();
                    if (params != null && !params.isEmpty()) {
                        Set<String> set = params.keySet();
                        for (String key : set) {
                            formBuilder.add(key, params.get(key).toString());
                        }
                        RequestBody requestBody = formBuilder.build();
                        builder.post(requestBody);
                    }
                    request = builder.build();
                    break;
                case FILE:
                    mediaType = MediaType.parse("text/x-markdown; charset=utf-8");
                    file = new File(requestEntry.getRequestParams().get("file").toString());
                    request = builder.post(RequestBody.create(mediaType, file))
                            .build();
                    break;
                case MULTIPART:
                    MultipartBody.Builder multiBuilder = new MultipartBody.Builder();
                    multiBuilder.setType(MultipartBody.FORM);
                    params = requestEntry.getRequestParams();
                    if (params != null && !params.isEmpty()) {
                        RequestBody fileBody;
                        for (String key : params.keySet()) {
                            if (key.endsWith(".png")) {
                                mediaType = MediaType.parse("image/png");
                                file = new File(params.get(key).toString());
                                fileBody = MultipartBody.create(mediaType, file);
                                multiBuilder.addFormDataPart(key, file.getName(), fileBody);
                            } else if (key.endsWith(".jpeg")) {
                                mediaType = MediaType.parse("image/jpeg");
                                file = new File(params.get(key).toString());
                                fileBody = MultipartBody.create(mediaType, file);
                                multiBuilder.addFormDataPart(key, file.getName(), fileBody);
                            }  else if (key.endsWith(".gif")) {
                                mediaType = MediaType.parse("image/gif");
                                file = new File(params.get(key).toString());
                                fileBody = MultipartBody.create(mediaType, file);
                                multiBuilder.addFormDataPart(key, file.getName(), fileBody);
                            } else {
                                multiBuilder.addPart(
                                        Headers.of("Content-Disposition", "form-data; name=\"" + key + "\""),
                                        RequestBody.create(null, params.get(key).toString()));
                            }
                        }
                    }
                    RequestBody multiBody = multiBuilder.build();
                    request = builder.post(multiBody).build();
                    break;
            }
        }
        return request;
    }

    private void handleResponse(Map<String, String> headers, String txt, Class clazz, HttpConnectCallback callback) {
//        try {
//            txt = EncryptionUtils.decrypt3DES(txt, Constant.DES3_KEY.getBytes("UTF-8"));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        JSONObject json = JSONObject.parseObject(txt);
        int status = json.getInteger("code");
        if (status == 0) {//请求成功
            HttpResponseEntry responseEntry = new HttpResponseEntry();
            responseEntry.addResponseHeaders(headers);
            responseEntry.setStatusCode(StatusCode.HTTP_OK);

            Object segment = json.get("data");
            Object data;
            if (segment instanceof JSONArray) {//是json数组
                JSONArray array = (JSONArray) segment;
                List<Object> list = new ArrayList<>();
                for (int i = 0; i < array.size(); i++) {
                    list.add(JSONObject.parseObject(array.getString(i), clazz));
                }
                data = list;
            } else {//是json对象
                data = JSON.parseObject(segment.toString(), clazz);
            }
            responseEntry.setData(data);
            callback.onRequestOk(responseEntry);
        } else {//请求失败
            HttpError error = new HttpError(status, json.getString("msg"));
            callback.onRequestFailure(error);
        }
    }

    private void handleResponse(Map<String, String> headers, String txt, HttpConnectCallback callback) {
//        try {
//            txt = EncryptionUtils.decrypt3DES(txt, Constant.DES3_KEY.getBytes("UTF-8"));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        JSONObject json = JSONObject.parseObject(txt);
        int status = json.getInteger("code");

        if (status == 0) {//请求成功
            String data = json.getString("data");
            HttpResponseEntry responseEntry = new HttpResponseEntry();
            responseEntry.addResponseHeaders(headers);
            responseEntry.setStatusCode(StatusCode.HTTP_OK);
            responseEntry.setData(data);
            callback.onRequestOk(responseEntry);
        } else {//请求失败
            HttpError error = new HttpError(status, json.getString("msg"));
            callback.onRequestFailure(error);
        }
    }

    private String generateGetUrl(String url, Map<String, Object> params) {
        StringBuffer urlBuff = new StringBuffer(url);
        urlBuff.append("?");
        Set<String> keys = params.keySet();
        Iterator<String> it = keys.iterator();
        String key;
        String value;
        while (it.hasNext()) {
            key = it.next();
            value = params.get(key).toString();
            urlBuff.append(key + "=" + value + "&");
        }
        return urlBuff.subSequence(0, urlBuff.length() - 1).toString();
    }

    private String generatePostJson(Map<String, Object> params) {
        if (params == null) return null;
        JSONObject json = new JSONObject(params);
        return json.toJSONString();
    }

    @Override
    public void stopAllSession() {
        try {
            mHttpClient.dispatcher().cancelAll();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void stopSessionByTag(Object tag) {
        try {
            for (Call call : mHttpClient.dispatcher().queuedCalls()) {
                if (call.request().tag().toString().contains(tag.toString())) {
                    call.cancel();
                }
            }
            for (Call call : mHttpClient.dispatcher().runningCalls()) {
                if (call.request().tag().toString().contains(tag.toString())) {
                    call.cancel();
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}