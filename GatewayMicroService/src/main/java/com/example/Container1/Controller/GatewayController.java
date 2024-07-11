package com.example.Container1.Controller;


import com.example.Container1.Response.Response;
import com.example.Container1.Service.GatewayService;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class GatewayController {
    @Value("${compute.app.url}")
    private String container2Url;
    private final GatewayService service;
    @PostMapping("/calculate")
    public ResponseEntity<?>gateway(@RequestBody Response response){
        try {
            HashMap<String, String> requestMap = new HashMap<>();
            requestMap.put("file", service.checkFile(response.getFile()));
            requestMap.put("product", response.getProduct());

            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<Object> responseEntity = restTemplate.postForEntity(container2Url, requestMap, Object.class);

            return new ResponseEntity<>(responseEntity.getBody(), responseEntity.getStatusCode());
        } catch (HttpClientErrorException e) {
            HashMap<String, String> errorResponse = new HashMap<>();
            String error = e.getResponseBodyAsString();
            JSONObject jsonObject = new JSONObject(error);
            errorResponse.put("file", response.getFile());
            errorResponse.put("error", jsonObject.getString("error"));
            return ResponseEntity.status(e.getStatusCode()).body(errorResponse);
        } catch (RuntimeException e) {
            HashMap<String, String> errorResponse = new HashMap<>();
            errorResponse.put("file", response.getFile());
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleInvalidJsonInput(Exception ex) {
        HashMap<String, String> errorResponse = new HashMap<>();
        errorResponse.put("file", null);
        errorResponse.put("error", "Invalid JSON input.");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }
}
