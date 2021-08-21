package com.meme.ala.common.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.meme.ala.core.auth.oauth.model.NaverUser;
import com.meme.ala.core.error.ErrorCode;
import com.meme.ala.core.error.exception.BusinessException;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

@Component
public class NaverOauthUtil {
    public static NaverUser naverTokenToNaverUser(String token) {
        ObjectMapper mapper = new ObjectMapper();
        String header = "Bearer " + token;
        try {
            String apiURL = "https://openapi.naver.com/v1/nid/me";
            URL url = new URL(apiURL);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("Authorization", header);
            int responseCode = con.getResponseCode();
            BufferedReader br;
            if (responseCode == 200) {
                br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            } else {
                br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
            }
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = br.readLine()) != null) {
                response.append(inputLine);
            }
            br.close();
            Map<String, Object> oAuthMap = mapper.readValue(response.toString(), Map.class);
            if (oAuthMap.get("resultcode").equals("024"))
                throw new BusinessException(ErrorCode.AUTHENTICATION_FAILED);
            return new NaverUser((Map<String, Object>) oAuthMap.get("response"));
        } catch (Exception e) {
            throw new BusinessException(ErrorCode.AUTHENTICATION_FAILED);
        }
    }
}
