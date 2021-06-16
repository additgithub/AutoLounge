package com.aprilinnovations.autoloungeindia.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.additinfotech.autoloungein.R;
import com.aprilinnovations.autoloungeindia.activity.CarDetailsActivity;
import com.aprilinnovations.autoloungeindia.datamodel.Car;
import com.bumptech.glide.Glide;

import java.util.List;

public class CarAdapter extends RecyclerView.Adapter<CarAdapter.MyViewHolder> {

    List<Car> carList;
    Context context;

    private final int normal = 0, flipped = 1;


    public CarAdapter(List<Car> carList, Context context) {
        this.carList = carList;
        this.context = context;
    }

    @Override
    public int getItemViewType(int position) {

        Car car = carList.get(position);

        if (position % 2 == 0){
            car.setLayout(true);
        }else if(position % 2 == 1){
            car.setLayout(false);
        }
        if (carList.get(position).getLayout() == true) {
            return normal;
        }
        else return flipped;
    }

    @NonNull
    @Override
    public CarAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.car_item, parent, false);

        View view = null;

        switch (viewType) {
            case normal:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.car_item, parent, false);
                break;

            case flipped:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.car_item_flipped, parent, false);
                break;
        }

        return new CarAdapter.MyViewHolder(view);

}

    @Override
    public void onBindViewHolder(@NonNull CarAdapter.MyViewHolder holder, int position) {

        Typeface createFromAsset = Typeface.createFromAsset(context.getAssets(), "fonts/HelveticaNeue Light.ttf");
        Typeface createFromAsset2 = Typeface.createFromAsset(context.getAssets(), "fonts/HelveticaNeue Medium.ttf");
        holder.item_tv_kmrun.setTypeface(createFromAsset);
        holder.item_tv_carnumber.setTypeface(createFromAsset2);
        holder.item_tv_carname.setTypeface(createFromAsset2);

        final Car car = carList.get(position);

        holder.item_tv_carname.setText(car.getCarBrand()+" "+car.getCarModel());
        holder.item_tv_carnumber.setText(car.getCarNumber());
        holder.item_tv_kmrun.setText(car.getKmRun());

        String carImage = car.getCarImageUrl();


        if (!TextUtils.isEmpty(car.getCarImageUrl())) {
            Glide.with(context).load(carImage).into(holder.iv_car);
        }
        holder.ll_car_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(context, CarDetailsActivity.class);
                i.putExtra("userCarDataId", car.getUserCarDataId());
                context.startActivity(i);

            }
        });

    }

    @Override
    public int getItemCount() {
        return carList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView item_tv_carname, item_tv_carnumber, item_tv_kmrun;
        public LinearLayout ll_car_item;
        public ImageView iv_car;
        public ImageView abc;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            item_tv_carname =(TextView)itemView.findViewById(R.id.item_tv_carname);
            item_tv_carnumber =(TextView)itemView.findViewById(R.id.item_tv_carnumber);
            item_tv_kmrun =(TextView)itemView.findViewById(R.id.item_tv_kmrun);
            ll_car_item =(LinearLayout) itemView.findViewById(R.id.ll_car_item);
            iv_car =(ImageView) itemView.findViewById(R.id.iv_car);
        }
    }
}
