package com.chernyavsky.util;

import com.chernyavsky.dto.User;
import com.google.gson.Gson;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ConvertToJson {

    public static final ConvertToJson INSTANCE = new ConvertToJson();

    public String toJson(User user) {
        Gson gson = new Gson();
        return gson.toJson(user);
    }

}
