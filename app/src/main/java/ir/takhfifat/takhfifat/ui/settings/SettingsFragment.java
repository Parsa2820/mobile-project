package ir.takhfifat.takhfifat.ui.settings;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import java.util.Arrays;

import ir.takhfifat.takhfifat.databinding.ActivityDiscountBinding;
import ir.takhfifat.takhfifat.databinding.FragmentSettingsBinding;

public class SettingsFragment extends Fragment {

    public static final String SHARED_PREFS = "sharedPrefs";
    private static final String[] REGIONS = new String[] {"Tehran", "Mashhad", "Tabriz", "Shiraz", "Mazandaran", "Gilan"};
    private FragmentSettingsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentSettingsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final Spinner spinner = binding.regionSpinner;
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(requireContext(), android.R.layout.simple_spinner_item, REGIONS);
        spinner.setAdapter(adapter);
        // read from shared preferences
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences(SHARED_PREFS, Activity.MODE_PRIVATE);
        String region = sharedPreferences.getString("region", "Tehran");
        int position = Arrays.asList(REGIONS).indexOf(region);
        spinner.setSelection(position);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String region = (String) parent.getItemAtPosition(position);
                SharedPreferences sharedPreferences = requireActivity().getSharedPreferences(SHARED_PREFS, Activity.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("region", region);
                editor.apply();
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