package ua.pi_13.echards;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by user on 11.12.2015.
 */
public class ItemListBandBaseAdapter extends BaseAdapter{
    private static ArrayList<ItemBand> itemBandArrayList;


    private LayoutInflater l_Inflater;

    public ItemListBandBaseAdapter(Context context, ArrayList<ItemBand> results) {
        itemBandArrayList = results;
        l_Inflater = LayoutInflater.from(context);
    }

    public int getCount() {
        return itemBandArrayList.size();
    }

    public Object getItem(int position) {
        return itemBandArrayList.get(position);
    }

    public long getItemId(int position){
        return position;
    }

    public View getView(final int position, View convertView, ViewGroup parent){
        ViewHolder holder;
        if (convertView == null) {
            convertView = l_Inflater.inflate(R.layout.item_bands_view, null);
            holder = new ViewHolder();
            holder.linearLayout = (LinearLayout) convertView.findViewById(R.id.linearLayoutHorizontal);
            holder.txt_bandName = (TextView) convertView.findViewById(R.id.textViewNameBand);
            holder.txt_countSong = (TextView) convertView.findViewById(R.id.textViewCountSongs);
            holder.imgbtn_edit_band =(ImageButton) convertView.findViewById(R.id.imgbtn_edit_band);
            holder.imgbtn_delete_band =(ImageButton) convertView.findViewById(R.id.imgbtn_delete_band);
            convertView.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.txt_bandName.setText(itemBandArrayList.get(position).getName());
        holder.txt_countSong.setText(Integer.toString(itemBandArrayList.get(position).getCounts()));

        final View finalConvertView = convertView;
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(finalConvertView.getContext(), AlbumsView.class);
                intent.putExtra("bandID", itemBandArrayList.get(position).get_ID());
                finalConvertView.getContext().startActivity(intent);
            }
        });

        holder.imgbtn_edit_band.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(finalConvertView.getContext(), EditingBand.class);
                intent.putExtra("Type", "Edit");
                intent.putExtra("ID", itemBandArrayList.get(position).get_ID().toString());
                intent.putExtra("NAME", itemBandArrayList.get(position).getName());
                finalConvertView.getContext().startActivity(intent);
            }
        });

        holder.imgbtn_delete_band.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:
                                DatabaseHelper mDatabaseHelper = new DatabaseHelper(finalConvertView.getContext());
                                SQLiteDatabase db = mDatabaseHelper.getWritableDatabase();
                                db.delete(mDatabaseHelper.DATATABLE_BANDS, "_id " + "=" + itemBandArrayList.get(position).get_ID(), null);
                                Intent intent = new Intent(finalConvertView.getContext(),finalConvertView.getContext().getClass());
                                finalConvertView.getContext().startActivity(intent);
                                break;

                            case DialogInterface.BUTTON_NEGATIVE:
                                break;
                        }
                    }
                };


                AlertDialog.Builder builder = new AlertDialog.Builder(finalConvertView.getContext());
                builder.setMessage("Вы уверены, что хотите удалить группу?").setPositiveButton("Да", dialogClickListener)
                        .setNegativeButton("Нет", dialogClickListener).show();
            }
        });


        return convertView;
    }

    static class ViewHolder{
        TextView txt_bandName;
        TextView txt_countSong;
        LinearLayout linearLayout;
        ImageButton imgbtn_edit_band;
        ImageButton imgbtn_delete_band;
    }
}
