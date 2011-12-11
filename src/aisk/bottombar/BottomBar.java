package aisk.bottombar;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class BottomBar extends LinearLayout {
	private int mSelectedBtn;
	private ImageView mBtn1;
	private ImageView mBtn2;
	private ImageView mBtn3;
	private ImageView mBtn4;
	private Paint paint;
	private RectF curRectF;
	private RectF tarRectF;
	
	public BottomBar(Context context) {
		this(context, null);
	}
	
	public BottomBar(Context context, AttributeSet attrs) {
		super(context, attrs);
		setWillNotDraw(false);
		LayoutInflater.from(context).inflate(R.layout.bottom_bar, this, true);
		
		paint = new Paint();
		paint.setAntiAlias(true);
		
		mBtn1 = (ImageView)findViewById(R.id.btn1);
		mBtn2 = (ImageView)findViewById(R.id.btn2);
		mBtn3 = (ImageView)findViewById(R.id.btn3);
		mBtn4 = (ImageView)findViewById(R.id.btn4);
		
		TypedArray a = context.obtainStyledAttributes(attrs, 
				R.styleable.BottomBar);
		mSelectedBtn = a.getInt(R.styleable.BottomBar_selected_btn, 1);
		a.recycle();
		
		curRectF = null;
		tarRectF = null;
			
		View.OnClickListener clickBtn = new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				if (view == mBtn1)
					mSelectedBtn = 1;
				else if (view == mBtn2)
					mSelectedBtn = 2;
				else if (view == mBtn3)
					mSelectedBtn = 3;
				else if (view == mBtn4)
					mSelectedBtn = 4;
				//Log.d("Selected Button:", String.valueOf(mSelectedBtn));

				tarRectF.left = view.getLeft() + 10;
				tarRectF.right = view.getRight() - 10;
				tarRectF.top = view.getTop() - 2;
				tarRectF.bottom = view.getBottom() + 2;
				invalidate();
			}
		};
		
		mBtn1.setOnClickListener(clickBtn);
		mBtn2.setOnClickListener(clickBtn);
		mBtn3.setOnClickListener(clickBtn);
		mBtn4.setOnClickListener(clickBtn);
		
	}
	
	
	public void setSelectedBtn(int i) {
		mSelectedBtn = i;
	}
	
	public int getSelectedBtn() {
		return mSelectedBtn;
	}

	@Override
	public void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		//Log.d("Validated!", "Should not always validate");
		int step = getWidth()/30;
		canvas.drawColor(Color.BLACK);
		paint.setColor(Color.LTGRAY);
		
		if (curRectF == null)
			curRectF = new RectF(mBtn1.getLeft()+10, mBtn1.getTop()-2,
					mBtn1.getRight()-10, mBtn1.getBottom()+2);
		if (tarRectF == null)
			tarRectF = new RectF(mBtn1.getLeft()+10, mBtn1.getTop()-2, 
					mBtn1.getRight()-10, mBtn1.getBottom()+2);
		
		if (Math.abs(curRectF.left - tarRectF.left) < step) {
			curRectF.left = tarRectF.left;
			curRectF.right = tarRectF.right;
		}
		if (curRectF.left > tarRectF.left) {
			curRectF.left -= step;
			curRectF.right -= step;
			invalidate();
		}
		else if (curRectF.left < tarRectF.left) {
			curRectF.left += step;
			curRectF.right += step;
			invalidate();
		}
		canvas.drawRoundRect(curRectF, 5, 5, paint);
	}
	
}
