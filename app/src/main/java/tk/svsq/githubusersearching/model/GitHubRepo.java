package tk.svsq.githubusersearching.model;

import com.google.gson.annotations.SerializedName;

public class GitHubRepo {
    @SerializedName("name")
    private String repoName;
    @SerializedName("description")
    private String repoDescription;

    @SerializedName("language")
    private String repoLanguage;

    public GitHubRepo(String repoName, String repoDescription, String repoLanguage) {
        this.repoName = repoName;
        this.repoDescription = repoDescription;
        this.repoLanguage = repoLanguage;
    }

    public GitHubRepo() {
    }

    public String getRepoName() {
        return repoName;
    }

    public void setRepoName(String repoName) {
        this.repoName = repoName;
    }

    public String getRepoDescription() {
        return repoDescription;
    }

    public void setRepoDescription(String repoDescription) {
        this.repoDescription = repoDescription;
    }

    public String getRepoLanguage() {
        return repoLanguage;
    }

    public void setRepoLanguage(String repoLanguage) {
        this.repoLanguage = repoLanguage;
    }
}
