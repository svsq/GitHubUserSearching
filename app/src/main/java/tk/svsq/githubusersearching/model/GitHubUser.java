package tk.svsq.githubusersearching.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class GitHubUser implements Parcelable {
    @SerializedName("login")
    private String login;
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

    public GitHubUser(String login, String userName, String userLocation, String userAvatar, String userBlog, String userRepos) {
        this.login = login;
        this.userName = userName;
        this.userLocation = userLocation;
        this.userAvatar = userAvatar;
        this.userBlog = userBlog;
        this.userRepos = userRepos;
    }

    public GitHubUser(Parcel parcel) {

    }

    public String getUserName() {
        return userName;
    }

    public String getUserLocation() {
        return userLocation;
    }

    public String getUserAvatar() {
        return userAvatar;
    }

    public String getUserBlog() {
        return userBlog;
    }

    public String getUserRepos() {
        return userRepos;
    }

    public void setUserRepos(String userRepos) {
        this.userRepos = userRepos;
    }

    public String getLogin() {
        return login;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(login);
        parcel.writeString(userName);
        parcel.writeString(userLocation);
        parcel.writeString(userAvatar);
        parcel.writeString(userBlog);
        parcel.writeString(userRepos);
    }

    public static final Creator CREATOR = new Creator<GitHubUser>() {
        @Override
        public GitHubUser createFromParcel(Parcel parcel) {
            return new GitHubUser(parcel);
        }

        @Override
        public GitHubUser[] newArray(int size) {
            return new GitHubUser[size];
        }
    };
}
