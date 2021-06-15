package com.aprilinnovations.autoloungeindia.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aprilinnovations.autoloungeindia.datamodel.BrandModel;
import com.aprilinnovations.autoloungeindia.R;
import com.aprilinnovations.autoloungeindia.activity.SelectModelActivity;
import com.aprilinnovations.autoloungeindia.helper.UniversalHelper;

import java.util.List;

public class BrandAdapter extends RecyclerView.Adapter<BrandAdapter.MyViewHolder> {

    List<BrandModel> brandModels;
    Context context;
    UniversalHelper helper;


    public BrandAdapter(List<BrandModel> brandModels, Context context) {
        this.brandModels = brandModels;
        this.context = context;
        this.helper = new UniversalHelper(context);

    }


    @NonNull
    @Override
    public BrandAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.brand_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull BrandAdapter.MyViewHolder holder, int position) {

        Typeface createFromAsset = Typeface.createFromAsset(context.getAssets(), "fonts/HelveticaNeue Light.ttf");
        holder.brand_name.setTypeface(createFromAsset);

        final BrandModel model = brandModels.get(position);
        holder.brand_name.setText(model.getBrandName());
        holder.rv_branditem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context,SelectModelActivity.class );
                //i.putExtra("carBrand", model.getBrandName());
                helper.savePreferences("carBrand", model.getBrandName());
                context.startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return brandModels.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView brand_name;
        public RelativeLayout rv_branditem;

        public MyViewHolder(View itemView) {
            super(itemView);

            brand_name =(TextView)itemView.findViewById(R.id.tv_brandname);
            rv_branditem =(RelativeLayout) itemView.findViewById(R.id.rv_branditem);

        }
    }
}
