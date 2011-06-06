package tjuhot.solapop.music;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.anddev.andengine.audio.music.Music;
import org.anddev.andengine.audio.music.MusicFactory;
import org.anddev.andengine.engine.Engine;
import org.anddev.andengine.opengl.texture.Texture;
import org.anddev.andengine.opengl.texture.region.TextureRegion;
import org.anddev.andengine.opengl.texture.region.TextureRegionFactory;

import tjuhot.solapop.ILevel;
import android.content.Context;
import android.graphics.Point;
import android.util.Log;

public class Level implements ILevel{
	Context m_context;
	Engine	m_engine;
	List<Long> m_beats;
	String m_slpFileName;
	String m_music;
	public Level(Context app,Engine eng,String slpFileName){
		m_context = app;
		m_engine = eng;
		m_slpFileName=slpFileName;
		List<Long > retv= new ArrayList<Long>();
		SlpFileReader music=new SlpFileReader(m_slpFileName);
		Log.d("BeatTime",slpFileName);
		Log.d("BeatTime",music.getArtist());
		int beatSize=music.beatSize();
		Log.d("BeatTime",Integer.toString(beatSize));
		for(int i=0;i<beatSize;++i){
			long l=music.BeatTime(i);
			Log.d("BeatTime",Long.toString(l));
			retv.add(l);
		}
		m_music = new File(slpFileName).getParent()+"/"+music.getMusic();
//		m_music = "res/raw/summer1.mp3";
		m_beats = retv;
	}
	public Music getMusic() {
		try {
			File musicFile=new File(m_music);
			Music retv = MusicFactory.createMusicFromFile(m_engine.getMusicManager(), m_context, musicFile);
			//Music retv =  MusicFactory.createMusicFromAsset(m_engine.getMusicManager()
			//		, m_context, m_music);
			assert(retv!=null);
			return retv;
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} 
		return null;
	}
	public List<Long> getBeats() {
		return m_beats;
	}
	
	
	public int getRatSize() {
		return 4;
	}
	public List<Point> getRats() {
		List<Point> retv = new ArrayList<Point>();
		retv.add(new Point(512, 300));
		retv.add(new Point(512+150,300+60));
		retv.add(new Point(512+150+160,300+60));
		retv.add(new Point(512+60,300+150));
		retv.add(new Point(512+60+170,300+150));
		retv.add(new Point(512+60+170+110,300+150));
		retv.add(new Point(400,300+75));
		return retv;
	}
	public TextureRegion getBackground() {
		Texture t = new Texture(2048, 1024); 
		TextureRegion retv =  TextureRegionFactory.createFromAsset(t, m_context
				, "stub/bg.png", 0, 0);
		m_engine.getTextureManager().loadTexture(t);
		return retv;
	}
}