package foshol.company.com.foshol;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.google.firebase.database.ValueEventListener;

import java.util.List;

/**
 * Created by User on 25-Aug-17.
 */

public class SellerList extends ArrayAdapter {
    private Activity context;
    private List<MarketNode>sellerlist;


    public SellerList(Activity context, List<MarketNode> sellerlist) {
        super(context, R.layout.list_layout,sellerlist);
        this.context=context;
        this.sellerlist = sellerlist;
    }





    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        View listViewItem = inflater.inflate(R.layout.list_layout, null, true);

        TextView textViewName = (TextView) listViewItem.findViewById(R.id.bazarName);


        MarketNode temp = sellerlist.get(position);

        textViewName.setText(temp.getShopname());

        return listViewItem;
    }
}
