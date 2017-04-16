/*
 * Copyright (c) 2017. www.hrsoft.net  Inc. All rights reserved.
 */

package net.hrsoft.voter.network;


import net.hrsoft.voter.account.activity.RegisterActivity;
import net.hrsoft.voter.account.model.LoginResponse;
import net.hrsoft.voter.account.model.LoginReuqest;
import net.hrsoft.voter.account.model.RegisterRequest;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * API接口
 *
 * @author yuanzeng
 * @since 17/1/22 下午6:43
 */

public interface VoterService {
    @POST("user/register")
    Call<APIResponse> register(@Body RegisterRequest registerRequest);
    @POST("user/login")
    Call<APIResponse<LoginResponse>> login(@Body LoginReuqest loginReuqest);

}
