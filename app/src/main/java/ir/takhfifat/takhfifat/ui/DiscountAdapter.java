package ir.takhfifat.takhfifat.ui;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import ir.takhfifat.takhfifat.DiscountActivity;
import ir.takhfifat.takhfifat.R;
import ir.takhfifat.takhfifat.model.Discount;

public class DiscountAdapter extends RecyclerView.Adapter<DiscountAdapter.ViewHolder> {
    private final Discount[] discounts;

    public DiscountAdapter(Discount[] discounts) {
        this.discounts = discounts;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.discount_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Discount discount = discounts[position];
        holder.getTitleTextView().setText(discount.getTitle());
        holder.getDescriptionTextView().setText(discount.getDescription());
        holder.getCategoryTextView().setText(discount.getCategory());
        holder.getEndDateTextView().setText(discount.getEndDate());
        holder.getTitleTextView().setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(android.net.Uri.parse(discount.getLink()));
            v.getContext().startActivity(intent);
        });
        Picasso.get().load(discount.getImageUrl()).into(holder.getImageUrlImageView());
        Picasso.get().load(discount.getImageUrl()).into(holder.getImageUrlImageView(), new Callback() {
            @Override
            public void onSuccess() {
            }

            @Override
            public void onError(Exception e) {
                holder.getImageUrlImageView().setImageResource(R.drawable.ic_baseline_no_photography_24);
            }
        });
        holder.getLinearLayout().setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), DiscountActivity.class);
            intent.putExtra("title", discount.getTitle());
            intent.putExtra("description", discount.getDescription());
            intent.putExtra("imageUrl", discount.getImageUrl());
            intent.putExtra("link", discount.getLink());
            intent.putExtra("category", discount.getCategory());
            intent.putExtra("endDate", discount.getEndDate());
            intent.putExtra("code", discount.getCode());
            v.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return discounts.length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final LinearLayout linearLayout;
        private final TextView titleTextView;
        private final TextView descriptionTextView;
        private final ImageView imageUrlImageView;
        private final TextView categoryTextView;
        private final TextView endDateTextView;

        public ViewHolder(View view) {
            super(view);
            linearLayout = (LinearLayout) view.findViewById(R.id.itemLinearLayout);
            titleTextView = (TextView) view.findViewById(R.id.itemTitleTextView);
            descriptionTextView = (TextView) view.findViewById(R.id.itemDescriptionTextView);
            imageUrlImageView = (ImageView) view.findViewById(R.id.itemImageUrlImageView);
            categoryTextView = (TextView) view.findViewById(R.id.itemCategoryTextView);
            endDateTextView = (TextView) view.findViewById(R.id.itemEndDateTextView);
        }

        public LinearLayout getLinearLayout() {
            return linearLayout;
        }

        public TextView getTitleTextView() {
            return titleTextView;
        }

        public TextView getDescriptionTextView() {
            return descriptionTextView;
        }

        public ImageView getImageUrlImageView() {
            return imageUrlImageView;
        }

        public TextView getCategoryTextView() {
            return categoryTextView;
        }

        public TextView getEndDateTextView() {
            return endDateTextView;
        }
    }
}
