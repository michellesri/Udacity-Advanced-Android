package com.example.android.erawanthai;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.erawanthai.db.CartItem;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class CartFragment extends Fragment {
    private RecyclerView recyclerView;
    private CartAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    public CartFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cart, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar((Toolbar) view.findViewById(R.id.toolbar));
        recyclerView = view.findViewById(R.id.cart_recycler_view);

        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(activity);
        recyclerView.setLayoutManager(layoutManager);

        MainActivity mainActivity = (MainActivity) getActivity();
        mAdapter = new CartAdapter();
        recyclerView.setAdapter(mAdapter);
        mainActivity.db.cartItemDao().getAll().observe(this, new Observer<List<CartItem>>() {
            @Override
            public void onChanged(List<CartItem> cartItems) {
                mAdapter.mDataset.clear();

                mAdapter.mDataset.addAll(cartItems);
                mAdapter.notifyDataSetChanged();
            }
        });

        FloatingActionButton fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringBuilder shareBody = new StringBuilder("Please order these items from Erawan Thai Cuisine: \n");
                for (CartItem c : mAdapter.mDataset) {
                    shareBody.append(c.name).append(" x ").append(c.quantity).append("\n");
                }

                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody.toString());
                startActivity(Intent.createChooser(sharingIntent, "Share via"));

            }
        });
    }

    public class CartAdapter extends RecyclerView.Adapter<CartAdapter.MyViewHolder> {
        public List<CartItem> mDataset = new ArrayList<>();

        // Provide a reference to the views for each data item
        // Complex data items may need more than one view per item, and
        // you provide access to all the views for a data item in a view holder
        public class MyViewHolder extends RecyclerView.ViewHolder {
            // each data item is just a string in this case
            public LinearLayout linearLayout;
            public MyViewHolder(LinearLayout v) {
                super(v);
                linearLayout = v;
            }
        }

        // Create new views (invoked by the layout manager)
        @Override
        public CartAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                           int viewType) {
            // create a new view
            LinearLayout v = (LinearLayout) LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.cart_item, parent, false);
            MyViewHolder vh = new MyViewHolder(v);
            return vh;
        }

        // Replace the contents of a view (invoked by the layout manager)
        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            // - get element from your dataset at this position
            // - replace the contents of the view with that element
            CartItem cartItem = mDataset.get(position);
            ((TextView) holder.linearLayout.findViewById(R.id.cart_item_quantity)).setText(getString(R.string.quantity) + (cartItem.quantity));
            ((TextView) holder.linearLayout.findViewById(R.id.cart_item_name)).setText(cartItem.name);
            ((TextView) holder.linearLayout.findViewById(R.id.cart_item_price)).setText(Utils.formatPrice(cartItem.price));

        }

        // Return the size of your dataset (invoked by the layout manager)
        @Override
        public int getItemCount() {
            return mDataset.size();
        }
    }
}
