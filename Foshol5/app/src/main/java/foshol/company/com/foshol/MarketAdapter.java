package foshol.company.com.foshol;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by User on 8/21/2017.
 */

public class MarketAdapter extends ArrayAdapter {
    private Activity context;
    List<Market> marketList;

    public MarketAdapter(Activity context, List<Market> marketList) {
        super(context, R.layout.market_list, marketList );
        context = context;
        this.marketList =marketList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = context.getLayoutInflater();

        View listViewItem = inflater.inflate(R.layout.market_list, null, true);

        TextView textViewName = (TextView) listViewItem.findViewById(R.id.textViewName);

        Market temp = marketList.get(position);

        textViewName.setText(temp.marketName);

        return listViewItem;

    }
}
