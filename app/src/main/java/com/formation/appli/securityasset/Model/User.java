package com.formation.appli.securityasset.Model;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by michael on 13-07-17.
 */

public class User {
    public boolean alerte;
    public String email;
    public Double latitude;
    public Double longitude;
    public int starCount = 0;
    public Map<String, Boolean> stars = new HashMap<>();

    public User() {
    }

    public User(boolean alerte, String email, Double latitude, Double longitude) {
        this.alerte = alerte;
        this.email = email;
        this.latitude = latitude;
        this.longitude = longitude;

    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("alerte", alerte);
        result.put("email",email);
        result.put("latitude", latitude);
        result.put("longitude", longitude);
        return result;
    }
}
