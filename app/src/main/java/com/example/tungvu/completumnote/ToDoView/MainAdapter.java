package com.example.tungvu.completumnote.ToDoView;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.tungvu.completumnote.DetailView.DetailActivity;
import com.example.tungvu.completumnote.R;
import com.example.tungvu.completumnote.SQLiteHelper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Tung Vu Hoang Minh on 1/14/2017.
 */

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {

    private List<ToDoItem> toDoItems;
    private int rowLayout;
    private Context mContext;
    List<ToDoItem> dblist;
//    List<DetailItem> detailDBList;
    RecyclerView.Adapter mainAdapter;

    // Constructor of MainAdapter class
    public MainAdapter(List<ToDoItem> toDoItems, int rowLayout, Context context) {

        this.toDoItems = toDoItems;
        this.rowLayout = rowLayout;
        this.mContext = context;

    }

    // Method to create a ViewHolder
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(rowLayout, viewGroup, false);
        return new ViewHolder(v);
    }

    // onBindViewHolder is used to display the data at ViewHolder
    @Override
    public void onBindViewHolder(final ViewHolder viewHolder,  final int position) {
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DATE);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);
        String monthString;
        switch (month){
            case 0:
                monthString = "Jan";
                break;
            case 1:
                monthString = "Feb";
                break;
            case 2:
                monthString = "Mar";
                break;
            case 3:
                monthString = "Apr";
                break;
            case 4:
                monthString = "May";
                break;
            case 5:
                monthString = "Jun";
                break;
            case 6:
                monthString = "Jul";
                break;
            case 7:
                monthString = "Aug";
                break;
            case 8:
                monthString = "Sep";
                break;
            case 9:
                monthString = "Oct";
                break;
            case 10:
                monthString = "Nov";
                break;
            case 11:
                monthString = "Dec";
                break;
            default:
                monthString = "null";
                break;
        }
        final ToDoItem toDoItem = toDoItems.get(position);
        viewHolder.toDoText.setText(toDoItem.getToDoItemName());
        viewHolder.dateText.setText(monthString + " " + day + ", " + year);
        viewHolder.toDoText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                detailActivityStart(position);
            }
        });
        viewHolder.dateText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                detailActivityStart(position);
            }
        });
    }


    public void onItemDismiss(int position) {
        final ToDoItem toDoItem = toDoItems.get(position);
        SQLiteHelper db = new SQLiteHelper(mContext, null, null, 1);
        db.deleteProduct(toDoItem.getToDoItemName());
        toDoItems.remove(position);
        notifyItemRemoved(position);
        //removing detailItems
//        detailDBList = db.getAllDetailItems();
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList>() {}.getType();
//        ArrayList<String> tempArray = gson.fromJson(detailDBList.get(position).getDetailText(), type);
//        ArrayList<String> checkedTempArray = gson.fromJson(detailDBList.get(position).getCheckedState(), type);
//        for (int i = tempArray.size()-1; i >= 0; i--) {  //deincriment through the array to remove all items
//            tempArray.remove(i);
//            checkedTempArray.remove(i);
//        }
//        String inputString= gson.toJson(tempArray);
//        String checkedInputString= gson.toJson(checkedTempArray);
//        db.updateDetailItem(detailDBList.get(position), inputString, checkedInputString);
    }


    @Override
    public int getItemCount() {
        return toDoItems ==  null ? 0 : toDoItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView toDoText;
        public TextView dateText;

        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            toDoText = (TextView) itemView.findViewById(R.id.toDoText);
            dateText = (TextView) itemView.findViewById(R.id.dateText);
        }

        @Override
        public void onClick(View view) {
            detailActivityStart(getAdapterPosition());
        }
    }

    public void detailActivityStart(int position){
        Intent intent = new Intent(mContext, DetailActivity.class);
        intent.putExtra("Position", position);
        mContext.startActivity(intent);
    }
}
