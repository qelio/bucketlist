package com.anapa.bucketlist;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import com.anapa.bucketlist.databinding.MyBottomSheetDialogBinding;

public class MyBottomSheet extends BottomSheetDialogFragment {

    private MyBottomSheetDialogBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = MyBottomSheetDialogBinding.inflate(getLayoutInflater());

        binding.dismissBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        binding.showToastBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "This is toast from my bottom sheet dialog fragment", Toast.LENGTH_SHORT).show();
            }
        });

        return binding.getRoot();
    }
}

