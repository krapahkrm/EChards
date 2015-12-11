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
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by user on 10.12.2015.
 */
public class ItemListRiffBaseAdapter extends BaseAdapter {
    private static ArrayList<ItemRiff> itemRiffArrayList;


    private LayoutInflater l_Inflater;

    public ItemListRiffBaseAdapter(Context context, ArrayList<ItemRiff> results) {
        itemRiffArrayList = results;
        l_Inflater = LayoutInflater.from(context);
    }

    public int getCount() {
        return itemRiffArrayList.size();
    }

    public Object getItem(int position) {
        return itemRiffArrayList.get(position);
    }

    public long getItemId(int position){
        return position;
    }

    public View getView(final int position, View convertView, ViewGroup parent){
        ViewHolder holder;
        if (convertView == null) {
            convertView = l_Inflater.inflate(R.layout.item_riffs_view, null);
            holder = new ViewHolder();
            holder.textViewNameRiff = (TextView) convertView.findViewById(R.id.textViewNameRiff);
            holder.textViewDescribeRiff = (TextView) convertView.findViewById(R.id.textViewDescribeRiff);
            holder.imgbtn_edit_riff =(ImageButton) convertView.findViewById(R.id.imgbtn_edit_riff);
            holder.imgbtn_delete_riff =(ImageButton) convertView.findViewById(R.id.imgbtn_delete_riff);

            convertView.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.textViewNameRiff.setText(itemRiffArrayList.get(position).getName());
        holder.textViewDescribeRiff.setText(itemRiffArrayList.get(position).getParseLine());

        final View finalConvertView = convertView;
        holder.imgbtn_edit_riff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(finalConvertView.getContext(),EditingRiff.class);
                intent.putExtra("Type","Edit");
                intent.putExtra("ID",itemRiffArrayList.get(position).getID().toString());
                intent.putExtra("NAME",itemRiffArrayList.get(position).getName());
                intent.putExtra("DESCRIBE",itemRiffArrayList.get(position).getParseLine());
                finalConvertView.getContext().startActivity(intent);
            }
        });

        holder.imgbtn_delete_riff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:
                                DatabaseHelper mDatabaseHelper = new DatabaseHelper(finalConvertView.getContext());
                                SQLiteDatabase db = mDatabaseHelper.getWritableDatabase();
                                db.delete(mDatabaseHelper.DATATABLE_RIFFS, "_id " + "=" + itemRiffArrayList.get(position).getID(), null);
                                Intent intent = new Intent(finalConvertView.getContext(),finalConvertView.getContext().getClass());
                                finalConvertView.getContext().startActivity(intent);
                                break;

                            case DialogInterface.BUTTON_NEGATIVE:
                                break;
                        }
                    }
                };


                AlertDialog.Builder builder = new AlertDialog.Builder(finalConvertView.getContext());
                builder.setMessage("Вы уверены, что хотите удалить бой?").setPositiveButton("Да", dialogClickListener)
                        .setNegativeButton("Нет", dialogClickListener).show();
            }
        });

        return convertView;
    }

    static class ViewHolder{
        TextView textViewNameRiff;
        TextView textViewDescribeRiff;
        ImageButton imgbtn_edit_riff;
        ImageButton imgbtn_delete_riff;
    }
}
