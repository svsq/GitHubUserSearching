package tk.svsq.githubusersearching.rest;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import tk.svsq.githubusersearching.model.GitHubUser;

public interface GitHubUserCall {

    @GET("/users/{user}")
    Call<GitHubUser> getUser(@Path("user")String user);
}
