package com.scu.musicplayer;

import java.io.IOException;

import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends Activity {
	private ImageView imagekassy, imgFront, imgStop, imgPlay, imgPause, imgNext, imgEnd;
	private ListView lstMusic;
	private TextView txtMusic;
	public MediaPlayer mediaplayer;
	//�q���W��
	String[] songname=new String[] {"Stuck in RED", "Be My Man", "Don't Wanna Be Good", "Daddy May I", "Was it Necessary", "But Youre Mine", "Aint the Madonna Aint Your Whore", "black", "Sleight of Hand"};
	//�q���귽
	int[] songfile=new int[] {R.raw.stuck_in_red, R.raw.be_my_man, R.raw.don_t_wanna_be_good, R.raw.daddy_may_i, R.raw.was_it_necessary, R.raw.but_youre_mine, R.raw.aint_the_madonna_aint_your_whore, R.raw.black, R.raw.sleight_of_hand};
	private int cListItem=0; //�ثe����q��
	private Boolean falgPause=false; //�Ȱ��B����X��
	
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //�b onCreate() ��ܼ��񾹤��������t�m�M���oImageView, ListView, TextView����
		imagekassy=(ImageView)findViewById(R.id.imagekassy);
        imgFront=(ImageView)findViewById(R.id.imgFront); 
		imgStop=(ImageView)findViewById(R.id.imgStop);
		imgPlay=(ImageView)findViewById(R.id.imgPlay); 
		imgPause=(ImageView)findViewById(R.id.imgPause);
		imgNext=(ImageView)findViewById(R.id.imgNext); 
		imgEnd=(ImageView)findViewById(R.id.imgEnd); 
		lstMusic=(ListView)findViewById(R.id.lstMusic); 
		txtMusic=(TextView)findViewById(R.id.txtMusic); 
		imgFront.setOnClickListener(listener);
		imgStop.setOnClickListener(listener);
		imgPlay.setOnClickListener(listener);
		imgPause.setOnClickListener(listener);
		imgNext.setOnClickListener(listener);
		imgEnd.setOnClickListener(listener);
    	lstMusic.setOnItemClickListener(lstListener);
    	mediaplayer=new MediaPlayer();
		ArrayAdapter<String> adaSong=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, songname);
		lstMusic.setAdapter(adaSong);
    }

    private ImageView.OnClickListener listener=new ImageView.OnClickListener() {
        @Override
        public void onClick(View v) {
          switch(v.getId())
          {
          	case R.id.imagekassy:
          		String url="https://raindoggs.com";          		 
          		Intent ie = new Intent(Intent.ACTION_VIEW,Uri.parse(url));          		 
          		startActivity(ie);
          		break;
          	case R.id.imgFront:  //�W�@��
				frontSong();
            	break;
            case R.id.imgStop:  //����
				if (mediaplayer.isPlaying()) { // �O�_���b����
					mediaplayer.reset(); //���mMediaPlayer
				}
              	break;
            case R.id.imgPlay:  //����
            	if(falgPause) {  //�p�G�O�Ȱ����A�N�~�򼽩�
            		mediaplayer.start();
            		falgPause=false;
            	} else  //�D�Ȱ��h���s����
            		playSong(songfile[cListItem]);
               	break;
            case R.id.imgPause:  //�Ȱ�
				mediaplayer.pause();
				falgPause=true;
            	break;
            case R.id.imgNext:  //�U�@��
				nextSong();
              	break;
            case R.id.imgEnd:  //����
            	mediaplayer.release();
            	finish();
              	break;
           }
        }
 	};

    private ListView.OnItemClickListener lstListener=new ListView.OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
			cListItem = position; //���o�I���m
			playSong(songfile[cListItem]); //����
		}
     };

 	private void playSong(int song) {
 		mediaplayer.reset();
		mediaplayer=MediaPlayer.create(MainActivity.this, song); //����q����
		try {
			mediaplayer.prepare();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		mediaplayer.start(); //�}�l����
		txtMusic.setText("�{������G" + songname[cListItem]); //��s�q�W
		mediaplayer.setOnCompletionListener(new OnCompletionListener() {
			public void onCompletion(MediaPlayer arg0) {
				nextSong(); //���񧹫Ἵ�U�@��
			}
		});
		falgPause=false;
 	}

	//�U�@���q
	private void nextSong() {
		cListItem++;
		if (cListItem >= lstMusic.getCount()) //�Y��̫�N����Ĥ@��
			cListItem = 0;
		playSong(songfile[cListItem]);
	}
	
	//�W�@���q/
	private void frontSong() {
		cListItem--;
		if (cListItem < 0)
			cListItem = lstMusic.getCount()-1; //�Y��Ĥ@���N����̫�
		playSong(songfile[cListItem]);
	}
}