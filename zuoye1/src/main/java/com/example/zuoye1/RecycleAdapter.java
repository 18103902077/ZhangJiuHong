package com.example.zuoye1;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.youth.banner.Banner;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.List;

public class RecycleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    List<BannerBean.DataBean> data =new ArrayList<>();
    List<DataBean> food  =new ArrayList<>();
    private PopupWindow popupWindow;
    int a;

    public RecycleAdapter(Context context) {
        this.context = context;
    }

    public void getBanner(List<BannerBean.DataBean> data){
        this.data.addAll(data);
        notifyDataSetChanged();
    }

    public void getFoodRefresh(List<DataBean> food ){
        this.food.clear();
        this.food.addAll(food);
        notifyDataSetChanged();
    }

    public void getFoodLoad(List<DataBean> food ){
        this.food.addAll(food);
        notifyDataSetChanged();
    }

    public void delete(){
        food.remove(food.get(a));
        notifyDataSetChanged();
    }

    public void deleteSql(){
        Utile.delete(food.get(a));
        food.remove(a);
        notifyDataSetChanged();
    }

    public void insert(){
        Utile.insert(context,food.get(a));
    }

    public void getPopup(PopupWindow popupWindow){
        this.popupWindow=popupWindow;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if (i==0){
            View view=View.inflate(context,R.layout.mybanner,null);
            return new ViewHolder0(view);
        }else {
            View view=View.inflate(context,R.layout.recadapter,null);
            return new ViewHolder1(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        int type = getItemViewType(i);
        if (type==0){
            ViewHolder0 viewHolder0= (ViewHolder0) viewHolder;
            viewHolder0.mybanner.setImages(data).setImageLoader(new MyImageLoad()).start();
        }else {

            if (data.size()>0){
                i--;
            }

            DataBean dataBean = food.get(i);
            ViewHolder1 viewHolder1= (ViewHolder1) viewHolder;
            viewHolder1.tv.setText(dataBean.getTitle());

            if (i%2==0){
                RequestOptions requestOptions = RequestOptions.circleCropTransform();
                Glide.with(context).load(dataBean.getPic()).apply(requestOptions).into(viewHolder1.img);
            }else {
                RoundedCorners roundedCorners = new RoundedCorners(20);
                RequestOptions requestOptions = RequestOptions.bitmapTransform(roundedCorners);
                Glide.with(context).load(dataBean.getPic()).apply(requestOptions).into(viewHolder1.img);
            }
        }
        final int finalI = i;
        viewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                a= finalI;
                if (popupWindow!=null){
                    popupWindow.showAtLocation(v, Gravity.CENTER,0,0);
                }
                return false;
            }
        });

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                a= finalI;
                insert();
            }
        });


    }

    @Override
    public int getItemCount() {
        if (data.size()>0){
            return food.size()+1;
        }else {
            return food.size();
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (data.size()>0&&position==0){
            return 0;
        }else {
            return 1;
        }
    }

    class ViewHolder0 extends RecyclerView.ViewHolder {
        Banner mybanner;
        public ViewHolder0(@NonNull View itemView) {
            super(itemView);
            mybanner=itemView.findViewById(R.id.mybanner);
        }
    }

    class ViewHolder1 extends RecyclerView.ViewHolder {
        ImageView img;
        TextView tv;
        public ViewHolder1(@NonNull View itemView) {
            super(itemView);
            img=itemView.findViewById(R.id.img);
            tv=itemView.findViewById(R.id.tv);
        }
    }

    class MyImageLoad extends ImageLoader {
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            BannerBean.DataBean paths= (BannerBean.DataBean) path;
            Glide.with(context).load(paths.getImagePath()).into(imageView);
        }
    }
}
