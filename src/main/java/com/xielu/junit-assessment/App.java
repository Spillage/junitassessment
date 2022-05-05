package com.xielu.junitassessment;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import okhttp3.*;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
    }

    private static final String BASE_URL = "https://datausa.io/api/data";
    private static final String NATION = "United States";
    private static final String ID_NATION = "01000US";
    private static final String SLUG_NATION = "united-states";

    public String[] run(String year){
        OkHttpClient client = new OkHttpClient();
        HttpUrl.Builder urlBuilder
                = HttpUrl.parse(BASE_URL).newBuilder();
        urlBuilder.addQueryParameter("drilldowns", "Nation")
                .addQueryParameter("measures","Population")
                .addQueryParameter("year",year);
        String url = urlBuilder.build().toString();
        Request request = new Request.Builder()
                .url(url)
                .build();
        JSONArray arr = new JSONArray();
        String validYear = "", validData = "";
        try {
            Call call = client.newCall(request);
            Response response = call.execute();
            arr = JSON.parseObject(response.body().string()).getJSONArray("data");
            validYear = checkYear(year);
            validData = checkData(arr,year);
        }catch (Exception e){
            System.out.println("Running error!");
        }
        return new String[]{validData,validYear};
    }

    private String checkData(JSONArray arr,String year){
        boolean flag = false;
        if(arr == null || arr.size() == 0) return "false";
        JSONObject data = arr.getJSONObject(0);
        if(data.isEmpty()) flag = false;
        else {
            if(!data.getString("ID Nation").equals(ID_NATION) ||
                    !data.getString("Nation").equals(NATION) ||
                    !data.getString("Slug Nation").equals(SLUG_NATION)) flag = false;
            try{
                int temp = data.getIntValue("Population");
                if(data.getIntValue("Year") == Integer.parseInt(year) && data.getString("ID Year").equals(year))
                    flag = true;
            }catch (Exception e){
                System.out.println("Population is not numeric");
            }
        }
        return String.valueOf(flag);
    }

    private String checkYear(String year){
        int yearInt = 0;
        try{
            yearInt = Integer.parseInt(year);
            if(yearInt >= 2012 && yearInt <= 2020) return "true";
            else return "false";
        }catch (Exception e){
            return "false";
        }
    }
}
