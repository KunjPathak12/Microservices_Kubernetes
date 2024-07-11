package com.example.Container1.Service;


import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class GatewayService {

    public String checkFile(String fileName) {
        if(Objects.isNull(fileName)){
            throw new RuntimeException("Invalid JSON input.");
        }
        String filePath=  File.separator+"kunj_PV_dir"+File.separator+fileName;
        System.out.println(filePath);
        File file = new File(filePath);
        try{
            if (file.exists()) {
                return fileName;
            }
            else {
                throw new FileNotFoundException("File not found.");
            }
        }
        catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }

    }
}
