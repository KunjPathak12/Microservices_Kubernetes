package com.example.Service;

import com.example.Request.CalculateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.HashMap;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CalculateImpl {

    public HashMap<String, Integer> calculate(CalculateRequest calculateRequest){
        String content;
        String data = "";
        try {
            if(Objects.isNull(calculateRequest.getFile())){
                throw new Exception("Invalid JSON input.");
            }
            String filePath=  File.separator+"kunj_PV_dir"+File.separator+calculateRequest.getFile();
            File file = new File(filePath);
            System.out.println(filePath);
            FileReader readFile = new FileReader(file);
            BufferedReader bufferedReader =new BufferedReader(readFile);
            while(!((content = bufferedReader.readLine()) == null)){
                data+= content.toLowerCase().trim()+System.lineSeparator();
            }
            bufferedReader.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException("File not found.");
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
//        if(data.length()==0){
//            throw new RuntimeException("Empty File.");
//        }

        String[] dataList = data.split(System.lineSeparator());
        HashMap<String, Integer> responseMap = new HashMap<>();

        try {

            for (int i = 1; i < dataList.length; i++) {
                if(dataList[i].split(",").length!=2){
                    throw new Exception("Input file not in CSV format.");
                }

                if ((dataList[i].split(",")[0].equals(calculateRequest.getProduct()))) {
                    Integer val = Integer.valueOf(dataList[i].split(",")[1]);
                    responseMap.put(calculateRequest.getProduct(), responseMap.getOrDefault(calculateRequest.getProduct(), 0) + val);
                }

            }
        }
        catch (Exception e){
            throw new RuntimeException(e.getMessage());

        }
//        System.out.println(responseMap.get(0));
        return responseMap;
    }

}
