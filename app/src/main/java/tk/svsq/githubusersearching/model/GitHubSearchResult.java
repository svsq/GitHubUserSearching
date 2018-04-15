package tk.svsq.githubusersearching.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class GitHubSearchResult implements Serializable {

    @SerializedName("items")
    private List<GitHubUser> items;

    public GitHubSearchResult() {

    }

    public GitHubSearchResult(List<GitHubUser> items) {
        this.items = items;
    }

    public List<GitHubUser> getItems() {
        return items;
    }

    public void setItems(List<GitHubUser> items) {
        this.items = items;
    }
}
