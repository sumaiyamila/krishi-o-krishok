package foshol.company.com.foshol;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by User on 25-Aug-17.
 */

public class KrishokList extends ArrayAdapter<Krishoknode> {
    private Activity context;
    private List<Krishoknode> krishoklist;


    public KrishokList(Activity context, List<Krishoknode> krishoklist) {
        super(context, R.layout.krishok_list_layout,krishoklist);
        this.context=context;
        this.krishoklist = krishoklist;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.krishok_list_layout,null,true);
        TextView textViewStatus=(TextView)listViewItem.findViewById(R.id.textViewStatus);

        TextView textViewName=(TextView)listViewItem.findViewById(R.id.textViewName);

        Krishoknode krishok = krishoklist.get(position);
        textViewName.setText(krishok.getNaam());
        textViewStatus.setText(krishok.getStat());
        return  listViewItem;
    }
}

