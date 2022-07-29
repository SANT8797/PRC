package com.example.prc;

import com.example.prc.Notifications.MyResponse;
import com.example.prc.Notifications.Sender;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface APIService {
    @Headers(
            {
                    "Content-Type:application/json",
                    "Authorization:key=AAAAK-v-uxY:APA91bFEJBgX1nWDChKmMDLEaaj7VRdn0Aaenqk1XseFu4HKfWwI0FamRHPFOpM8Rs-QKYYIs7XMJvl_Ks0Uwi9rU6qfoKwbChvkd9_h3b___Eqb77EaEZH96G1JOLPu-u5AEq-76gYQ"
            }
    )
    @POST("fcm/send")
    Call<MyResponse> sendNotification(@Body Sender body );
}
