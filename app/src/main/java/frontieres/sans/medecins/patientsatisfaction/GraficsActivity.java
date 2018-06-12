package frontieres.sans.medecins.patientsatisfaction;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.recyclerview.extensions.ListAdapter;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GraficsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grafics);

        ArrayList<String> col1 = new ArrayList<String>(Arrays.asList("Buenos Aires", "Córdoba", "La Plata")) ;
        ArrayList<String> col2 = new ArrayList<String>(Arrays.asList("Buenos Aires", "Córdoba", "La Plata")) ;
        ArrayList<String> col3 = new ArrayList<String>(Arrays.asList("Buenos Aires", "Córdoba", "La Plata")) ;
        ArrayList<ArrayList <String>> ANSWERS = new ArrayList<ArrayList<String>>() ;
        ANSWERS.add(col1) ;
        ANSWERS.add(col2) ;
        ANSWERS.add(col3) ;

        BarChart barChart = (BarChart) findViewById(R.id.chart);

        List<BarEntry> entries = new ArrayList<>();
        entries.add(new BarEntry(0f, 30f));
        entries.add(new BarEntry(1f, 80f));
        entries.add(new BarEntry(2f, 60f));
        entries.add(new BarEntry(3f, 50f));
        // gap of 2f
        entries.add(new BarEntry(5f, 70f));
        entries.add(new BarEntry(6f, 60f));

        BarDataSet set = new BarDataSet(entries, "BarDataSet");

ArrayList <String> labels = new ArrayList<String>() ;
        labels.add("wow") ;
        labels.add("wow1") ;
        labels.add("wow2") ;
        labels.add("wow3") ;
        labels.add("wow4") ;
        labels.add("wow5") ;

        //barChart.setData(theData);


        BarData data = new BarData(set);
        data.setBarWidth(0.9f); // set custom bar width
        BarChart chart = findViewById(R.id.chart);
        chart.setData(data);
        chart.setFitBars(true); // make the x-axis fit exactly all bars
        chart.invalidate(); // refresh

    }
/*private class BarChartaddabter extends ListAdapter<BarData> {
    public BarChartaddabter(Context context , List<BarData> W){
        super (context ,0 , W) ;
    }
}*/
}
