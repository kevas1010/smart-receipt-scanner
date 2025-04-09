package com.example.myapplication;

import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class UserInfoFragment extends Fragment {

    private DatabaseHelper dbHelper;
    private EditText editTextHeight, editTextWeight, editTextAge;
    private Spinner spinnerGender;
    private Button btnSaveUserInfo;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_info, container, false);

        dbHelper = new DatabaseHelper(getContext());

        editTextHeight = view.findViewById(R.id.editTextHeight);
        editTextWeight = view.findViewById(R.id.editTextWeight);
        editTextAge = view.findViewById(R.id.editTextAge);
        spinnerGender = view.findViewById(R.id.spinnerGender);
        btnSaveUserInfo = view.findViewById(R.id.btnSaveUserInfo);

        btnSaveUserInfo.setOnClickListener(v -> saveUserInfo());

        return view;
    }

    private void saveUserInfo() {
        String heightStr = editTextHeight.getText().toString();
        String weightStr = editTextWeight.getText().toString();
        String ageStr = editTextAge.getText().toString();
        String gender = spinnerGender.getSelectedItem().toString();

        if (!heightStr.isEmpty() && !weightStr.isEmpty() && !ageStr.isEmpty()) {
            int height = Integer.parseInt(heightStr);
            int weight = Integer.parseInt(weightStr);
            int age = Integer.parseInt(ageStr);

            dbHelper.addUserInfo(height, weight, gender, age);
            Toast.makeText(getContext(), "User info saved!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getContext(), "Please fill out all fields", Toast.LENGTH_SHORT).show();
        }
    }

    // Retrieve user info for later use
    public Cursor getUserInfo() {
        return dbHelper.getUserInfoCursor();
    }
}
