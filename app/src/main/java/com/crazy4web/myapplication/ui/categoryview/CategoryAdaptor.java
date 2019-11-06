package com.crazy4web.myapplication.ui.categoryview;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.crazy4web.myapplication.R;
import com.crazy4web.myapplication.ui.chat.MyAdaptor;


    public class CategoryAdaptor extends RecyclerView.Adapter<CategoryAdaptor.MyViewHolder> {

        private Context mcon;


        public static class MyViewHolder extends RecyclerView.ViewHolder {

            public CardView cardView;


            public MyViewHolder(View v) {

                super(v);

                cardView = v.findViewById(R.id.business_card_list);
            }
        }


        public CategoryAdaptor(Context con ) {


            mcon = con;

        }






        @Override
        public CategoryAdaptor.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_list_row, parent, false);



            MyViewHolder vh = new MyViewHolder(v);
            return vh;

        }

        @Override
        public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {




            holder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    // here I have to call the individual chats of the user on click
                    // I will leave it like this for now
                    Log.d("click","works");
//               Intent i = new Intent(mcon, MainActivity.class);




                }
            });



        }


        public int getItemCount() {

            return 10;

        }}



