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
 * Created by User on 26-Aug-17.
 */

public class OfficerList extends ArrayAdapter<Officernode> {
    private Activity context;
    private List<Officernode> officerlist;


    public OfficerList(Activity context, List<Officernode> officerlist) {
        super(context, R.layout.list_layout,officerlist);
        this.context=context;
        this.officerlist = officerlist;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.list_layout,null,true);
        TextView textViewStatus=(TextView)listViewItem.findViewById(R.id.textViewStatus);

        TextView textViewName=(TextView)listViewItem.findViewById(R.id.textViewName);

        Officernode officer = officerlist.get(position);
        textViewName.setText(officer.getNaam());
        textViewStatus.setText(officer.getStat());
        return  listViewItem;
    }
}
