package com.globant.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ReadPropertiesFile {
    private String path;

    private ReadPropertiesFile(String path) {
        this.path = path;
    }

    public static ReadPropertiesFile getInstance(String path){
        return new ReadPropertiesFile(path);
    }

    public Properties getProperties(){
        Properties properties = new Properties();
        File file = new File(path);
        FileInputStream fileInputStream= null;
        try {
            fileInputStream = new FileInputStream(file);
            properties.load(fileInputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(fileInputStream!=null){
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return properties;
    }
}
