package com.abs.avtrack.ui;

import com.abs.avtrack.LoginActivity;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UserService {



    @POST("login.php?name=admin&pass=auto@tracker&accesskey=12345/")
    Call<LoginResponse>  userLogin(@Body LoginRequest loginActivity );

}
