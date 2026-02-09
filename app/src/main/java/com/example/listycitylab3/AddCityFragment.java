package com.example.listycitylab3;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class AddCityFragment extends DialogFragment {

    interface AddCityDialogListener {
        void addCity(City city);
        void cityUpdated();
    }

    private AddCityDialogListener listener;

    private static final String ARG_CITY = "city";
    private City city;

    public AddCityFragment() {
    }

    static AddCityFragment newInstance(City city) {
        Bundle args = new Bundle();
        args.putSerializable("city", city);

        AddCityFragment fragment = new AddCityFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof AddCityDialogListener) {
            listener = (AddCityDialogListener) context;
        } else {
            throw new RuntimeException(context + " must implement AddCityDialogListener");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        if (getArguments() != null) {
            city = (City) getArguments().getSerializable(ARG_CITY);
        }

        View view = LayoutInflater.from(getContext())
                .inflate(R.layout.fragment_add_city, null);

        EditText editCityName = view.findViewById(R.id.edit_text_city_text);
        EditText editProvinceName = view.findViewById(R.id.edit_text_province_text);

        if (city != null) {
            editCityName.setText(city.getName());
            editProvinceName.setText(city.getProvince());
        }

        return new AlertDialog.Builder(getContext())
                .setView(view)
                .setTitle(city == null ? "Add City" : "Edit City")
                .setNegativeButton("Cancel", null)
                .setPositiveButton("Save", (dialog, which) -> {
                    if (city == null) {
                        // ADD
                        listener.addCity(
                                new City(
                                        editCityName.getText().toString(),
                                        editProvinceName.getText().toString()
                                )
                        );
                    } else {
                        city.setName(editCityName.getText().toString());
                        city.setProvince(editProvinceName.getText().toString());
                        listener.cityUpdated();
                    }
                })
                .create();
    }
}
