/**
 * 
 */
package com.jiajie.jiajieproject.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**   
 * 项目名称：NewProject   
 * 类名称：MyGridView   
 * 类描述：   
 * 创建人：王蕾
 * 创建时间：2016-8-5 下午4:51:37   
 * 修改备注：    
 */
public class MyGridView extends GridView{  
    public MyGridView(Context context, AttributeSet attrs) {   
          super(context, attrs);   
      }   
     
      public MyGridView(Context context) {   
          super(context);   
      }   
     
      public MyGridView(Context context, AttributeSet attrs, int defStyle) {   
          super(context, attrs, defStyle);   
      }   
     
      @Override   
      public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {   
     
          int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,   
                  MeasureSpec.AT_MOST);   
          super.onMeasure(widthMeasureSpec, expandSpec);   
      }   
}  
