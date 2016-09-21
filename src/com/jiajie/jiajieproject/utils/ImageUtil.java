package com.jiajie.jiajieproject.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;

public class ImageUtil {


	// 获得圆角图片的方法
	public static Bitmap getRoundedCornerBitmap(Bitmap bitmap, float roundPx) {

		if (bitmap == null || roundPx == 0f)
			return bitmap;

		int min = Math.min(bitmap.getWidth(), bitmap.getHeight());

		int round = (int)(min * roundPx);
		
		int w = bitmap.getWidth();
		int h = bitmap.getHeight();

		Bitmap output = Bitmap.createBitmap(min, min, Config.ARGB_8888);

		Canvas canvas = new Canvas(output);

		final int color = 0xff424242;
		final Paint paint = new Paint();
		

		final Rect src = new Rect();

		src.left = (int) (w > h ? (w - h) / 2.0f : 0); // miDTX,//这个是可以改变的，也就是绘图的起点X位置
		src.top = (int) (w > h ? 0 : (h - w) / 2.0f); // mBitQQ.getHeight();//这个是QQ图片的高度。
														// 也就相当于 桌面图片绘画起点的Y坐标
		src.right = src.left + min; // miDTX + mBitDestTop.getWidth();//
									// 表示需绘画的图片的右上角
		src.bottom = src.top + min; // mBitQQ.getHeight() +
									// mBitDestTop.getHeight();//表示需绘画的图片的右下角

		final Rect dst = new Rect(0, 0, min, min);
		
		final RectF rectF = new RectF(dst);

		paint.setAntiAlias(true);
		canvas.drawARGB(0, 0, 0, 0);
		paint.setColor(color);
		canvas.drawRoundRect(rectF, round, round, paint);

		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		canvas.drawBitmap(bitmap, src, dst, paint);

		canvas.save();
		canvas.restore();
		bitmap.recycle();

		return output;
	}

	/*public static Bitmap createRepeater(Context mContext) {
		Bitmap src = BitmapFactory.decodeResource(mContext.getResources(),
				R.drawable.icon_juchi_tile);
		int tWidth = src.getWidth();
		int tHeight = src.getHeight();
		int count = (DisplayUtil.wh[0] - DisplayUtil.dipToPixel(30)) / tWidth;
		Bitmap bitmap = Bitmap.createBitmap(count * tWidth, tHeight,
				Config.ARGB_8888);
		Canvas canvas = new Canvas(bitmap);
		for (int idx = 0; idx < count; ++idx) {
			canvas.drawBitmap(src, idx * tWidth, 0, null);
		}
		return bitmap;
	}*/
}
