package com.example.tungvu.completumnote.ToDoView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.example.tungvu.completumnote.DetailView.DetailItem;
import com.example.tungvu.completumnote.R;
import com.example.tungvu.completumnote.SQLiteHelper;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    SQLiteHelper db = new SQLiteHelper(this, null, null, 1);
    List<ToDoItem> toDoDBList;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    MainAdapter mainAdapter;
    private List toDoItems = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // http://stackoverflow.com/questions/14671897/super-oncreatesavedinstancestate
        super.onCreate(savedInstanceState);
        // http://stackoverflow.com/questions/24706348/what-is-setcontentviewr-layout-main
        setContentView(R.layout.activity_main);

        // Set color for toolbar title
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(Color.WHITE);

        toDoDBList = db.getAllToDos(); //Get a list of all ToDoItem object in database

        //adds every ToDoItem to the RecyclerView on start
        for (int i = 0; i < toDoDBList.size(); i++)
        {
            // toDoItems list contains all ToDoItem name in database, we don't need id in this list
            toDoItems.add(i, new ToDoItem(toDoDBList.get(i).getToDoItemName()));
        }

        //setup everything related to the RecylcerView
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(false);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
//        recyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL_LIST));
        mainAdapter = new MainAdapter(toDoItems, R.layout.todoitem, this);
        recyclerView.setAdapter(mainAdapter);
        //ItemTouchHelper is used for swipeToDismiss items
//        ItemTouchHelper.Callback callback =
//                new SimpleItemTouchHelperCallback(MainActivity.this, mainAdapter);
//        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
//        touchHelper.attachToRecyclerView(recyclerView);


        // Create fab object for floating add button
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater li = LayoutInflater.from(MainActivity.this);
                // Create a view object using layout inflater
                View promptsView = li.inflate(R.layout.prompts, null);

                // Create alert dialog object
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this, R.style.Theme_AppCompat_Light_Dialog_Alert);

                // Set view object to alert dialog
                alertDialogBuilder.setView(promptsView);
                final EditText userInput = (EditText) promptsView.findViewById(R.id.editTextDialogUserInput);
                alertDialogBuilder
                        .setCancelable(false)
                        .setPositiveButton("Add",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,int id) {
                                        //package a new ArrayList for all the detailItems into a gson string
                                        ArrayList<String> tempArray = new ArrayList<String>();
                                        Gson gson = new Gson();
                                        String inputString= gson.toJson(tempArray);
                                        //add created ToDoItem to the database
                                        db.addProduct(new ToDoItem(userInput.getText().toString()));
                                        toDoDBList = db.getAllToDos();
                                        if (toDoDBList.size() > 0) {
                                            //add created ToDoItem to the RecyclerView
                                            toDoItems.add(toDoDBList.size() - 1, new ToDoItem(toDoDBList.get(toDoDBList.size() - 1).getToDoItemName()));
                                            //create empty ArrayList for DetailItems associated with this ToDoItem to avoid null call when clicked and create column in database
                                            db.addDetailItemName(new DetailItem(inputString, inputString));
                                            // Update RecycleView with created ToDoItem
                                            mainAdapter.notifyItemInserted(toDoDBList.size() - 1);
                                        }else{
                                            toDoItems.add(0, new ToDoItem(toDoDBList.get(0).getToDoItemName()));
                                            db.addDetailItemName(new DetailItem(inputString, inputString));
                                            // Update RecycleView with created ToDoItem
                                            mainAdapter.notifyItemInserted(toDoDBList.size());
                                        }
                                    }
                                })
                        .setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,int id) {
                                        dialog.cancel();
                                    }
                                });
                alertDialogBuilder.show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
