package org.example.serializer;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Serializer;
import org.example.model.UserInfoDto;


import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

public class UserInfoSerializer implements Serializer<UserInfoDto> {

    @Override
    public void configure(Map<String, ?> mpa, boolean b){}

    @Override
    public byte[] serialize(String arg0, UserInfoDto arg1) {
        byte[] bytes = null;
        ObjectMapper mapper = new ObjectMapper();
        try{
            bytes = mapper.writeValueAsString(arg1).getBytes();
        }catch (Exception e){
            e.printStackTrace();
        }

        return bytes;
    }

    @Override
    public void close() {}
}
