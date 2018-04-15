package tk.svsq.githubusersearching.rest;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import tk.svsq.githubusersearching.model.GitHubRepo;
import tk.svsq.githubusersearching.model.GitHubSearchResult;

public interface GitHubRepoCall {
    @GET("/users/{login}/repos")
    Call<List<GitHubRepo>> getRepo(@Path("login") String name);
}
