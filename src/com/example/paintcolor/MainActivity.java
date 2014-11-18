package com.example.paintcolor;
  
  import android.os.Bundle;
  import android.util.Log;
  import android.widget.ImageView;
  import android.widget.SeekBar;
  import android.widget.SeekBar.OnSeekBarChangeListener;
  import android.app.Activity;
  import android.graphics.Bitmap;
 import android.graphics.BitmapFactory;
 import android.graphics.Canvas;
 import android.graphics.ColorMatrix;
 import android.graphics.ColorMatrixColorFilter;
 import android.graphics.Matrix;
 import android.graphics.Paint;
 
 public class MainActivity extends Activity {
     private SeekBar sb_red, sb_green, sb_blue,sb_alpha;
     private ImageView iv_show;
     private Bitmap afterBitmap;
     private Paint paint;
     private Canvas canvas;
     private Bitmap baseBitmap;

     @Override
     protected void onCreate(Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);
         setContentView(R.layout.activity_main);

         iv_show = (ImageView) findViewById(R.id.iv_show);
         sb_red = (SeekBar) findViewById(R.id.sb_red);
         sb_green = (SeekBar) findViewById(R.id.sb_green);
         sb_blue = (SeekBar) findViewById(R.id.sb_blue);
         sb_alpha = (SeekBar) findViewById(R.id.sb_alpha);
        
         sb_red.setOnSeekBarChangeListener(seekBarChange);
         sb_green.setOnSeekBarChangeListener(seekBarChange);
         sb_blue.setOnSeekBarChangeListener(seekBarChange);
         sb_alpha.setOnSeekBarChangeListener(seekBarChange);
 
         // 从资源文件中获取图片
         baseBitmap = BitmapFactory.decodeResource(getResources(),
                 R.drawable.ic_launcher);
         // 获取一个与baseBitmap大小一致的可编辑的空图片
         afterBitmap = Bitmap.createBitmap(baseBitmap.getWidth(),
                 baseBitmap.getHeight(), baseBitmap.getConfig());
         canvas = new Canvas(afterBitmap);
         paint = new Paint();
     }
 
     private SeekBar.OnSeekBarChangeListener seekBarChange = new OnSeekBarChangeListener() {
 
         @Override
         public void onStopTrackingTouch(SeekBar seekBar) {
             // 获取每个SeekBar当前的值
             float progressR = sb_red.getProgress()/128f;
             float progressG = sb_green.getProgress()/128f;
             float progressB = sb_blue.getProgress()/128f;
             float progressA=sb_alpha.getProgress()/128f;
             Log.i("main", "R：G：B="+progressR+"："+progressG+"："+progressB);
             // 根据SeekBar定义RGBA的矩阵
             float[] src = new float[]{
                     progressR, 0, 0, 0, 0, 
                     0, progressG, 0, 0, 0,
                     0, 0, progressB, 0, 0, 
                     0, 0, 0, progressA, 0};
             // 定义ColorMatrix，并指定RGBA矩阵
             ColorMatrix colorMatrix = new ColorMatrix();
             colorMatrix.set(src);
             // 设置Paint的颜色
             paint.setColorFilter(new ColorMatrixColorFilter(src));
             // 通过指定了RGBA矩阵的Paint把原图画到空白图片上
             canvas.drawBitmap(baseBitmap, new Matrix(), paint);
             iv_show.setImageBitmap(afterBitmap);
         }
 
         @Override
         public void onStartTrackingTouch(SeekBar seekBar) {
         }
 
         @Override
         public void onProgressChanged(SeekBar seekBar, int progress,
                 boolean fromUser) {
         }
     };
 }