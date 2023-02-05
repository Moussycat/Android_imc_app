package com.example.projet_final;

import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.jjoe64.graphview.series.PointsGraphSeries;

public class thirdActivity extends AppCompatActivity {
    GraphView graphView;
    //add PointsGraphSeries of DataPoint type
    PointsGraphSeries<DataPoint> imcValues;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

        //toolbar
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar2);
        setSupportActionBar(myToolbar);
        //activation bouton up
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
        //button return
        myToolbar.setNavigationOnClickListener(clickToolbar);
        //graph
        graphView=(GraphView) findViewById(R.id.graphiqueView);
        //Draw the low Serie of points
        LineGraphSeries<DataPoint> low = new LineGraphSeries<DataPoint>(new DataPoint[]{
                new DataPoint(20,250),
                new DataPoint(30,250),
                new DataPoint(40,250),
                new DataPoint(50,250),
                new DataPoint(60,250),
                new DataPoint(70,250),
                new DataPoint(80,250),
                new DataPoint(90,250),
                new DataPoint(100,250),
                new DataPoint(110,250),
                new DataPoint(120,250),
                new DataPoint(130,250)
        });
        low.setBackgroundColor(Color.argb(255,0,0,255));
        low.setDrawBackground(true);
        low.setDrawDataPoints(false); //hide point
        graphView.addSeries(low);
        //Draw the good Serie of points
        LineGraphSeries<DataPoint> good = new LineGraphSeries<DataPoint>(new DataPoint[]{
                new DataPoint(20,103.97),
                new DataPoint(30,127.34),
                new DataPoint(40,147.04),
                new DataPoint(50,164.39),
                new DataPoint(60,180.09),
                new DataPoint(70,194.51),
                new DataPoint(80,207.95),
                new DataPoint(90,220.56),
                new DataPoint(100,232.49),
                new DataPoint(110,243.8),
                new DataPoint(120,254.68),
                new DataPoint(130,265.08),
                new DataPoint(140,275.09),
                new DataPoint(150,284.74),
                new DataPoint(160,284.74),
                new DataPoint(170,284.74),
                new DataPoint(180,284.74),
                new DataPoint(190,284.74),
                new DataPoint(200,284.74),
                new DataPoint(210,284.74),
                new DataPoint(220,284.74),
                new DataPoint(230,284.74),
                new DataPoint(240,284.74),
                new DataPoint(250,284.74)
        });
        good.setBackgroundColor(Color.argb(255,0,255,0));
        good.setDrawBackground(true);
        good.setDrawDataPoints(false); //hide point
        graphView.addSeries(good);
        //Draw the overweight serie of points
        LineGraphSeries<DataPoint> overweight = new LineGraphSeries<DataPoint>(new DataPoint[]{
                new DataPoint(20,89.44),
                new DataPoint(30,109.54),
                new DataPoint(40,126.49),
                new DataPoint(50,141.42),
                new DataPoint(60,154.91),
                new DataPoint(70,167.33),
                new DataPoint(80,178.88),
                new DataPoint(90,189.73),
                new DataPoint(100,200),
                new DataPoint(110,209.76),
                new DataPoint(120,219.08),
                new DataPoint(130,228.03),
                new DataPoint(140,236.64),
                new DataPoint(150,244.94),
                new DataPoint(160,252.98),
                new DataPoint(170,260.76),
                new DataPoint(180,268.32),
                new DataPoint(190,275.68),
                new DataPoint(200,282.84),
                new DataPoint(210,289.82)
        });
        overweight.setBackgroundColor(Color.argb(255,255,255,0));
        overweight.setDrawBackground(true);
        overweight.setDrawDataPoints(false); //hide point
        graphView.addSeries(overweight);
        //Draw the obesity serie of points
        LineGraphSeries<DataPoint> obesity = new LineGraphSeries<DataPoint>(new DataPoint[]{
                new DataPoint(20,81.64),
                new DataPoint(30,100),
                new DataPoint(40,115.47),
                new DataPoint(50,129.09),
                new DataPoint(60,141.42),
                new DataPoint(70,152.75),
                new DataPoint(80,163.29),
                new DataPoint(90,173.20),
                new DataPoint(100,182.57),
                new DataPoint(110,191.4),
                new DataPoint(120,200),
                new DataPoint(130,208.16),
                new DataPoint(140,216.02),
                new DataPoint(150,223.60),
                new DataPoint(160,230.94),
                new DataPoint(170,238.04),
                new DataPoint(180,244.94),
                new DataPoint(190,251.66),
                new DataPoint(200,258.19),
                new DataPoint(210,264.57),
                new DataPoint(220,270.80),
                new DataPoint(230,276.88),
                new DataPoint(240,282.84),
                new DataPoint(250,288.67)
        });
        obesity.setBackgroundColor(Color.argb(255,255,0,0));
        obesity.setDrawBackground(true);
        obesity.setDrawDataPoints(false);//hide point
        graphView.addSeries(obesity);

        Bundle bundle = getIntent().getExtras();
        String poids= bundle.getString("Poids");
        String taille = bundle.getString("Taille");

        imcValues=new PointsGraphSeries(new DataPoint[]{
                new DataPoint(Double.parseDouble(poids),Double.parseDouble(taille))
        });

        graphView.addSeries(imcValues);
        //manuel limits of XY axis
        graphView.getViewport().setYAxisBoundsManual(true);
        graphView.getViewport().setMinY(100);
        graphView.getViewport().setMaxY(250);
        graphView.getViewport().setXAxisBoundsManual(true);
        graphView.getViewport().setMinX(20);
        graphView.getViewport().setMaxX(250);

    }
    //bp up retour
    final View.OnClickListener clickToolbar = new View.OnClickListener() {// clic sur le bouton Up
        @Override
        public void onClick(View v) {
            Intent IMCActivity = new Intent(thirdActivity.this, secondActivity.class);
            startActivity(IMCActivity);
        }
    };

}