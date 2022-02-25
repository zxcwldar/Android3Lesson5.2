package com.example.android3lesson2.ui.fragments.boarding;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.viewpager2.widget.ViewPager2;

import com.example.android3lesson2.R;
import com.example.android3lesson2.adapters.ViewPagerAdapter;
import com.example.android3lesson2.base.BaseFragment;
import com.example.android3lesson2.data.local.client.ViewPagerClient;
import com.example.android3lesson2.databinding.FragmentMainBoardBinding;
import com.example.android3lesson2.models.viewpager_model.ViewPagerModel;
import com.example.android3lesson2.utils.interfaces.OnPagerClickListener;
import com.example.android3lesson2.viewmodel.PixabayViewModel;

import java.util.ArrayList;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainBoardFragment extends BaseFragment<FragmentMainBoardBinding> implements OnPagerClickListener {


    ViewPagerAdapter adapter;
    ViewPagerClient client = new ViewPagerClient();
    PixabayViewModel viewModel;
    ArrayList<ViewPagerModel> list = new ArrayList<>();

    @Override
    public FragmentMainBoardBinding bind() {
        return FragmentMainBoardBinding.inflate(getLayoutInflater());
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(this).get(PixabayViewModel.class);
        initAdapter();
        initListeners();
        checkIfShown();
        enableButton();
    }

    private void enableButton() {
        binding.viewpager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
                if (position == 4) {
                    binding.btnGetStarted.setVisibility(View.VISIBLE);
                } else {
                    binding.btnGetStarted.setVisibility(View.GONE);
                }
            }

        });
    }

    private void initListeners() {
        binding.btnGetStarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewModel.setBoolean(true);
                Navigation.findNavController(requireView()).navigate(R.id.wordsFragment);
            }
        });
    }

    private void checkIfShown() {
        if (viewModel.getBoolean()) {
            Navigation.findNavController(requireView()).navigate(R.id.categoryFragment);
        } else {
            viewModel.setBoolean(false);
        }
    }

    @Override
    public void onClick(ViewPagerModel viewPagerModel) {

    }


    private void initAdapter() {
        adapter = new ViewPagerAdapter(client.getList(), this);
        binding.viewpager.setAdapter(adapter);
        binding.dotsIndicator.setViewPager2(binding.viewpager);

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}