package tk.svsq.githubusersearching.model;

import com.google.gson.annotations.SerializedName;

public class GitHubUser {
    @SerializedName("name")
    private String userName;
    @SerializedName("location")
    private String userLocation;
    @SerializedName("avatar_url")
    private String userAvatar;
    @SerializedName("blog")
    private String userBlog;
    @SerializedName("public_repos")
    private String userRepos;

    public GitHubUser(String userName, String userLocation, String userAvatar, String userBlog) {
        this.userName = userName;
        this.userLocation = userLocation;
        this.userAvatar = userAvatar;
        this.userBlog = userBlog;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
    //TODO (4): Maybe not needed setters here at all

    public String getUserLocation() {
        return userLocation;
    }

    public void setUserLocation(String userLocation) {
        this.userLocation = userLocation;
    }

    public String getUserAvatar() {
        return userAvatar;
    }

    public void setUserAvatar(String userAvatar) {
        this.userAvatar = userAvatar;
    }

    public String getUserBlog() {
        return userBlog;
    }

    public void setUserBlog(String userBlog) {
        this.userBlog = userBlog;
    }

    public String getUserRepos() {
        return userRepos;
    }

    public void setUserRepos(String userRepos) {
        this.userRepos = userRepos;
    }
}
