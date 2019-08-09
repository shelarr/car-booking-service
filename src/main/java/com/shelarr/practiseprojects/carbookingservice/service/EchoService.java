package com.shelarr.practiseprojects.carbookingservice.service;

import org.springframework.stereotype.Component;

@Component
public class EchoService {

    public String getEchoString(String msg) {
        return msg;
    }

}