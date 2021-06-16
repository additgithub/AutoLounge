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

import com.additinfotech.autoloungein.R;
import com.aprilinnovations.autoloungeindia.activity.CarInfoActivity;
import com.aprilinnovations.autoloungeindia.datamodel.VariantModel;
import com.aprilinnovations.autoloungeindia.helper.UniversalHelper;

import java.util.List;

public class VariantAdapter extends RecyclerView.Adapter<VariantAdapter.MyViewHolder>   {

    List<VariantModel> variantModels;
    Context context;
    UniversalHelper helper;

    public VariantAdapter(List<VariantModel> variantModels, Context context) {
        this.variantModels = variantModels;
        this.context = context;
    }

    @NonNull
    @Override
    public VariantAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.variant_item, parent, false);

        return new VariantAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull VariantAdapter.MyViewHolder holder, int position) {

        Typeface createFromAsset = Typeface.createFromAsset(context.getAssets(), "fonts/HelveticaNeue Light.ttf");
        holder.tv_variantname.setTypeface(createFromAsset);

        final VariantModel model = variantModels.get(position);
        holder.tv_variantname.setText(model.getVariantName());
        holder.rv_variantitem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, CarInfoActivity.class );
                helper.savePreferences("comeFrom","varientActivity");
                i.putExtra("carDataId", model.getCarDataId());
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return variantModels.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_variantname;
        public RelativeLayout rv_variantitem;

        public MyViewHolder(View itemView) {
            super(itemView);
            helper = new UniversalHelper(context);
            tv_variantname =(TextView)itemView.findViewById(R.id.tv_variantname);
            rv_variantitem =(RelativeLayout) itemView.findViewById(R.id.rv_variantitem);

        }
    }
}
