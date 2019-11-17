package com.crazy4web.myapplication.ui.chat;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ChatFragment extends Fragment {


    RecyclerView recyclerView;
    RecyclerView.Adapter mAdaptor;
    RecyclerView.LayoutManager layoutManager;
    private String prefFile = "com.crazy4web.myapplication.userdata";
    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    String emailname;

    ArrayList<String> biz_name= new ArrayList<>();

    ArrayList<String> last_message= new ArrayList<>();

    JsonObject jsonObject;





    private ChatViewModel chatViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.activity_chat_fragment, container, false);

        recyclerView = root.findViewById(R.id.recyclerview_chat);

        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setHasFixedSize(false);

        SharedPreferences sp = getContext().getSharedPreferences("prefFile", Context.MODE_PRIVATE);

        emailname = sp.getString("emailName","Default");

        getmessages();


        return root;
    }

    private void getmessages() {

        firebaseFirestore.collection("messages").whereEqualTo("messageuserID",emailname).get().addOnSuccessListener(
                new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                        for (DocumentSnapshot document : queryDocumentSnapshots.getDocuments()) {

                            Map data = new HashMap();
                            data = document.getData();
                            jsonObject = new JsonObject();
                            data.forEach((key, value) -> {

                                jsonObject.addProperty(key.toString(),value.toString());

                            });

                            biz_name.add(jsonObject.get("messageUser").toString());
                            last_message.add(jsonObject.get("messageText").toString());


                            Log.d("jsonovject", jsonObject+"");
                        }

                        mAdaptor = new MyAdaptor(getContext(), biz_name, last_message);
                        recyclerView.setLayoutManager(layoutManager);
                        recyclerView.setAdapter(mAdaptor);

                    }



                });

    }


}
