package com.example.promoapp.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ListView;

import com.example.promoapp.Activities.MainActivity;
import com.example.promoapp.Item;
import com.example.promoapp.ItemAdapter;
import com.example.promoapp.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public class HomeFragment extends Fragment {
    private ListView listView;
    private ItemAdapter adpter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_home, container, false);
        listView = view.findViewById(R.id.listView);
        adpter = new ItemAdapter(view.getContext(),getArrayItems());
        listView.setAdapter(adpter);


        return view;
    }
    private ArrayList<Item> getArrayItems(){
        ArrayList<Item> listItems = new ArrayList<>();
        for(int i=0; i< MainActivity.list.size();i++){
            switch(MainActivity.list.get(i)){
                case "dominos":
                    listItems.add(new Item(0,"DOMINOS","Una pizza gratis todos los dias","Hasta agotar existencias"));
                    break;
                case "fox":
                    listItems.add(new Item(0,"ARTICFOX","Todos los colores a mitad de precion","Hasta agotar existencias"));
                    break;
                case "cafennio":
                    listItems.add(new Item(0,"CAFFENIO","Refill gratis en la compra de cualquier cafe","Hasta agotar existencias"));
                    break;
                case "xiaomi":
                    listItems.add(new Item(0,"XIAOMI","Un corta unas gratis en la compra de cualquier otro producto de MI","Hasta agotar existencias"));
                    break;
            }
        }
        return listItems;
    }

}
