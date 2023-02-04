package ir.takhfifat.takhfifat.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.MenuHost;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import java.util.ArrayList;

import ir.takhfifat.takhfifat.R;
import ir.takhfifat.takhfifat.databinding.FragmentHomeBinding;
import ir.takhfifat.takhfifat.model.Discount;
import ir.takhfifat.takhfifat.ui.DiscountAdapter;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private Discount[] discounts;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final LinearLayout homeLinearLayout = binding.homeLinearLayout;
        final ProgressBar progressBar = binding.homeProgressBar;
        final SearchView searchView = binding.homeSearchView;
        final RecyclerView discountsRecyclerView = binding.homeDiscountsRecyclerView;
        final TextView errorTextView = binding.homeErrorTextView;
        discountsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        StringBuilder url = new StringBuilder(getString(R.string.api_address));
        url.append("discounts/");
        RequestQueue queue = Volley.newRequestQueue(requireContext());
        StringRequest stringRequest = new StringRequest(url.toString(), response -> {
            Gson gson = new Gson();
            discounts = gson.fromJson(response, Discount[].class);
            discountsRecyclerView.setAdapter(new DiscountAdapter(discounts));
            progressBar.setVisibility(View.GONE);
            homeLinearLayout.setVisibility(View.VISIBLE);
        }, error -> {
            errorTextView.setText(error.getLocalizedMessage());
            progressBar.setVisibility(View.GONE);
            errorTextView.setVisibility(View.VISIBLE);
        });
        queue.add(stringRequest);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.length() == 0) {
                    discountsRecyclerView.setAdapter(new DiscountAdapter(discounts));
                } else {
                    ArrayList<Discount> filteredDiscounts = new ArrayList<>();
                    for (Discount discount : discounts) {
                        if (discount.matches(newText)) {
                            filteredDiscounts.add(discount);
                        }
                    }
                    Discount[] filteredDiscountsArray = new Discount[filteredDiscounts.size()];
                    filteredDiscounts.toArray(filteredDiscountsArray);
                    discountsRecyclerView.setAdapter(new DiscountAdapter(filteredDiscountsArray));
                }
                return false;
            }
        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}