package foshol.company.com.foshol;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by User on 9/15/2017.
 */

public class SellerAdapter extends ArrayAdapter<MarketNode> {

    private Activity context;
    private List<MarketNode> sellerList;

    public SellerAdapter(Activity context, List<MarketNode> sellerList) {
        super(context, R.layout.list_layout,sellerList);
        this.context=context;
        this.sellerList = sellerList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.list_layout,null,true);


        TextView textViewName=(TextView)listViewItem.findViewById(R.id.bazarName);

        MarketNode seller = sellerList.get(position);
        textViewName.setText(seller.getNaam());

        return  listViewItem;
    }
}
