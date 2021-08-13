package com.example.videoplayer;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("ALL")
public class MyAdapter extends RecyclerView.Adapter<VideoHolder> {

    private Context context;
    //ArrayList<File> videoArrayList;
    private List<filemodel> mUploads;
    private ImageView imageThumbnail;

    public MyAdapter(Context context, List<filemodel> uploads) {
        this.context = context;
        mUploads = uploads;
    }

//    public MyAdapter(Context context, List<filemodel> videoArrayList) {
//        this.context = context;
//        this.videoArrayList = videoArrayList;
//    }

    @Override
    public VideoHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.video_item,viewGroup,false);
        return new VideoHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final VideoHolder videoHolder, int position) {
        filemodel uploadCurrent = mUploads.get(position);

        videoHolder.txtFileName.setText(uploadCurrent.getTitle());
//        Picasso.with(context)
//                .load(uploadCurrent.getVurl())
//                .fit()
//                .centerCrop()
//                .into(videoHolder.imageThumbnail);
       try {


            Bitmap bitmapThumbnail = ThumbnailUtils.createVideoThumbnail(uploadCurrent.getUrl(),
                    MediaStore.Video.Thumbnails.MINI_KIND);
            videoHolder.imageThumbnail.setImageBitmap(bitmapThumbnail);

       }
        catch (Exception e){
            e.printStackTrace();
            Log.e(String.valueOf(e),"error");
        }
        videoHolder.mCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,VideoPlayerActivity.class);
                intent.putExtra("position",videoHolder.getLayoutPosition());
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
//        RequestOptions requestOptions = new RequestOptions();
//        requestOptions.placeholder(R.drawable.jumping);
//        requestOptions.error(R.drawable.jumping);
//
//
//        Glide.with(context)
//                .load(uploadCurrent.getUrl())
//                .apply(requestOptions)
//                .thumbnail(Glide.with(context).load(uploadCurrent.getUrl()))
//                .into(imageThumbnail);


    }



    @Override
    public int getItemCount() {
        if(mUploads.size()>0){
            return mUploads.size();
        }
        else
            return 1;
    }
}

class VideoHolder extends RecyclerView.ViewHolder{

    TextView txtFileName;
    ImageView imageThumbnail;
    CardView mCardView;

    VideoHolder(View view){
        super(view);

        txtFileName = view.findViewById(R.id.rxr_videoFileName);
        imageThumbnail = view.findViewById(R.id.iv_thumnail);
        mCardView = view.findViewById(R.id.mCardView);


    }

}