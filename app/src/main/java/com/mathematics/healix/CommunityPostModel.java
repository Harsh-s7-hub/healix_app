package com.mathematics.healix;

public class CommunityPostModel {
    private int profilePhoto;
    private String profileName;
    private String date;
    private String time;
    private String description;
    private int postImage; // use -1 if no image
    private String imageUri; // optional gallery image

    public CommunityPostModel(int profilePhoto, String profileName, String date, String time, String description, int postImage) {
        this(profilePhoto, profileName, date, time, description, postImage, null);
    }

    public CommunityPostModel(int profilePhoto, String profileName, String date, String time, String description, int postImage, String imageUri) {
        this.profilePhoto = profilePhoto;
        this.profileName = profileName;
        this.date = date;
        this.time = time;
        this.description = description;
        this.postImage = postImage;
        this.imageUri = imageUri;
    }

    public int getProfilePhoto() { return profilePhoto; }
    public String getProfileName() { return profileName; }
    public String getDate() { return date; }
    public String getTime() { return time; }
    public String getDescription() { return description; }
    public int getPostImage() { return postImage; }
    public String getImageUri() { return imageUri; }
}
