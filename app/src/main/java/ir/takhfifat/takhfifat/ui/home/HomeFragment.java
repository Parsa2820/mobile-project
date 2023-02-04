package ir.takhfifat.takhfifat.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
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

import ir.takhfifat.takhfifat.R;
import ir.takhfifat.takhfifat.databinding.FragmentHomeBinding;
import ir.takhfifat.takhfifat.model.Discount;
import ir.takhfifat.takhfifat.ui.DiscountAdapter;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final ProgressBar progressBar = binding.homeProgressBar;
        final RecyclerView discountsRecyclerView = binding.homeDiscountsRecyclerView;
        final TextView errorTextView = binding.homeErrorTextView;
        discountsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        StringBuilder url = new StringBuilder(getString(R.string.api_address));
        url.append("discounts/");
        RequestQueue queue = Volley.newRequestQueue(requireContext());
        StringRequest stringRequest = new StringRequest(url.toString(), response -> {
            Gson gson = new Gson();
            Discount[] discounts = gson.fromJson(response, Discount[].class);
            discountsRecyclerView.setAdapter(new DiscountAdapter(discounts));
            progressBar.setVisibility(View.GONE);
            discountsRecyclerView.setVisibility(View.VISIBLE);
        }, error -> {
            errorTextView.setText(error.getLocalizedMessage());
            progressBar.setVisibility(View.GONE);
            errorTextView.setVisibility(View.VISIBLE);
        });
        queue.add(stringRequest);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}