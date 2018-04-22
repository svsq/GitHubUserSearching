package tk.svsq.githubusersearching.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import tk.svsq.githubusersearching.R;
import tk.svsq.githubusersearching.model.GitHubUser;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.UsersViewHolder> {

    private List<GitHubUser> users = new ArrayList<>();
    public static OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(View view, String login, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public UsersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_user_cardview, parent, false);
        return new UsersViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UsersViewHolder holder, int position) {
        holder.bind(users.get(position));
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public void addAll(List<GitHubUser> userList) {
        int position = getItemCount();
        this.users.addAll(userList);
        notifyItemRangeInserted(position, this.users.size());
    }

    public void clearAll() {
        int position = getItemCount();
        this.users.clear();
        notifyItemRangeRemoved(position, this.getItemCount());
    }

    public void add(List<GitHubUser> userList) {
        int position = getItemCount();
        this.users.add(userList.get(position));
        notifyItemInserted(position);
    }

    public class UsersViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private CardView carditem;

        private ImageView userAvatar;
        private TextView login;
        private TextView name;
        private TextView location;
        private TextView blog;

        public UsersViewHolder(View itemView) {
            super(itemView);

            carditem = itemView.findViewById(R.id.carditem_user);
            userAvatar = itemView.findViewById(R.id.user_avatar);
            name = itemView.findViewById(R.id.user_name);
            login = itemView.findViewById(R.id.user_login);
            location = itemView.findViewById(R.id.user_location);
            blog = itemView.findViewById(R.id.user_blog);
            carditem.setOnClickListener(this);
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
            if (UsersAdapter.listener != null) {
                //String userLogin = login.getText().toString();
                UsersAdapter.listener.onItemClick(view, users.get(getAdapterPosition())
                        .getLogin(), getAdapterPosition());
            }
        }
    }
}
