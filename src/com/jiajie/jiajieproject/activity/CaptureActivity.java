package com.jiajie.jiajieproject.activity;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Hashtable;
import java.util.Vector;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.ChecksumException;
import com.google.zxing.DecodeHintType;
import com.google.zxing.FormatException;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeReader;
import com.jiajie.jiajieproject.R;
import com.jiajie.jiajieproject.utils.RGBLuminanceSource;
import com.jiajie.jiajieproject.view.ViewfinderView;
import com.wyy.twodimcode.camera.CameraManager;
import com.wyy.twodimcode.decoding.CaptureActivityHandler;
import com.wyy.twodimcode.decoding.InactivityTimer;

/**
 * 项目名称：备件商城 类名称： CaptureActivity 类描述： 二维码扫描 创建人：王蕾 创建时间： 修改备注：
 * 添加开关手电功能，相册获取二维码，返回
 */
public class CaptureActivity extends BaseActivity implements Callback,
		OnClickListener {

	private CaptureActivityHandler handler;
	private ViewfinderView viewfinderView;
	private boolean hasSurface;// surface��û�б�����
	private Vector<BarcodeFormat> decodeFormats;
	private String characterSet;
	private InactivityTimer inactivityTimer;
	private MediaPlayer mediaPlayer;
	private boolean playBeep;
	private static final float BEEP_VOLUME = 0.10f;
	private boolean vibrate;// ���ɨ��ʱ�Ƿ�����ʾ
	private ImageView headerleftImg;
	private ImageView headerrighttext;
	// private FlashlightUtil flashlightUtil;
	private ProgressDialog progressDialog;
	private static final int SUCCESS = 0;
	private static final int FAIL = 1;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.twodimcode);
		CameraManager.init(getApplication());
		viewfinderView = (ViewfinderView) findViewById(R.id.viewfinder_view);
		hasSurface = false;
		InitView();
		inactivityTimer = new InactivityTimer(this);// activity��ֹһ��ʱ����Զ��ر�

	}

	private void InitView() {

		// 返回
		headerleftImg = (ImageView) findViewById(R.id.headerleftImg);
		// 相册
		headerrighttext = (ImageView) findViewById(R.id.headerrightImg);

		progressDialog = new ProgressDialog(this);
		headerleftImg.setOnClickListener(this);
		headerrighttext.setOnClickListener(this);
	}

	private SurfaceView surfaceVie;
	private SurfaceHolder surfaceHolder;

	@Override
	protected void onResume() {
		super.onResume();
		surfaceVie = (SurfaceView) findViewById(R.id.preview_view);
		surfaceHolder = surfaceVie.getHolder();
		if (hasSurface) {
			initCamera(surfaceHolder);
		} else {
			surfaceHolder.addCallback(this);
			surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		}
		decodeFormats = null;
		characterSet = null;

		playBeep = false;
		AudioManager audioService = (AudioManager) getSystemService(AUDIO_SERVICE);
		if (audioService.getRingerMode() != AudioManager.RINGER_MODE_NORMAL) {
			playBeep = false;
		}
		initBeepSound();
		vibrate = true;
	}

	@Override
	protected void onPause() {
		super.onPause();
		if (handler != null) {
			handler.quitSynchronously();
			handler = null;
		}
		// CameraManager.get().closeDriver();
	}

	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
		try {
			CameraManager.get().openDriver(surfaceHolder);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	protected void onDestroy() {
		CameraManager.get().closeDriver();
		inactivityTimer.shutdown();
		super.onDestroy();
	}

	// ��ʼ�������
	private void initCamera(SurfaceHolder surfaceHolder) {
		try {
			CameraManager.get().openDriver(surfaceHolder);
		} catch (IOException ioe) {
			return;
		} catch (RuntimeException e) {
			return;
		}
		if (handler == null) {
			handler = new CaptureActivityHandler(this, decodeFormats,
					characterSet);
		}
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {

	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		if (!hasSurface) {
			hasSurface = true;
			initCamera(holder);
		}

	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		hasSurface = false;

	}

	public ViewfinderView getViewfinderView() {
		return viewfinderView;
	}

	public Handler getHandler() {
		return handler;
	}

	public void drawViewfinder() {
		viewfinderView.drawViewfinder();

	}

	// ɨ�������
	public void handleDecode(Result obj, Bitmap barcode) {
		inactivityTimer.onActivity();
		viewfinderView.drawResultBitmap(barcode);// �����ͼƬ
		playBeepSoundAndVibrate();// ��������Ч��

		String str = obj.getText();
		System.out.println("lalalla:" + str);
		Intent it = new Intent();
		it.putExtra("result", str);
		setResult(RESULT_OK, it);
		finish();

	}

	// ��������
	private void initBeepSound() {
		if (playBeep && mediaPlayer == null) {
			setVolumeControlStream(AudioManager.STREAM_MUSIC);
			mediaPlayer = new MediaPlayer();
			mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
			mediaPlayer.setOnCompletionListener(beepListener);

			AssetFileDescriptor file = getResources().openRawResourceFd(
					R.raw.beep);
			try {
				mediaPlayer.setDataSource(file.getFileDescriptor(),
						file.getStartOffset(), file.getLength());
				file.close();
				mediaPlayer.setVolume(BEEP_VOLUME, BEEP_VOLUME);
				mediaPlayer.prepare();
			} catch (IOException e) {
				mediaPlayer = null;
			}
		}
	}

	private static final long VIBRATE_DURATION = 200L;

	// ������������
	private void playBeepSoundAndVibrate() {
		if (playBeep && mediaPlayer != null) {
			mediaPlayer.start();
		}
		if (vibrate) {
			Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
			vibrator.vibrate(VIBRATE_DURATION);
		}
	}

	/**
	 * When the beep has finished playing, rewind to queue up another one.
	 */
	private final OnCompletionListener beepListener = new OnCompletionListener() {
		public void onCompletion(MediaPlayer mediaPlayer) {
			mediaPlayer.seekTo(0);
		}
	};

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.headerleftImg:
			finish();
			break;
		case R.id.headerrightImg: 
			Intent intent = new Intent(
					Intent.ACTION_PICK,
					android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
			startActivityForResult(intent, 1000);
			break;

		}
		

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
		case 1000:
			if (data != null)
				startPhotoZoom(data.getData());
			break;
		case 2000:
			// 处理裁剪完后的二维码
			/*
			 * Uri selectedImage = data.getData(); String[] filePathColumn = {
			 * MediaStore.Images.Media.DATA }; Cursor cursor =
			 * getContentResolver().query(selectedImage, filePathColumn, null,
			 * null, null); cursor.moveToFirst(); int columnIndex =
			 * cursor.getColumnIndex(filePathColumn[0]); String picturePath =
			 * cursor.getString(columnIndex); cursor.close();
			 * Log.d("picturePath", picturePath); decode(picturePath);
			 */
			if (data != null) {
				Bundle extras = data.getExtras();
				if (extras != null) {

					final Bitmap photo = extras.getParcelable("data");
					decode(photo);
				}
			}
		}
	}

	private void decode(final Bitmap bitmap) {
		progressDialog.show();
		new Thread() {
			@Override
			public void run() {

				// Bitmap image = BitmapFactory.decodeFile(picturePath);
				// Bitmap bitmap = reduce(image, 50, 50, true);
				RGBLuminanceSource source = new RGBLuminanceSource(bitmap);
				BinaryBitmap binaryBitmap = new BinaryBitmap(
						new HybridBinarizer(source));
				Hashtable<DecodeHintType, String> hints = new Hashtable<DecodeHintType, String>();
				hints.put(DecodeHintType.CHARACTER_SET, "utf-8");
				QRCodeReader reader = new QRCodeReader();
				Result result = null;
				try {
					result = reader.decode(binaryBitmap, hints);
				} catch (NotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ChecksumException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (FormatException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} finally {
					reader.reset();
				}
				Message message = Message.obtain();
				if (result != null) {
					message.what = SUCCESS;
					message.obj = result.getText();
				} else {
					message.what = FAIL;
				}
				scanHandler.sendMessage(message);
			}
		}.start();

	}

	/**
	 * 压缩图片
	 * 
	 * @param bitmap
	 *            源图片
	 * @param width
	 *            想要的宽度
	 * @param height
	 *            想要的高度
	 * @param isAdjust
	 *            是否自动调整尺寸, true图片就不会拉伸，false严格按照你的尺寸压缩
	 * @return Bitmap
	 * @author wangyongzheng
	 */
	public Bitmap reduce(Bitmap bitmap, int width, int height, boolean isAdjust) {
		// 如果想要的宽度和高度都比源图片小，就不压缩了，直接返回原图
		if (bitmap.getWidth() < width && bitmap.getHeight() < height) {
			return bitmap;
		}
		// 根据想要的尺寸精确计算压缩比例, 方法详解：public BigDecimal divide(BigDecimal divisor,
		// int scale, int roundingMode);
		// scale表示要保留的小数位, roundingMode表示如何处理多余的小数位，BigDecimal.ROUND_DOWN表示自动舍弃
		float sx = new BigDecimal(width).divide(
				new BigDecimal(bitmap.getWidth()), 4, BigDecimal.ROUND_DOWN)
				.floatValue();
		float sy = new BigDecimal(height).divide(
				new BigDecimal(bitmap.getHeight()), 4, BigDecimal.ROUND_DOWN)
				.floatValue();
		if (isAdjust) {// 如果想自动调整比例，不至于图片会拉伸
			sx = (sx < sy ? sx : sy);
			sy = sx;// 哪个比例小一点，就用哪个比例
		}
		Matrix matrix = new Matrix();
		matrix.postScale(sx, sy);// 调用api中的方法进行压缩，就大功告成了
		return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
				bitmap.getHeight(), matrix, true);
	}

	private Handler scanHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			progressDialog.dismiss();
			if (msg.what == SUCCESS) {
				// 扫码内容
				String callbackMessage = (String) msg.obj;
				if (callbackMessage.contains("http:")) {
					Intent intent = new Intent();
					intent.setAction("android.intent.action.VIEW");
					Uri content_url = Uri.parse(callbackMessage);
					intent.setData(content_url);
					startActivity(intent);
				}else{ 
					//进入二维码列表
				}

//				Toast.makeText(CaptureActivity.this, "扫描结果：" + msg.obj,
//						Toast.LENGTH_LONG).show();
			} else {
				finish();
				Toast.makeText(CaptureActivity.this, "失败", Toast.LENGTH_LONG)
						.show();
			}

			super.handleMessage(msg);
		}

	};

	/**
	 * 裁剪图片方法实现
	 * 
	 * @param uri
	 */
	public void startPhotoZoom(Uri uri) {

		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");
		// 设置裁剪
		intent.putExtra("crop", "true");
		// aspectX aspectY 是宽高的比例
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		// outputX outputY 是裁剪图片宽高
		intent.putExtra("outputX", 100);
		intent.putExtra("outputY", 100);
		intent.putExtra("return-data", true);
		startActivityForResult(intent, 2000);
	}

}