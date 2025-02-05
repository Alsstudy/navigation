package com.gachon.innergation.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.gachon.innergation.R;
import com.gachon.innergation.adapter.ClassAdapter;
import com.google.firebase.firestore.FirebaseFirestore;

import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;

public class UserSearchActivity extends AppCompatActivity {

    private FirebaseFirestore firebaseFirestore;
    private ProgressDialog customProgressDialog;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private ClassAdapter classAdapter;
    private ArrayList<String> classrooms, filteredClassrooms;
    private EditText editDestination;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_search);

        //로딩창 객체 생성
        customProgressDialog = new ProgressDialog(this);
        customProgressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        customProgressDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);

        firebaseFirestore = FirebaseFirestore.getInstance();

        editDestination = findViewById(R.id.edit_destination);
        filteredClassrooms = new ArrayList<>();
        classrooms = new ArrayList<>();
        classrooms.add("401호");
        classrooms.add("402호");
        recyclerView = findViewById(R.id.recycler_classroom);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        classAdapter = new ClassAdapter(this, classrooms);
        classAdapter.setHasStableIds(true);

        recyclerView.setAdapter(classAdapter);
        editDestination.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String searchText = editDestination.getText().toString();
                searchFilter(searchText);
            }
        });
    }

    public void searchFilter(String searchText) {
        filteredClassrooms.clear();

        for (int i = 0; i < classrooms.size(); i++) {
            if (classrooms.get(i).toLowerCase().contains(searchText.toLowerCase())) {
                filteredClassrooms.add(classrooms.get(i));
            }
        }

        classAdapter.filterList(filteredClassrooms);
    }
}