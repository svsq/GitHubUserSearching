package tk.svsq.githubusersearching.rest;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import tk.svsq.githubusersearching.model.GitHubRepo;

public interface GitHubRepoCall {
    @GET("/users/{user}/repos")
    Call<List<GitHubRepo>> getRepo(@Path("user") String name);
}
