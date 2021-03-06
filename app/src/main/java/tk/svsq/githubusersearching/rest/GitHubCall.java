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
    Call<List<GitHubRepo>> getRepos(
            @Path("login") String name,
            @Query("page") String page);

    @GET("/search/users")
    Call<GitHubSearchResult> getUsers(
            @Query("q") String query,
            @Query("page") String page,
            @Query("per_page") String per_page
    );

    @GET("/users/{user}")
    Call<GitHubUser> getUser(@Path("user") String user);
}
