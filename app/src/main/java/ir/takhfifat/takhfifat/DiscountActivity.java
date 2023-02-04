package ir.takhfifat.takhfifat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

public class DiscountActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discount);
        final ImageView discountImage = findViewById(R.id.discountImageView);
        final TextView title = findViewById(R.id.discountTitleTextView);
        final TextView description = findViewById(R.id.discountDescriptionTextView);
        final Button copyCodeButton = findViewById(R.id.discountCopyCodeButton);
        final TextView code = findViewById(R.id.discountCodeTextView);
        final TextView endDate = findViewById(R.id.discountEndDateTextView);
        final TextView category = findViewById(R.id.discountCategoryTextView);

        Picasso.get().load(getIntent().getStringExtra("imageUrl")).into(discountImage, new Callback() {
            @Override
            public void onSuccess() {
            }

            @Override
            public void onError(Exception e) {
                discountImage.setImageResource(R.drawable.ic_baseline_no_photography_24);
            }
        });
        title.setText(getIntent().getStringExtra("title"));
        title.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(android.net.Uri.parse(getIntent().getStringExtra("link")));
            v.getContext().startActivity(intent);
        });
        description.setText(getIntent().getStringExtra("description"));
        copyCodeButton.setOnClickListener(v -> {
            android.content.ClipboardManager clipboard = (android.content.ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
            android.content.ClipData clip = android.content.ClipData.newPlainText("code", getIntent().getStringExtra("code"));
            clipboard.setPrimaryClip(clip);
            Toast toast = Toast.makeText(getApplicationContext(), "Discount code copied to clipboard", Toast.LENGTH_SHORT);
            toast.show();
        });
        code.setText(getIntent().getStringExtra("code"));
        endDate.setText(getIntent().getStringExtra("endDate"));
        category.setText(getIntent().getStringExtra("category"));
    }
}