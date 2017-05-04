/*
 * Copyright (c) 2017. www.hrsoft.net  Inc. All rights reserved.
 */

package net.hrsoft.voter.network;


import net.hrsoft.voter.account.model.LoginResponse;
import net.hrsoft.voter.account.model.LoginReuqest;
import net.hrsoft.voter.account.model.RegisterRequest;
import net.hrsoft.voter.home.model.CreateVoteRequest;
import net.hrsoft.voter.home.model.ModifyPasswordRequest;
import net.hrsoft.voter.home.model.UserVoteInfoResponse;
import net.hrsoft.voter.home.model.VoteRecordsResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

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
    @GET("vote/info")
    Call<APIResponse<UserVoteInfoResponse>> userVoteInfoGet(@Header("Token") String Token, @Query("page") int page, @Query("rows") int rows);
    @POST("vote/create")
    Call<APIResponse>createVotePost(@Header("Token")String Token,@Body CreateVoteRequest createVoteRequest);
    @DELETE("vote/{voteId}/delete")
    Call<APIResponse>deleteVotePost(@Path("voteId") long voteId);
    @GET("record")
    Call<APIResponse<VoteRecordsResponse>>getVoteRecordsPost(@Query("voteId") long voteId);
    @PUT("user/password")
    Call<APIResponse>modifyPasswordPost(@Header("Token")String Token,@Body ModifyPasswordRequest modifyPasswordRequest);
    @POST("user/logout")
    Call<APIResponse>logoutPost();
}
