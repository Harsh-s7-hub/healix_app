package com.mathematics.healix;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class create_community_post_activity extends AppCompatActivity {

    public static final String EXTRA_PROFILE_NAME = "extra_profile_name";
    public static final String EXTRA_DESCRIPTION = "extra_description";
    public static final String EXTRA_IMAGE_URI = "extra_image_uri";
    public static final String EXTRA_DATE = "extra_date";
    public static final String EXTRA_TIME = "extra_time";

    private static final int MAX_CAPTION_LENGTH = 500;

    private EditText displayNameInput;
    private EditText postCaptionInput;
    private LinearLayout uploadImageCard;
    private MaterialCardView imagePreviewCard;
    private ImageView postImagePreview;
    private MaterialButton publishPostButton;
    private ProgressBar postProgressBar;
    private TextView charCountText;
    private BottomNavigationView bottomNav;

    private Uri selectedImageUri;
    private final ActivityResultLauncher<String> pickImageLauncher =
            registerForActivityResult(new ActivityResultContracts.GetContent(), uri -> {
                if (uri != null) {
                    selectedImageUri = uri;
                    showImagePreview(uri);
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_create_community_post);

        Window window = getWindow();
        window.setStatusBarColor(getResources().getColor(R.color.blue));

        bindViews();
        setupBottomNav();
        setupListeners();
        setupCharacterCounter();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void bindViews() {
        displayNameInput = findViewById(R.id.displayNameInput);
        postCaptionInput = findViewById(R.id.postCaptionInput);
        uploadImageCard = findViewById(R.id.uploadImageCard);
        imagePreviewCard = findViewById(R.id.imagePreviewCard);
        postImagePreview = findViewById(R.id.postImagePreview);
        publishPostButton = findViewById(R.id.publishPostButton);
        postProgressBar = findViewById(R.id.postProgressBar);
        charCountText = findViewById(R.id.charCountText);
        bottomNav = findViewById(R.id.bottomNavigationView);
    }

    private void setupBottomNav() {
        bottomNav.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_home) {
                startActivity(new Intent(this, home_page.class)
                        .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP));
                finish();
                return true;
            } else if (id == R.id.nav_lab) {
                startActivity(new Intent(this, home_lab_test.class)
                        .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP));
                finish();
                return true;
            } else if (id == R.id.nav_community) {
                // Already in the community flow — do not finish here or the screen closes on launch.
                return true;
            } else if (id == R.id.nav_diet) {
                startActivity(new Intent(this, diet_planning_page.class)
                        .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP));
                finish();
                return true;
            }
            return false;
        });
        bottomNav.setSelectedItemId(R.id.nav_community);
    }

    private void setupListeners() {
        findViewById(R.id.backButtonCreatePost).setOnClickListener(v -> finish());

        uploadImageCard.setOnClickListener(v -> pickImageLauncher.launch("image/*"));

        findViewById(R.id.removeImageButton).setOnClickListener(v -> {
            selectedImageUri = null;
            uploadImageCard.setVisibility(View.VISIBLE);
            imagePreviewCard.setVisibility(View.GONE);
            postImagePreview.setImageDrawable(null);
        });

        publishPostButton.setOnClickListener(v -> publishPost());
    }

    private void setupCharacterCounter() {
        postCaptionInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                charCountText.setText(s.length() + "/" + MAX_CAPTION_LENGTH);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    private void showImagePreview(Uri uri) {
        uploadImageCard.setVisibility(View.GONE);
        imagePreviewCard.setVisibility(View.VISIBLE);
        postImagePreview.setImageURI(uri);
    }

    private void publishPost() {
        String name = displayNameInput.getText().toString().trim();
        String caption = postCaptionInput.getText().toString().trim();

        if (name.isEmpty() || caption.isEmpty()) {
            Toast.makeText(this, "Please enter your name and post caption", Toast.LENGTH_SHORT).show();
            return;
        }

        publishPostButton.setEnabled(false);
        publishPostButton.setText("");
        postProgressBar.setVisibility(View.VISIBLE);

        Date now = new Date();
        String date = new SimpleDateFormat("dd MMMM yyyy", Locale.getDefault()).format(now);
        String time = new SimpleDateFormat("hh:mma", Locale.getDefault()).format(now);

        new Handler().postDelayed(() -> {
            Intent resultIntent = new Intent();
            resultIntent.putExtra(EXTRA_PROFILE_NAME, name);
            resultIntent.putExtra(EXTRA_DESCRIPTION, caption);
            resultIntent.putExtra(EXTRA_DATE, date);
            resultIntent.putExtra(EXTRA_TIME, time);
            if (selectedImageUri != null) {
                resultIntent.putExtra(EXTRA_IMAGE_URI, selectedImageUri.toString());
            }

            setResult(RESULT_OK, resultIntent);
            Toast.makeText(this, "Post published successfully!", Toast.LENGTH_SHORT).show();
            finish();
        }, 1200);
    }
}
