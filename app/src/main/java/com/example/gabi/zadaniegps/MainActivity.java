package com.example.gabi.zadaniegps;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
//import com.google.android.gms.location.LocationListener;
//implements com.google.android.gms.location.LocationListener
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements LocationListener {

    TextView dostawca;
    TextView dlugosc;
    TextView szerokosc;

    Criteria cr;
    Location loc;
    String mojDostawca;

    LocationManager mylm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dostawca = (TextView) findViewById(R.id.poleDostawca);
        dlugosc = (TextView) findViewById(R.id.poleDlugosc);
        szerokosc = (TextView) findViewById(R.id.poleSzerokosc);

        //LocationManager mylm;
        mylm = (LocationManager) getSystemService(LOCATION_SERVICE);

        cr = new Criteria();

        //Przekazujemy wybranego dostawcę, wykorzystując obiekt klasy Criteria:

        mojDostawca = mylm.getBestProvider(cr, true);

        //oraz położenie wg. Wybranego dostawcy:
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        loc = mylm.getLastKnownLocation(mojDostawca);


        //Wyświetlmy informacje o lokalizacji:
        dostawca.setText("dostawca: " + mojDostawca);
        dlugosc.setText("dlugosc geograficzna: " + loc.getLongitude());
        szerokosc.setText("szerokosc geograficzna: " + loc.getLatitude());
    }///onCreate

    //Oczywiście chcemy aby aplikacja odświeżała informację o naszym położeniu:
    @Override
    protected void onResume() {
        super.onResume();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mylm.requestLocationUpdates(mojDostawca, 400, 1, this);
    }

    //Gdy aplikacja działa w tle nie potrzebujemy odświeżać informacji na temat lokalizacji:
    @Override
    protected void onPause() {
        super.onPause();
        mylm.removeUpdates(this);
    }

    //Przy zmianie lokalizacji wyświetlamy informacje w ten sam sposób jak przy tworzeniu aktywności:
    public void onLocationChanged(Location location) {
        Toast.makeText(this, "bajer", Toast.LENGTH_SHORT).show();
        mojDostawca = mylm.getBestProvider(cr, true);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        loc = mylm.getLastKnownLocation(mojDostawca);
        dostawca.setText("dostawca: "+mojDostawca);
        dlugosc.setText("dlugosc geograficzna: "+loc.getLongitude());
        szerokosc.setText("szerokosc geograficzna: "+loc.getLatitude());
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
