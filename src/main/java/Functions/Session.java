package Functions;

import model.User;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Session {

    public static Map<String, User> SessionMap = Collections.synchronizedMap(new HashMap<>());

    public void addSession(String sid, User user){
        SessionMap.put(sid, user);
        System.out.println("session added: " + sid + user); // 이상하다. null비정상 + user정상
        return;
    }

    public User getSession(String sid){

        User user = SessionMap.get(sid);
        System.out.println("Session USer: " + user); //// 여기가 문제, nnull 뜸
        return user;
    }
}
