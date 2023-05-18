package org.pytorch.demo.ui.search;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import org.pytorch.demo.R;
import org.pytorch.demo.databinding.FragmentSearchBinding;


public class SearchFragment extends Fragment {
    private FragmentSearchBinding binding;
    ListView listView;

    String[] itemNames = {"Cây lúa", "Cây bắp", "Cây cà chua", "Cây khoai mì","Cây cải xanh"};
    Integer[] itemImages = {R.drawable.rice, R.drawable.corn, R.drawable.tomato, R.drawable.wheat, R.drawable.salad};
    public static SearchFragment newInstance() {
        return new SearchFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentSearchBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        listView = root.findViewById(R.id.plantPopular);

        PopularListApdapter adapter = new PopularListApdapter(getActivity(), itemNames, itemImages);
        listView.setAdapter(adapter);
        return root;
    }
}