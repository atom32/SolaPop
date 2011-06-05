package tjuhot.solapop;

import java.util.List;

import org.anddev.andengine.entity.Entity;
import org.anddev.andengine.entity.sprite.Sprite;
import org.anddev.andengine.opengl.texture.region.TextureRegion;

import android.util.Log;

public class Rat extends Entity {
	List<TextureRegion> mTextures;
	List<TextureRegion> mHitTextures;
	Sprite mSprite;
	int mPos;
	int mIncrese;
	private static final int INCRESE = 0;
	private static final int DECRESE = 1;
	private static final int HITTED = 2;
	private static final int NORAT = 3;
	private static final String DB_FILTER = "Solapop.Rat";

	void debug(String msg) {
		Log.d(DB_FILTER, msg);
	}

	public Rat(List<TextureRegion> aTextures, List<TextureRegion> aHitTextures) {
		super(0, 0);
		mTextures = aTextures;
		mHitTextures = aHitTextures;
		mPos = 0;
		mIncrese = INCRESE;
		mSprite = new Sprite(0, 0, mTextures.get(0));
		this.attachChild(mSprite);
	}

	public void showNext() {
		debug("Show Next");
		if(mIncrese == NORAT)return;
		if (mIncrese == INCRESE) {
			++mPos;
		} else if (mIncrese == DECRESE) {
			--mPos;
		}
		if (mPos >= mTextures.size() - 1) {
			mPos = mTextures.size() - 1;
			mIncrese = DECRESE;
		}
		if (mPos <= 0) {
			mPos = 0;
			//mIncrese = INCRESE;
			mIncrese = NORAT;
		}
		this.detachChild(mSprite);
		if (HITTED == mIncrese) {
			mIncrese = DECRESE;
			if(mPos ==0)
				return;
			mSprite = new Sprite(0, 0, mHitTextures.get(mPos-1));
		} else
			mSprite = new Sprite(0, 0, mTextures.get(mPos));
		this.attachChild(mSprite);
	}

	public int hit() {
		if(mIncrese == NORAT){
			return 0;
		}
		mIncrese = HITTED;
		return mPos;
	}
}
