package com.example.musicplayer;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>   {

    private String[] localDataSet;

    public static Context context;

    public static List<Music> mMusicList;



    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder)
     */
    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private final TextView textView;

        private final ImageView thumbnail;

        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View
            context = view.getContext();
            view.setOnClickListener(this);

            textView = (TextView) view.findViewById(R.id.textView);
            textView.setOnClickListener(this);

            thumbnail = (ImageView) view.findViewById(R.id.thumbnail);

            thumbnail.setOnClickListener(this);
        }

        public TextView getTextView() {
            return textView;
        }

        @Override
        public void onClick(View view) {
            int position = this.getAdapterPosition();
            Music music = mMusicList.get(position);
            //final Intent intent;
            //intent =  new Intent(context, MusicMethod.class);
            // context.startActivity(intent);
            String name = music.getName();

            Uri uri = music.getUri();
            MusicMethod musicMethod = new MusicMethod();
            musicMethod.playMusic(uri);
            Intent myIntent = new Intent(MainActivity.getContext(), MusicMethod.class);
            myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            myIntent.putExtra("SongName", name.replace(".mp3", "") );
            myIntent.putExtra("AlbumArtUri", music.getUri() );

            MainActivity.getContext().startActivity(myIntent);
        }
    }


    public RecyclerViewAdapter(List<Music> musicList) {
        mMusicList = musicList;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.text_row_item, viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        Music music = mMusicList.get(position);

        // Set item views based on your

        TextView textView = viewHolder.textView;
        String musicName = music.getName();
        if (musicName.length()>20){
            musicName =  musicName.substring(0, 20) + "...";
        }
        textView.setText(musicName.replace(".mp3", ""));

        ImageView imageView = viewHolder.thumbnail;
        imageView.setImageBitmap(music.getThumbnail());

    }

    // Replace the contents of a view (invoked by the layout manager)
    //@Override
   // public void onBindViewHolder(ViewHolder viewHolder, final int position) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
     //   viewHolder.getTextView().setText(localDataSet[position]);
    //}

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mMusicList.size();
    }
}
