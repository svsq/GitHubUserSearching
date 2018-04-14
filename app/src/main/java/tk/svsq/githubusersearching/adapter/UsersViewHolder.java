package tk.svsq.githubusersearching.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import tk.svsq.githubusersearching.R;
import tk.svsq.githubusersearching.model.GitHubUser;

class UsersViewHolder extends RecyclerView.ViewHolder {

    private ImageView userAvatar;
    private TextView name;
    private TextView location;
    private TextView blog;

    public UsersViewHolder(View itemView) {
        super(itemView);
        userAvatar = itemView.findViewById(R.id.user_avatar);
        name = itemView.findViewById(R.id.user_name);
        location = itemView.findViewById(R.id.user_location);
        blog = itemView.findViewById(R.id.user_blog);
    }

    public void bind(GitHubUser item) {
        Picasso.get()
                .load(item.getUserAvatar())
                .resize(150, 150)
                .into(userAvatar);
        name.setText(item.getUserName());
        location.setText(item.getUserLocation());
        blog.setText(item.getUserBlog());
    }
}
