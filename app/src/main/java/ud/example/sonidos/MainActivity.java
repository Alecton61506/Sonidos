package ud.example.sonidos;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    private SoundPool sPool;
    private int sound1, sound2, sound3, sound4;

    private MediaPlayer player;
    private AudioManager audiomanager;
    private Button boton3, boton4;
    private SeekBar volumen;
    private ProgressBar Progreso;
    private TextView total, hasonado, Audio;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){

            AudioAttributes attributes = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_ASSISTANCE_SONIFICATION)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .build();
            sPool = new SoundPool.Builder()
                    .setAudioAttributes(attributes)
                    .setMaxStreams(10)
                    .build();


        }else {
            sPool = new SoundPool( 6, AudioManager.STREAM_MUSIC, 0);
        }
        sound1 = sPool.load( this, R.raw.gallina, 1);
        sound2 = sPool.load( this, R.raw.perro, 1);
        sound3 = sPool.load( this, R.raw.leon, 1);
        sound4 = sPool.load( this, R.raw.serpiente, 1);

        audiomanager = (AudioManager) getSystemService(AUDIO_SERVICE);
        boton3 = findViewById(R.id.button3);
        boton4 = findViewById(R.id.button4);
        total = findViewById(R.id.textView3);
        hasonado = findViewById(R.id.textView4);
        volumen = findViewById(R.id.seekBar);
        Audio = findViewById(R.id.textView2);
        Progreso = findViewById(R.id.progressBar);


        volumen.setMax(audiomanager.getStreamMaxVolume(AudioManager.STREAM_MUSIC));

        volumen.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b){
                audiomanager.setStreamVolume(AudioManager.STREAM_MUSIC,i,0);
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar){}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar){}
        });
        traerVolumen();
    }
    public void suenaboton01(View v){sPool.play(sound1, 1, 1, 1, 0, 1);}
    public void suenaboton02(View v){sPool.play(sound2, 1, 1, 1, 2, 1);}

    public void suenaboton03(View v){
        int duracion=0;
        int avance=0;
        int actual=0;

        if (player != null && player.isPlaying()){
            player.stop();
            boton3.setText("Reproducir Mediaplayer");

        }else{
            player = MediaPlayer.create( this, R.raw.pharrell_williams_happy);
            //total.setText(String.valueOf(player.getDuration()));
            duracion=(player.getDuration()/1000); // borrar
            total.setText("Duracion Total = " + duracion +" Segundos");
            player.start();
            boton3.setText("Detener Mediaplayer");

            /*if(player.isPlaying()){    // borrar
            while(player.isPlaying()){ // borrar
                actual=player.getCurrentPosition(); //Borrar
                avance= (actual*100)/duracion; // borrar
                Progreso.setProgress(avance); // borrar
            }   // borrar
            }   // borrar*/



        }

    }
    public void suenaboton04(View v){
        int avance=0;
        int actual=0;
        if (player != null){
            if (player.isPlaying()){
                player.pause();
                boton4.setText("Reiniciar");
                hasonado.setText("Avance: " + (player.getCurrentPosition())/1000 + " Segundos");
                actual=player.getCurrentPosition(); //borrar
                avance= (actual*100)/240628; // borrar
                Progreso.setProgress(avance); // borrar


            }else{
                player.start();
                boton4.setText("Pausar");
            }
            }
    }

    public void suenabotonLeon(View v){sPool.play(sound3, 1, 1, 1, 0, 1);}
    public void suenabotonserpiente(View v){sPool.play(sound4, 1, 1, 1, 0, 1);}

protected void onDestroy(){
        super.onDestroy();
        sPool.release();
        sPool=null;
        player.release();

        player=null;
}

    private void traerVolumen() {
        try {
            volumen = (SeekBar)findViewById(R.id.seekBar);
            audiomanager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
            volumen.setMax(audiomanager.getStreamMaxVolume(AudioManager.STREAM_MUSIC));
            volumen.setProgress(audiomanager.getStreamVolume(AudioManager.STREAM_MUSIC));

            volumen.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onStopTrackingTouch(SeekBar arg0) {}

                @Override
                public void onStartTrackingTouch(SeekBar arg0) {}
                @Override
                public void onProgressChanged(SeekBar arg0, int progress, boolean arg2) {
                    audiomanager.setStreamVolume(AudioManager.STREAM_MUSIC, progress, 0);
                    Audio.setText("Volumen ="+progress);
                }
            });
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }//Cierre volumen

}
