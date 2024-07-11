package com.example.Container1.Service;

import com.example.Container1.Request.FileHandlerRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Objects;


@Service
@RequiredArgsConstructor
public class FileHandler {

    public HashMap<String, String> fileSaver(FileHandlerRequest fileHandlerRequest){
        String fileName = fileHandlerRequest.getFile();
        String fileData = fileHandlerRequest.getData();
        try{
            if(Objects.isNull(fileName)){
                throw new RuntimeException("Invalid JSON input.");
            }
//            File file = new File(fileName);
//            if(file.exists()){
//                throw new Exception("Error while storing the file to the storage.");
//            }
            File fileCreater = new File(File.separator+"kunj_PV_dir"+File.separator+fileName);
            FileWriter fileWriter =new FileWriter(fileCreater);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            String[] data = fileData.split(System.lineSeparator());
            for(String line: data){
                line  = line.replaceAll(",\\s+", ",");
                bufferedWriter.write(line.trim());
                bufferedWriter.newLine();
            }
            bufferedWriter.close();
            String message = "Success.";
            HashMap<String, String> output = new HashMap<>();
            output.put(fileName,message);
            return output;

        }
        catch (RuntimeException e) {
            throw new RuntimeException(e.getMessage());

        } catch (Exception e) {
            throw new RuntimeException("Error while storing the file to the storage.");
        }

    }
}
