package tk.svsq.githubusersearching.rest;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import tk.svsq.githubusersearching.model.GitHubSearchResult;
import tk.svsq.githubusersearching.model.GitHubUser;

public interface GitHubSearchUserCall {

    @GET("/search/users")
    Call<GitHubSearchResult> getUsers(@Query("q") String query);
}
