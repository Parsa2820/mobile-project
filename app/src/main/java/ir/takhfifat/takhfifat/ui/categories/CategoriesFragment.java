package ir.takhfifat.takhfifat.ui.categories;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import java.lang.reflect.Array;
import java.util.ArrayList;

import ir.takhfifat.takhfifat.R;
import ir.takhfifat.takhfifat.databinding.FragmentCategoriesBinding;
import ir.takhfifat.takhfifat.model.Discount;
import ir.takhfifat.takhfifat.ui.DiscountAdapter;

public class CategoriesFragment extends Fragment {

    private FragmentCategoriesBinding binding;
    private Discount[] discounts;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentCategoriesBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final LinearLayout linearLayout = binding.categoriesLinearLayout;
        final Spinner spinner = binding.categoriesSpinner;
        final RecyclerView recyclerView = binding.categoriesRecyclerView;
        final ProgressBar progressBar = binding.categoriesProgressBar;
        final TextView errorTextView = binding.categoriesErrorTextView;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        String discountsUrl = getString(R.string.api_address) + "discounts/";
        RequestQueue queue = Volley.newRequestQueue(requireContext());
        StringRequest stringRequest = new StringRequest(discountsUrl, response -> {
            Gson gson = new Gson();
            discounts = gson.fromJson(response, Discount[].class);
            String categoriesUrl = getString(R.string.api_address) + "categories/";
            StringRequest categoriesStringRequest = new StringRequest(categoriesUrl, categoriesResponse -> {
                String[] categories = gson.fromJson(categoriesResponse, String[].class);
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(requireContext(), android.R.layout.simple_spinner_item, categories);
                spinner.setAdapter(adapter);
                progressBar.setVisibility(View.GONE);
                linearLayout.setVisibility(View.VISIBLE);
            }, error -> {
                errorTextView.setText(error.getLocalizedMessage());
                progressBar.setVisibility(View.GONE);
                errorTextView.setVisibility(View.VISIBLE);
            });
            queue.add(categoriesStringRequest);
        }, error -> {
            errorTextView.setText(error.getLocalizedMessage());
            progressBar.setVisibility(View.GONE);
            errorTextView.setVisibility(View.VISIBLE);
        });
        queue.add(stringRequest);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String category = parent.getItemAtPosition(position).toString();
                ArrayList<Discount> categoryDiscounts = new ArrayList<>();
                for (Discount discount : discounts) {
                    if (discount.getCategory().equals(category)) {
                        categoryDiscounts.add(discount);
                    }
                }
                Discount[] categoryDiscountsArray = new Discount[categoryDiscounts.size()];
                categoryDiscounts.toArray(categoryDiscountsArray);
                DiscountAdapter adapter = new DiscountAdapter(categoryDiscountsArray);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

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