package ua.pi_13.echards;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

/**
 * Created by user on 23.11.2015.
 */
public class ItemListChordBaseAdapter extends BaseAdapter {
    private static ArrayList<ItemChord> itemChordArrayList;


    private LayoutInflater l_Inflater;

    public ItemListChordBaseAdapter(Context context, ArrayList<ItemChord> results) {
        itemChordArrayList = results;
        l_Inflater = LayoutInflater.from(context);
    }

    public int getCount() {
        return itemChordArrayList.size();
    }

    public Object getItem(int position) {
        return itemChordArrayList.get(position);
    }

    public long getItemId(int position){
        return position;
    }

    public View getView(final int position, View convertView, ViewGroup parent){
        ViewHolder holder;
        if (convertView == null) {
            convertView = l_Inflater.inflate(R.layout.item_chords_view, null);
            holder = new ViewHolder();
            holder.txt_chordName = (TextView) convertView.findViewById(R.id.text_chord_name);
            holder.imgbtn_image = (ImageButton) convertView.findViewById(R.id.imgbtn_chord);
            holder.imgbtn_music =(ImageButton) convertView.findViewById(R.id.imgbtn_music);

            convertView.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.txt_chordName.setText(itemChordArrayList.get(position).getName());
        holder.imgbtn_image.setImageResource(StaticValue.chordsImageMap.get(itemChordArrayList.get(position).getImage()));
        final View finalConvertView1 = convertView;
        holder.imgbtn_music.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MediaPlayer mp = MediaPlayer.create(finalConvertView1.getContext(),StaticValue.chordsMusicMap.get(itemChordArrayList.get(position).getMusic()));
                mp.start();
            }
        });

        return convertView;
    }

    static class ViewHolder{
        TextView txt_chordName;
        ImageButton imgbtn_image;
        ImageButton imgbtn_music;
    }
}
