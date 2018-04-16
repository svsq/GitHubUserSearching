package tk.svsq.githubusersearching.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import tk.svsq.githubusersearching.R;
import tk.svsq.githubusersearching.model.GitHubUser;

class UsersViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    private ImageView userAvatar;
    private TextView login;
    private TextView name;
    private TextView location;
    private TextView blog;

    private RecyclerViewClickListener mListener;

    public UsersViewHolder(View itemView, RecyclerViewClickListener listener) {
        super(itemView);
        userAvatar = itemView.findViewById(R.id.user_avatar);
        name = itemView.findViewById(R.id.user_name);
        login = itemView.findViewById(R.id.user_login);
        location = itemView.findViewById(R.id.user_location);
        blog = itemView.findViewById(R.id.user_blog);
        mListener = listener;
    }

    public void bind(GitHubUser item) {
        Picasso.get()
                .load(item.getUserAvatar())
                .resize(150, 150)
                .into(userAvatar);
        name.setText(item.getUserName());
        login.setText(item.getLogin());
        location.setText(item.getUserLocation());
        blog.setText(item.getUserBlog());
    }

    @Override
    public void onClick(View view) {
        mListener.onClick(view, getAdapterPosition());
    }
}
