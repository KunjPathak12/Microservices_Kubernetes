package com.example.Container1.Controller;


import com.example.Container1.Request.FileHandlerRequest;
import com.example.Container1.Response.FileResponse;
import com.example.Container1.Service.FileHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class FileHandlerController {

    private final FileHandler fileHandler;

    @PostMapping("/store-file")
    public ResponseEntity<?>fileController(@RequestBody FileHandlerRequest fileHandlerRequest){
        FileResponse fileResponse =new FileResponse();
        try{
           fileResponse.setFileResponse(fileHandler.fileSaver(fileHandlerRequest));
           HashMap<String, String> response = new HashMap<>();
           response.put("file", fileHandlerRequest.getFile());
           response.put("message", fileResponse.getFileResponse().get(fileHandlerRequest.getFile()));
           return ResponseEntity.status(HttpStatus.OK).body(response);
        }
        catch (Exception e){
            HashMap<String,String>errorResponse = new HashMap<>();
            errorResponse.put("file",fileHandlerRequest.getFile());
            errorResponse.put("error",e.getMessage());
            fileResponse.setFileResponse(errorResponse);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(fileResponse.getFileResponse());
        }

    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleInvalidJsonInput(Exception exception) {
        HashMap<String, String> errorResponse = new HashMap<>();
        errorResponse.put("file", null);
        errorResponse.put("error", "Invalid JSON input.");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

}
