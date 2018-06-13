package frontieres.sans.medecins.patientsatisfaction;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.recyclerview.extensions.ListAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GraficsActivity extends AppCompatActivity {

    ArrayList<ArrayList <String>> ANSWERS = new ArrayList<ArrayList<String>>() ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grafics);

        ListView ls = (ListView) findViewById(R.id.list) ;
        ArrayList <ArrayList<String>> ANSWERS = new ArrayList<>();
        ArrayList<String>P = new ArrayList<>(Arrays.asList("patient","doctor"));
        ANSWERS.add (P) ;
        ArrayList<BarData> dat = new ArrayList<>() ;
        for (int i = 0 ; i < ANSWERS.size() ; i++) dat.add(generateData(ANSWERS.get(i))) ;

        BarChartaddabter bar = new BarChartaddabter(this,dat) ;
        ls.setAdapter(bar);




    }
private class BarChartaddabter extends ArrayAdapter<BarData> {
    private BarChartaddabter(Context context , List<BarData> W){
        super (context ,0 , W) ;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
      ViewHolder holder = null ;
      BarData data = getItem(position) ;
      if (convertView == null){
          holder = new ViewHolder() ;
          convertView = getLayoutInflater().from(getContext()).inflate(R.layout.grafics ,null) ;
          holder.chart = (BarChart) convertView.findViewById(R.id.chart) ;
          convertView.setTag(holder);
      }
      else {
          holder = (ViewHolder) convertView.getTag();
      }
    data.setValueTextColor(Color.BLACK);
      holder.chart.getDescription().setEnabled(false);
      holder.chart.setDrawGridBackground(false);

        XAxis xAxis = holder.chart.getXAxis() ;
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);

        YAxis leftAxis = holder.chart.getAxisLeft();
        leftAxis.setLabelCount(5,false);
        leftAxis.setSpaceTop(15f);

        YAxis rightAxis = holder.chart.getAxisRight();
        rightAxis.setLabelCount(5,false);
        rightAxis.setSpaceTop(15f);

        holder.chart.setData(data);
        holder.chart.setFitBars(true);

        holder.chart.animateY(500);
        return convertView ;

    }
    private class ViewHolder {
         BarChart chart ;
    }
}

private BarData generateData (ArrayList <String> a) {
        ArrayList<BarEntry> entries = new ArrayList<>() ;
        String ph = "";
        for (int i1 = 0 ; i1 < a.size() ; i1++){
            ph =ph + "/"+ (String) a.get(i1) ;
            entries.add(new BarEntry(i1 ,50f)) ;
        }
        BarDataSet dataSet = new BarDataSet(entries,ph) ;
        dataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        dataSet.setBarShadowColor(Color.rgb(203,203,203));

        BarData data = new BarData(dataSet) ;
        data.setBarWidth(0.9F);
        return data ;
    }

}
