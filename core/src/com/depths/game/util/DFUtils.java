package com.depths.game.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class DFUtils {
	private static String[] fnames;
	private static String[] lnames;
	private static Pixmap pmap;

	/**
	 *  Converts HSV values to RGBA
	 * @param hue The hue input value
	 * @param saturation The saturation of the colour
	 * @param value the value of the colour
	 * @param alpha the alpha to output with RGB
	 * @return The RGBA value
	 */
	public static Color hsvToRgba(float hue, float saturation, float value, float alpha) {

	    int h = (int)(hue * 6);
	    float f = hue * 6 - h;
	    float p = value * (1 - saturation);
	    float q = value * (1 - f * saturation);
	    float t = value * (1 - (1 - f) * saturation);

	    switch (h) {
	      case 0: return new Color(value, t, p,alpha);
	      case 1: return new Color(q, value, p,alpha);
	      case 2: return new Color(p, value, t,alpha);
	      case 3: return new Color(p, q, value,alpha);
	      case 4: return new Color(t, p, value,alpha);
	      case 5: return new Color(value, p, q,alpha);
	      default: throw new RuntimeException("Something went wrong when converting from HSV to RGB. Input was " + hue + ", " + saturation + ", " + value);
	    }
	}

	/** converts RGB 0-1 to hex string e.g. FFFFFF
	 * @param r red value 0-1
	 * @param g green value 0-1
	 * @param b blue value 0-1
	 * @return RGB in HEX
	 */
	public static String rgbToString(float r, float g, float b) {
	    String rs = Integer.toHexString((int)(r * 256));
	    String gs = Integer.toHexString((int)(g * 256));
	    String bs = Integer.toHexString((int)(b * 256));
	    return rs + gs + bs;
	}
	
	/**
	 * Generates a random name using 2 text files in the assets folder
	 * @return random name
	 */
	public static String generateRandomName(){
		String name = "";
		if(fnames == null){
			FileHandle fnfile = Gdx.files.internal("fname.txt");
			fnames = fnfile.readString().split("\n");
			
			FileHandle lnfile = Gdx.files.internal("lname.txt");
			lnames = lnfile.readString().split("\n");
		}
		int fni = (int)(Math.random() * fnames.length);
		name = fnames[fni].trim();
		
		int lni = (int)(Math.random() * lnames.length);
		name += "_"+lnames[lni].trim();
		
		return name;
	}
	
	/**
	 * Quick access to console logging
	 * @param o
	 */
	public static void log(Object o){
		System.out.println(o);
	}
	
	public static Texture makeTexture(int width, int height, String hex){
		if(hex.length() == 6 ){
			hex+="FF";
		}
		return makeTexture(width,height,Color.valueOf(hex));
	}
	
	public static TextureRegion makeTextureRegion(int width, int height, String hex){
		if(hex.length() == 6 ){
			hex+="FF";
		}
		return makeTextureRegion(width,height,Color.valueOf(hex));
	}
	
	public static TextureRegion makeTextureRegion(int width, int height, Color col){
		TextureRegion tex = new TextureRegion(makeTexture(width,height,col));
		return tex;
	}
	
	public static Texture makeTexture(int width, int height, Color col){
		Texture tex;
		tex = new Texture(makePixMap(width,height,col));
		disposePmap();
		return tex;
	}
	
	private static Pixmap makePixMap(int width, int height, Color fill){
		pmap = new Pixmap(width, height,Format.RGBA8888);
		pmap.setColor(fill);
		pmap.fill();
		return pmap;
	}
	
	private static void disposePmap(){
		pmap.dispose();
	}
	
}