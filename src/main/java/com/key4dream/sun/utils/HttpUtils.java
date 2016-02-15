package com.key4dream.sun.utils;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 */
public class HttpUtils {

    private static Logger logger = LoggerFactory.getLogger(HttpUtils.class);

    private static final Integer DEFAULT_TIMEOUT = 3 * 1000;
    // private static CloseableHttpClient httpclient = HttpClients.createDefault();

    public static String get(String url) {
        return get(url, DEFAULT_TIMEOUT);
    }

    public static String get(String url, Integer timeout) {
        String content = "";
        CloseableHttpClient httpclient = null;
        CloseableHttpResponse response = null;
        try {
            httpclient = HttpClients.createDefault();
            RequestConfig requestConfig = RequestConfig.custom().setConnectionRequestTimeout(timeout)
                    .setConnectTimeout(timeout).setSocketTimeout(timeout).build();
            // 创建httpget.     
            HttpGet httpget = new HttpGet(url);
            httpget.setConfig(requestConfig);
            // 执行get请求.    
            response = httpclient.execute(httpget);
            // 获取响应实体    
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                // 打印响应内容    
                content = EntityUtils.toString(entity);
            }
            return content;
        } catch (Exception e) {
            logger.error("http get throw Exception, url=" + url, e);
        } finally {
            // 关闭连接,释放资源    
            try {
                if (response != null) {
                    response.close();
                }
                if (httpclient != null) {
                    httpclient.close();
                }
            } catch (IOException e) {
                logger.error("httpclient close error.", e);
            }
        }
        return content;
    }

    /**
     * Http request ：Post
     *
     * @param url
     * @param params
     * @param heads
     * @return String of request result
     */
    public static String post(String url, Map<String, String> params) {
        List<NameValuePair> pairList = new ArrayList<NameValuePair>();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            NameValuePair pair = new BasicNameValuePair(entry.getKey(), entry.getValue());
            pairList.add(pair);
        }
        return post(url, pairList, null, DEFAULT_TIMEOUT, Charset.forName("UTF-8"));
    }

    /**
     * Http request ：Post, add timeout
     *
     * @param url
     * @param params
     * @param heads
     * @return String of request result
     */
    public static String post(String url, Map<String, String> params, Integer timeout) {
        List<NameValuePair> pairList = new ArrayList<NameValuePair>();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            NameValuePair pair = new BasicNameValuePair(entry.getKey(), entry.getValue());
            pairList.add(pair);
        }
        return post(url, pairList, null, timeout, Charset.forName("UTF-8"));
    }

    /**
     * Http request ：Post， default utf-8
     *
     * @param url
     * @param params
     * @param heads
     * @return String of request result
     */
    public static String post(String url, Map<String, String> params, Map<String, String> heads) {
        List<NameValuePair> pairList = new ArrayList<NameValuePair>();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            NameValuePair pair = new BasicNameValuePair(entry.getKey(), entry.getValue());
            pairList.add(pair);
        }
        return post(url, pairList, heads, DEFAULT_TIMEOUT, Charset.forName("UTF-8"));
    }

    /**
     * Http request ：Post
     *
     * @param url
     * @param params
     * @param heads
     * @return String of request result
     */
    public static String post(String url, List<NameValuePair> params, Map<String, String> heads) {
        return post(url, params, heads, DEFAULT_TIMEOUT, Charset.forName("UTF-8"));
    }

    /**
     * Http request ：Post
     *
     * @param url
     * @param params
     * @param heads
     * @param timeOut
     *            (Millisecond)
     * @return String of request result
     */
    public static String post(String url, List<NameValuePair> params, Map<String, String> heads, Integer timeout,
            Charset charset) {
        String reStr = "";
        CloseableHttpClient httpclient = null;
        CloseableHttpResponse response = null;
        try {
            httpclient = HttpClients.createDefault();
            HttpPost httppost = new HttpPost(url);

            if (params != null) {
                httppost.setEntity(new UrlEncodedFormEntity(params, charset));
            }
            if (heads != null) {
                for (Map.Entry<String, String> e : heads.entrySet()) {
                    httppost.addHeader(e.getKey(), e.getValue());
                }
            }

            // set Timeout
            RequestConfig requestConfig = RequestConfig.custom().setConnectionRequestTimeout(timeout)
                    .setConnectTimeout(timeout).setSocketTimeout(timeout).build();
            httppost.setConfig(requestConfig);
            // get responce
            response = httpclient.execute(httppost);
            // get http status code
            int resStatus = response.getStatusLine().getStatusCode();
            if (resStatus == HttpStatus.SC_OK) {
                // get result data
                HttpEntity entity = response.getEntity();
                reStr = EntityUtils.toString(entity);
            } else {
                logger.error(url + ": resStatus is " + resStatus);
            }
        } catch (Exception e) {
            logger.error("http post throw Exception, url=" + url, e);
        } finally {
            // 关闭连接,释放资源
            try {
                httpclient.close();
            } catch (IOException e) {
                logger.error("httpclient close error.", e);
            }
        }
        return reStr;
    }

    /**
     * post发送json body内容
     * @param url
     * @param body
     * @param timeout
     * @param charset
     * @return
     */
    public static String postJson(String url, String body) {
        return postJson(url, body, DEFAULT_TIMEOUT, Charset.forName("UTF-8"));
    }

    /**
     * post发送json body内容
     * @param url
     * @param body
     * @param timeout
     * @param charset
     * @return
     */
    public static String postJson(String url, String body, Integer timeout) {
        return postJson(url, body, timeout, Charset.forName("UTF-8"));
    }

    /**
     * post发送json body内容
     * @param url
     * @param body
     * @param timeout
     * @param charset
     * @return
     */
    public static String postJson(String url, String body, Integer timeout, Charset charset) {
        String reStr = "";
        CloseableHttpClient httpclient = null;
        CloseableHttpResponse response = null;
        try {
            httpclient = HttpClients.createDefault();
            HttpPost httppost = new HttpPost(url);
            body = StringUtils.trimToEmpty(body);
            HttpEntity jsonEntity = new ByteArrayEntity(body.getBytes("UTF-8"), ContentType.APPLICATION_JSON);
            httppost.setEntity(jsonEntity);

            // set Timeout
            RequestConfig requestConfig = RequestConfig.custom().setConnectionRequestTimeout(timeout)
                    .setConnectTimeout(timeout).setSocketTimeout(timeout).build();
            httppost.setConfig(requestConfig);
            // get responce
            response = httpclient.execute(httppost);
            // get http status code
            int resStatus = response.getStatusLine().getStatusCode();
            if (resStatus == HttpStatus.SC_OK) {
                // get result data
                HttpEntity entity = response.getEntity();
                reStr = EntityUtils.toString(entity);
            } else {
                logger.error(url + ": resStatus is " + resStatus);
            }
        } catch (Exception e) {
            logger.error("http post throw Exception, url=" + url, e);
        } finally {
            // 关闭连接,释放资源
            try {
                httpclient.close();
            } catch (IOException e) {
                logger.error("httpclient close error.", e);
            }
        }
        return reStr;
    }
}
