package com.example.list_view_example;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    //Main activity attr
    private Button addBtn;
    private ListView listContainer;
    private Dialog dialog;
    private final ArrayList<String> toDoList = new ArrayList<>();
    //add dialog attr
    private EditText addItemText;
    private Button addTextBtn;
    private Button resetTextBtn;
    //edit dialog attr
    private EditText editSelected;
    private Button deleteBtn;
    private Button saveBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        /*
        * method start on app initiate
        * */
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listContainer = (ListView) findViewById(R.id.lvContainer);  //attach list view
        dialog = new Dialog(MainActivity.this); //initiate new dialog popup
        addBtn = (Button) findViewById(R.id.btnAdd);    //attach add button
        //attach click listener to add button
        addBtn.setOnClickListener((v) -> {
            /*
            * on button click:
            * initiate new dialog, attached with add_dialog
            * add new item to list view in case item is not empty
            * */
            dialog.setContentView(R.layout.add_dialog); //attach add dialog
            addItemText = (EditText) dialog.findViewById(R.id.etAddItem);   //attach Edit text
            addTextBtn = (Button) dialog.findViewById(R.id.btnAddText); //attach add button
            resetTextBtn = (Button) dialog.findViewById(R.id.btnResetText); //attach reset button
            //attach click listener to add button
            addTextBtn.setOnClickListener((e) -> {
                /*
                * in case edit text content is not empty
                * create new item in list
                * and adds it to a list view
                * */
                if(!addItemText.getText().toString().equals("")) {
                    toDoList.add(addItemText.getText().toString());
                    refreshList();
                    dialog.dismiss();
                }

            });
            //attach click listener to reset button
            resetTextBtn.setOnClickListener((e) -> {
                /*
                * reset all changes in edit text
                * */
                addItemText.setText("");
            });

            dialog.show();
            //set dialog window full width matching view parent
            Window window = dialog.getWindow();
            window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        });
        //attach click listener on each item in list view
        listContainer.setOnItemClickListener((parent, view, position, id) -> {
            /*
            * pop up new edit_dialog
            * where you can edit or delete clicked item
            * from list
            * */
            dialog.setContentView(R.layout.edit_dialog);    //attach edit dialog
            editSelected = (EditText) dialog.findViewById(R.id.etEditItem); //attach edit text
            deleteBtn = (Button) dialog.findViewById(R.id.btnDeleteText);   //attach delete button
            saveBtn = (Button) dialog.findViewById(R.id.btnSaveText);   //attach save button
            String value = ((TextView)view).getText().toString();   //save value from selected list item
            editSelected.setHint(value);    //set hint in edit text as selected item value
            //attach click listener on delete button
            deleteBtn.setOnClickListener((e) -> {
                /*
                * remove selected item from list
                * refresh list
                * */
                toDoList.remove(value);
                refreshList();
                dialog.dismiss();
            });
            //attach click listener on save button
            saveBtn.setOnClickListener((e) -> {
                /*
                * get new value from edit text
                * and set value to the list item selected
                * only in case edit text is not empty
                * */
                if (!editSelected.getText().toString().isEmpty()) {
                    toDoList.set(toDoList.indexOf(value), editSelected.getText().toString());
                    refreshList();
                }
                dialog.dismiss();
            });
            dialog.show();
            //set dialog window full width matching view parent
            Window window = dialog.getWindow();
            window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        });
    }
    private void refreshList() {
        /*
        * method to get invoked every time list has changed
        * */
        ArrayAdapter <String> adp = new ArrayAdapter<>(this,R.layout.row,toDoList);
        listContainer.setAdapter(adp);
        Log.d("log:,",toDoList.toString());
    }
}