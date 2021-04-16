package pl.myzia.zapisodczyt;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    private EditText nazwa;
    private EditText zawartosc;
    private Spinner tryb;
    private TextView komunikat;
    private TextView tresc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nazwa = (EditText) findViewById(R.id.editText_nazwa);
        zawartosc = (EditText) findViewById(R.id.editText_zawartosc);
        tryb = (Spinner) findViewById(R.id.spinner_tryb);
        komunikat = (TextView) findViewById(R.id.textView_komunikat);
        tresc = (TextView) findViewById(R.id.textView_tresc);
    }
    private boolean czyNazwa(){
        if (nazwa.getText().toString().equals("")){
            komunikat.setText(R.string.brakNazwy);
            return false;
        }
        else{
            return true;
        }
    }

    private boolean czyZawartosc(){
        if (zawartosc.getText().toString().equals("")){
            komunikat.setText(R.string.brakZawartosci);
            return false;
        }
        else{
            return true;
        }
    }

    public void onZapisClick(View view){
        if (czyNazwa() && czyZawartosc()){
            int uprawnienia = (int)tryb.getSelectedItemId();
            FileOutputStream fos = null;
            try {
                //openFileOutput() – umożliwia zapis w prywatnym pliku,
                fos = openFileOutput(nazwa.getText().toString() + ".txt", uprawnienia);
                fos.write(zawartosc.getText().toString().getBytes());
                tresc.setText(R.string.textView_komunikat);
            }
            catch (Exception e){
                komunikat.setText(R.string.Blad);
            }
            finally {
                if (fos != null){
                    try {
                        fos.flush();
                        fos.close();
                    } catch (IOException e){
                        //we ignore
                    }
                }
            }
        }
    }

    public void onOdczytClick(View view){
        if (czyNazwa()){
            FileInputStream fis = null;
            try{
                //openFileInput() – umożliwia odczyt prywatnego pliku,
                fis = openFileInput(nazwa.getText().toString() + ".txt");
                byte[] reader = new byte[fis.available()];
                while (fis.read(reader) != -1) {}
                tresc.setText(new String(reader));
            }
            catch (Exception e){
                komunikat.setText(R.string.Blad);
            }
            finally {
                if (fis != null) {
                    try {
                        fis.close();
                    }
                    catch (Exception e) {
                        //we also ignore
                    }
                }
            }
        }
    }



}