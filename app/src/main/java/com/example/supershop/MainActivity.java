package com.example.supershop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.supershop.data.Book;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static final int MENU_ID_ADD = 1;
    public static final int MENU_ID_UPDATE = 2;
    public static final int MENU_ID_DELETE = 3;

    private ArrayList<Book> books;   //书籍列表
    private MainRecycleViewAdapter mainRecycleViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerViewMain=findViewById(R.id.recyclerview_main);

        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerViewMain.setLayoutManager(linearLayoutManager);

        books =new ArrayList<>();

        //添加书籍
        books.add(new Book(R.drawable.photo_1,"红楼梦"));
        books.add(new Book(R.drawable.photo_2,"活着"));
        books.add(new Book(R.drawable.photo_3,"狂人日记"));
        //String []mainDataSet=new String[]{
        //       "iterm 1","iterm 2","iterm 3","iterm 4",
        //        "iterm 5","iterm 6","iterm 7","iterm 8"
        //};
        mainRecycleViewAdapter= new MainRecycleViewAdapter(books);
        recyclerViewMain.setAdapter(mainRecycleViewAdapter);

    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case MENU_ID_ADD:
                books.add(item.getOrder(),new Book(R.drawable.ic_launcher_background,"书名"));
                //item.getOrder()在对应位置上添加数据
                mainRecycleViewAdapter.notifyItemInserted(item.getOrder());
                break;
            case MENU_ID_UPDATE:
                books.get(item.getOrder()).setTitle("update");
                mainRecycleViewAdapter.notifyDataSetChanged(item.getOrder());
                break;
            case MENU_ID_DELETE:
                AlertDialog alertDialog=new AlertDialog.Builder(this)
                        .setTitle("confirmation")
                        .setMessage("确定删除这本书吗？")
                        .setPositiveButton("是", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                books.remove(item.getOrder());
                                mainRecycleViewAdapter.notifyItemRangeRemoved(item.getOrder());
                            }
                        }).setNegativeButton("否", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        }).create();
                alertDialog.show();
                break;
        }
        return super.onContextItemSelected(item);
    }

    public class MainRecycleViewAdapter extends RecyclerView.Adapter<MainRecycleViewAdapter.ViewHolder>{
        private ArrayList<Book> localDataSet;

        public void notifyItemRangeRemoved(int order) {
        }

        public void notifyDataSetChanged(int order) {
        }

        public class ViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
            private final TextView textView;
            private final ImageView imageViewImage;

            public ViewHolder(View view){
                super(view);

                imageViewImage=view.findViewById(R.id.image);
                textView=view.findViewById(R.id.text);

                view.setOnCreateContextMenuListener(this);
            }

            public TextView getTextView() {
                return textView;
            }

            public ImageView getImageViewImage() {
                return imageViewImage;
            }

            @Override
            public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
                contextMenu.add(0,MENU_ID_ADD, getAdapterPosition(),"Add "+getAdapterPosition());
                contextMenu.add(0,MENU_ID_UPDATE, getAdapterPosition(),"Update "+getAdapterPosition());
                contextMenu.add(0,MENU_ID_DELETE, getAdapterPosition(),"Delete "+getAdapterPosition());
            }
        }
        public MainRecycleViewAdapter(ArrayList<Book> dataSet){
            localDataSet=dataSet;
        }
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            View view= LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.item_main,viewGroup,false);

            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder( ViewHolder viewHolder,final int position) {
            viewHolder.getImageViewImage().setImageResource(localDataSet.get(position).getId());
            viewHolder.getTextView().setText(localDataSet.get(position).getTitle());
        }

        @Override
        public int getItemCount() {
            return localDataSet.size();
        }



    }

}