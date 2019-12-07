package com.crazy4web.myapplication.ui.chat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.crazy4web.myapplication.R;
import com.crazy4web.myapplication.ui.dashboard.DashboardViewModel;
import com.crazy4web.myapplication.ui.notifications.RecyclerAdapter;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.annotation.Nullable;

public class ChatFragment extends Fragment {


    RecyclerView recyclerView;
    RecyclerView.Adapter mAdaptor;
    RecyclerView.LayoutManager layoutManager;
    private String prefFile = "com.crazy4web.myapplication.userdata";
    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    String emailname, userhascompany;
    String field;

    ArrayList<String> biz_name= new ArrayList<>();

    ArrayList<String> last_message= new ArrayList<>();

    JsonObject jsonObject;

    Set<String> msg, biz;




    private ChatViewModel chatViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.activity_chat_fragment, container, false);

        recyclerView = root.findViewById(R.id.recyclerview_chat);

        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setHasFixedSize(false);

        SharedPreferences sp = getContext().getSharedPreferences("prefFile", Context.MODE_PRIVATE);

        userhascompany = sp.getString("userhascompany","").replaceAll("\"","");
//        field = "messageUser";

//        emailname = sp.getString("emailName","Default");

        String googlename = sp.getString("googleName", "");
        String fbname = sp.getString("fbName", "");





        if(!userhascompany.equals("")){
            field="messageuserID";
            emailname= userhascompany;

        }else {

            if(!googlename.equals("")){
            emailname=googlename;
            }else if(!fbname.equals("")){
            emailname=fbname;
            }
//            emailname = sp.getString("emailName","Default");
//            Log.d("emailname", "getmessages: "+emailname);
                field = "messageUser";
        }




        getmessages();
        return root;
    }


    private void getmessages() {

        Log.d("fields",field+":"+emailname+":"+userhascompany);
        msg = new HashSet<>();
        biz = new HashSet<>();

        firebaseFirestore.collection("messages").whereEqualTo(field,emailname).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {

                        for (DocumentSnapshot document : queryDocumentSnapshots.getDocuments()) {

                            Map data = new HashMap();
                            data = document.getData();
                            jsonObject = new JsonObject();
                            data.forEach((key, value) -> {

                                jsonObject.addProperty(key.toString(), value.toString());

                            });
                            msg.add(jsonObject.get("messageText").toString());

                            // display username if it is a business
                            // and business name for users in the chat list
                            if (!userhascompany.equals("")){
                                biz.add(jsonObject.get("messageUser").toString());

                            }else {
                                biz.add(jsonObject.get("messageuserID").toString());
                            }

                        }


                            for (String msgs: msg) {
                                last_message.add(msgs);
                                Log.d("msgs",msgs);
                            }


                            for (String msgs: biz) {
                                biz_name.add(msgs);

                                Log.d("biz",msgs);
                            }

                        mAdaptor = new MyAdaptor(getContext(), biz_name, last_message);
                        recyclerView.setLayoutManager(layoutManager);
                        recyclerView.setAdapter(mAdaptor);

                    }



                });

    }


}
