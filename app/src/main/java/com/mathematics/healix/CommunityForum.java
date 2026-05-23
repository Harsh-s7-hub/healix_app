package com.mathematics.healix;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.widget.EditText;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class CommunityForum extends AppCompatActivity {
    RecyclerView communityposts_recyclerview;
    CommunityPostAdapter communityPostAdapter;
    List<CommunityPostModel> postList;
    BottomNavigationView bottomNav;

    private EditText searchbox;
    private final String[] hints = {"Search for doctors", "Find lab tests", "Make your diet plan"};
    private int hintIndex = 0;
    private int charIndex = 0;
    private boolean deleting = false;
    private Handler handler = new Handler();
    private long delay = 100;

    private final ActivityResultLauncher<Intent> createPostLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() != RESULT_OK || result.getData() == null) {
                    return;
                }

                Intent data = result.getData();
                String name = data.getStringExtra(create_community_post_activity.EXTRA_PROFILE_NAME);
                String description = data.getStringExtra(create_community_post_activity.EXTRA_DESCRIPTION);
                String date = data.getStringExtra(create_community_post_activity.EXTRA_DATE);
                String time = data.getStringExtra(create_community_post_activity.EXTRA_TIME);
                String imageUri = data.getStringExtra(create_community_post_activity.EXTRA_IMAGE_URI);

                CommunityPostModel newPost = new CommunityPostModel(
                        R.drawable.profilephoto1,
                        name,
                        date,
                        time,
                        description,
                        -1,
                        imageUri
                );

                communityPostAdapter.addPostAtTop(newPost);
                communityposts_recyclerview.scrollToPosition(0);
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_community_forum);
        Window window = getWindow();
        window.setStatusBarColor(getResources().getColor(R.color.blue));

        searchbox = findViewById(R.id.searchbox);
        animateHint();
        bottomNav =findViewById(R.id.bottomNavigationView);
        bottomNav.setSelectedItemId(R.id.nav_community);

        bottomNav.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_home) {
                Intent intent = new Intent(this, home_page.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);

                return true;
            }else if (id == R.id.nav_lab) {
                Intent intent = new Intent(this, home_lab_test.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                return true;
            }
            else if (id == R.id.nav_community) {

                 return true;
            }
            else if (id == R.id.nav_diet) {
                Intent intent = new Intent(this, diet_planning_page.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                return true;
            }

            return false;
        });


        communityposts_recyclerview = findViewById(R.id.communityposts_recyclerview);
        communityposts_recyclerview.setLayoutManager(new LinearLayoutManager(this));

        postList = new ArrayList<>();

        postList.add(new CommunityPostModel(R.drawable.profilephoto1,"Shivang Bansal","17 April 2025", "01:39PM","Nothing beats the peace of mind that comes from exercising in nature—fresh air, clear thoughts, and a happy soul. ",R.drawable.postimage));
        postList.add(new CommunityPostModel(R.drawable.profilephoto1,"Shivang Bansal","17 April 2025", "01:39PM","Nothing beats the peace of mind that comes from exercising in nature—fresh air, clear thoughts, and a happy soul. ",R.drawable.postimage));
        postList.add(new CommunityPostModel(R.drawable.profilephoto1,"Shivang Bansal","17 April 2025", "01:39PM","Nothing beats the peace of mind that comes from exercising in nature—fresh air, clear thoughts, and a happy soul. ",R.drawable.postimage));
        postList.add(new CommunityPostModel(R.drawable.profilephoto1,"Shivang Bansal","17 April 2025", "01:39PM","Nothing beats the peace of mind that comes from exercising in nature—fresh air, clear thoughts, and a happy soul. ",R.drawable.postimage));
        postList.add(new CommunityPostModel(R.drawable.profilephoto1,"Shivang Bansal","17 April 2025", "01:39PM","Nothing beats the peace of mind that comes from exercising in nature—fresh air, clear thoughts, and a happy soul. ",R.drawable.postimage));
        postList.add(new CommunityPostModel(R.drawable.profilephoto1,"Shivang Bansal","17 April 2025", "01:39PM","Nothing beats the peace of mind that comes from exercising in nature—fresh air, clear thoughts, and a happy soul. ",R.drawable.postimage));
        postList.add(new CommunityPostModel(R.drawable.profilephoto1,"Shivang Bansal","17 April 2025", "01:39PM","Nothing beats the peace of mind that comes from exercising in nature—fresh air, clear thoughts, and a happy soul. ",R.drawable.postimage));
        postList.add(new CommunityPostModel(R.drawable.profilephoto1,"Shivang Bansal","17 April 2025", "01:39PM","Nothing beats the peace of mind that comes from exercising in nature—fresh air, clear thoughts, and a happy soul. ",R.drawable.postimage));
        postList.add(new CommunityPostModel(R.drawable.profilephoto1,"Shivang Bansal","17 April 2025", "01:39PM","Nothing beats the peace of mind that comes from exercising in nature—fresh air, clear thoughts, and a happy soul. ",R.drawable.postimage));


        communityPostAdapter = new CommunityPostAdapter(postList);
        communityposts_recyclerview.setAdapter(communityPostAdapter);

        ExtendedFloatingActionButton createPostFab = findViewById(R.id.createPostFab);
        createPostFab.setOnClickListener(v ->
                createPostLauncher.launch(new Intent(this, create_community_post_activity.class)));

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void animateHint() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!deleting) {
                    if (charIndex <= hints[hintIndex].length()) {
                        searchbox.setHint(hints[hintIndex].substring(0, charIndex++));
                        handler.postDelayed(this, delay);
                    } else {
                        deleting = true;
                        handler.postDelayed(this, 1000); // Wait before deleting
                    }
                } else {
                    if (charIndex > 0) {
                        searchbox.setHint(hints[hintIndex].substring(0, --charIndex));
                        handler.postDelayed(this, delay);
                    } else {
                        deleting = false;
                        hintIndex = (hintIndex + 1) % hints.length;
                        handler.postDelayed(this, delay);
                    }
                }
            }
        }, delay);
    }

    @Override
    protected void onResume() {
        super.onResume();

        bottomNav.setSelectedItemId(R.id.nav_community);
    }
}