package tk.svsq.githubusersearching.rest;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import tk.svsq.githubusersearching.model.GitHubRepo;
import tk.svsq.githubusersearching.model.GitHubSearchResult;
import tk.svsq.githubusersearching.model.GitHubUser;

public interface GitHubCall {
    @GET("/users/{login}/repos")
    Call<List<GitHubRepo>> getRepo(@Path("login") String name);

    @GET("/search/users")
    Call<GitHubSearchResult> getUsers(@Query("q") String query);

    /*@GET("/search/users")
    Call<GitHubSearchResult> getUsersPaged(@Query("q") String query);*/

    @GET("/users/{user}")
    Call<GitHubUser> getUser(@Path("user") String user);
}
