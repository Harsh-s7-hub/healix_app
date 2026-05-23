package com.mathematics.healix;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.imageview.ShapeableImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CommunityPostAdapter extends RecyclerView.Adapter<CommunityPostAdapter.ViewHolder> {

    List<CommunityPostModel> posts;

    public CommunityPostAdapter(List<CommunityPostModel> posts) {
        this.posts = posts;
    }

    public void addPostAtTop(CommunityPostModel post) {
        posts.add(0, post);
        notifyItemInserted(0);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.community_item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CommunityPostModel post = posts.get(position);
        holder.profilePhoto.setImageResource(post.getProfilePhoto());
        holder.profileName.setText(post.getProfileName());
        holder.date.setText(post.getDate());
        holder.time.setText(post.getTime());
        holder.description.setText(post.getDescription());

        if (post.getImageUri() != null && !post.getImageUri().isEmpty()) {
            holder.postImage.setVisibility(View.VISIBLE);
            holder.postImage.setImageURI(Uri.parse(post.getImageUri()));
        } else if (post.getPostImage() != -1) {
            holder.postImage.setVisibility(View.VISIBLE);
            holder.postImage.setImageResource(post.getPostImage());
        } else {
            holder.postImage.setVisibility(View.GONE);
        }

        holder.favourite3.setOnClickListener(v -> {
            boolean isSelected = holder.favourite3.isSelected();
            holder.favourite3.setSelected(!isSelected);
            if(!isSelected){
                Toast.makeText(v.getContext(),"You liked the post",Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ShapeableImageView profilePhoto;
        TextView profileName, date, time, description;
        ImageView postImage;
        ImageButton favourite3;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            profilePhoto = itemView.findViewById(R.id.communityprofilephoto);
            profileName = itemView.findViewById(R.id.communityprofilename);
            date = itemView.findViewById(R.id.communityprofiledate);
            time = itemView.findViewById(R.id.communityprofiletime);
            description = itemView.findViewById(R.id.communityprofiledescription);
            postImage = itemView.findViewById(R.id.communityprofileimage);
            favourite3 = itemView.findViewById(R.id.favourite3);
        }
    }
}
