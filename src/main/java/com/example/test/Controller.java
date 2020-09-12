package com.example.test;

import com.example.test.base.ResponseDto;
import com.example.test.common.HttpStatusUtil;
import com.example.test.common.Values;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/")
public class Controller {

    @Autowired
    ClientRespository clientRespository;

    @GetMapping("/status")
    public Map<String,String> getStatus(){
        Map<String,String> response = new HashMap<String,String>();
        response.put("database","success");
        response.put("disk","success");
        response.put("network","success");

        return  response;
    }

    @GetMapping("/client")
    public ResponseEntity<String> getClientOk(@RequestParam String clientId,@RequestParam String clientSecret){
        Map<String,String> response = new HashMap<String,String>();
        response.put("status","200");
        response.put("message","success");


        return  new HttpStatusUtil().getHttpStatusByResponse(new ResponseDto(Values.APP_CODE_OK, response));
    }

    @GetMapping("/token")
    public ResponseEntity<String> getClientToken(@RequestParam String clientId,@RequestParam String clientSecret){
        Map<String,String> response = new HashMap<String,String>();
        response.put("token",clientRespository.findByClientAcces(clientId,clientSecret));
        return  new HttpStatusUtil().getHttpStatusByResponse(new ResponseDto(Values.APP_CODE_OK, response));
    }

    @GetMapping("/introspect")
    public ResponseEntity<String> getClientIntrospect(@RequestParam String token){
        Map<String,String> response = new HashMap<String,String>();
        String clienId=clientRespository.findByToken(token);
        if(clienId==null){
            response.put("status","400");
            response.put("message","error");
            return  new HttpStatusUtil().getHttpStatusByResponse(new ResponseDto(Values.APP_CODE_BAD_REQUEST, response));
        }else{
            response.put("clientId",clientRespository.findByToken(token));
            response.put("status","200");
            response.put("message","success");
            return  new HttpStatusUtil().getHttpStatusByResponse(new ResponseDto(Values.APP_CODE_OK, response));
        }

    }
}
