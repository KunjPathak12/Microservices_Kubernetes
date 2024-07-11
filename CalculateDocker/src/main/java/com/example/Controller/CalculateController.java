package com.example.Controller;

import com.example.Request.CalculateRequest;
import com.example.Response.CalculateResponse;
import com.example.Service.CalculateImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Objects;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class CalculateController {

    private final CalculateImpl calculateImpl;

    @PostMapping("/calculate")
    public ResponseEntity<?>calculate(@RequestBody CalculateRequest calculateRequest){
        try {
            HashMap<String, Integer> response = calculateImpl.calculate(calculateRequest);
            CalculateResponse calculateResponse = new CalculateResponse();
            calculateResponse.setFile(calculateRequest.getFile());
            calculateResponse.setSum(response.get(calculateRequest.getProduct()));
            if(Objects.isNull(calculateResponse.getSum())){
                throw new RuntimeException("Input file not in CSV format.");
            }
            return ResponseEntity.status(HttpStatus.OK).body(calculateResponse);
        }
        catch (RuntimeException e) {
            HashMap<String, String> errorResponse = new HashMap<>();
            errorResponse.put("file", calculateRequest.getFile());
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }

}