package com.example.promoapp.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.promoapp.Activities.MainActivity;
import com.example.promoapp.Item;
import com.example.promoapp.ItemAdapter;
import com.example.promoapp.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

import java.util.ArrayList;

public class HomeFragment extends Fragment {
    private ListView listView;
    private ItemAdapter adpter;
    CarouselView carouselView;
    int[] sampleImages = {R.drawable.promo0, R.drawable.promo1, R.drawable.promo2, R.drawable.promo3};

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_home, container, false);
        listView = view.findViewById(R.id.listView);
        adpter = new ItemAdapter(view.getContext(),getArrayItems());
        listView.setAdapter(adpter);

        carouselView = view.findViewById(R.id.carouselView);
        carouselView.setPageCount(sampleImages.length);
        carouselView.setImageListener(imageListener);

        return view;
    }
    private ArrayList<Item> getArrayItems(){
        ArrayList<Item> listItems = new ArrayList<>();
        // Test
        /*listItems.add(new Item(R.drawable.domino,"DOMINOS","Una pizza gratis todos los dias","Hasta agotar existencias"));
        listItems.add(new Item(R.drawable.fox,"ARTICFOX","Todos los colores a mitad de precion","Hasta agotar existencias"));
        listItems.add(new Item(R.drawable.caffenio,"CAFFENIO","Refill gratis en la compra de cualquier cafe","Hasta agotar existencias"));
        listItems.add(new Item(R.drawable.xiaomi,"XIAOMI","Un corta unas gratis en la compra de cualquier otro producto de MI","Hasta agotar existencias"));
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference peopleReference = FirebaseDatabase.getInstance().getReference();
        Object people = peopleReference.child("user_promo").orderByChild("user").equalTo(uid);*/
        // End Test
        for(int i=0; i< MainActivity.list.size();i++){
            switch(MainActivity.list.get(i)){
                case "dominos":
                    listItems.add(new Item(R.drawable.domino,"DOMINOS","Una pizza gratis todos los dias","Hasta agotar existencias"));
                    break;
                case "fox":
                    listItems.add(new Item(R.drawable.fox,"ARTICFOX","Todos los colores a mitad de precion","Hasta agotar existencias"));
                    break;
                case "cafennio":
                    listItems.add(new Item(R.drawable.caffenio,"CAFFENIO","Refill gratis en la compra de cualquier cafe","Hasta agotar existencias"));
                    break;
                case "xiaomi":
                    listItems.add(new Item(R.drawable.xiaomi,"XIAOMI","Un corta unas gratis en la compra de cualquier otro producto de MI","Hasta agotar existencias"));
                    break;
            }
        }
        return listItems;
    }

    ImageListener imageListener = new ImageListener() {
        @Override
        public void setImageForPosition(int position, ImageView imageView) {
            imageView.setImageResource(sampleImages[position]);
        }
    };

}
