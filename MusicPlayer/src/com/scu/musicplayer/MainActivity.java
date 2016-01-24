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
	//歌曲名稱
	String[] songname=new String[] {"Stuck in RED", "Be My Man", "Don't Wanna Be Good", "Daddy May I", "Was it Necessary", "But Youre Mine", "Aint the Madonna Aint Your Whore", "black", "Sleight of Hand"};
	//歌曲資源
	int[] songfile=new int[] {R.raw.stuck_in_red, R.raw.be_my_man, R.raw.don_t_wanna_be_good, R.raw.daddy_may_i, R.raw.was_it_necessary, R.raw.but_youre_mine, R.raw.aint_the_madonna_aint_your_whore, R.raw.black, R.raw.sleight_of_hand};
	private int cListItem=0; //目前播放歌曲
	private Boolean falgPause=false; //暫停、播放旗標
	
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //在 onCreate() 顯示播放器介面版面配置和取得ImageView, ListView, TextView元件
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
          	case R.id.imgFront:  //上一首
				frontSong();
            	break;
            case R.id.imgStop:  //停止
				if (mediaplayer.isPlaying()) { // 是否正在播放
					mediaplayer.reset(); //重置MediaPlayer
				}
              	break;
            case R.id.imgPlay:  //播放
            	if(falgPause) {  //如果是暫停狀態就繼續播放
            		mediaplayer.start();
            		falgPause=false;
            	} else  //非暫停則重新播放
            		playSong(songfile[cListItem]);
               	break;
            case R.id.imgPause:  //暫停
				mediaplayer.pause();
				falgPause=true;
            	break;
            case R.id.imgNext:  //下一首
				nextSong();
              	break;
            case R.id.imgEnd:  //結束
            	mediaplayer.release();
            	finish();
              	break;
           }
        }
 	};

    private ListView.OnItemClickListener lstListener=new ListView.OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
			cListItem = position; //取得點選位置
			playSong(songfile[cListItem]); //播放
		}
     };

 	private void playSong(int song) {
 		mediaplayer.reset();
		mediaplayer=MediaPlayer.create(MainActivity.this, song); //播放歌曲源
		try {
			mediaplayer.prepare();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		mediaplayer.start(); //開始播放
		txtMusic.setText("現正播放：" + songname[cListItem]); //更新歌名
		mediaplayer.setOnCompletionListener(new OnCompletionListener() {
			public void onCompletion(MediaPlayer arg0) {
				nextSong(); //播放完後播下一首
			}
		});
		falgPause=false;
 	}

	//下一首歌
	private void nextSong() {
		cListItem++;
		if (cListItem >= lstMusic.getCount()) //若到最後就移到第一首
			cListItem = 0;
		playSong(songfile[cListItem]);
	}
	
	//上一首歌/
	private void frontSong() {
		cListItem--;
		if (cListItem < 0)
			cListItem = lstMusic.getCount()-1; //若到第一首就移到最後
		playSong(songfile[cListItem]);
	}
}