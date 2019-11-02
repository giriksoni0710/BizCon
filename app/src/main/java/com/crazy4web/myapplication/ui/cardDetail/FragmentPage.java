//package com.crazy4web.myapplication.ui.cardDetail;
//
//import android.os.Bundle;
//import android.text.Html;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.LinearLayout;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.fragment.app.Fragment;
//
//import com.crazy4web.myapplication.R;
//
//import org.w3c.dom.Text;
//
//public class FragmentPage extends Fragment {
//
//    @Nullable
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater,
//                             @Nullable ViewGroup container,
//                             @Nullable Bundle savedInstanceState) {
//        final View view;
//        Bundle bundle = getArguments();
//        int pageNumber = bundle.getInt("pageNumber");
//        Log.d("String",""+pageNumber);
//
//        view = inflater.inflate(R.layout.activity_detail_page_fragment_layout,container, false);
//        TextView textView = view.findViewById(R.id.pageNumber);
//        textView.setText(Integer.toString(pageNumber));
//
//        return view;
//        //we will return a view that we want to inflate
//        //return super.onCreateView(inflater, container, savedInstanceState);
//    }
//}
//
