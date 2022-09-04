package com.example.template.support.helper;

import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.template.support.base.global.BaseApplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.graphics.drawable.DrawableCompat;


/**
 * Created by Horrarndoo on 2022/8/31.
 * <p>
 * 资源帮助类
 */
public class ResourceHelper {
    private static final int DEFAULT_BACKGROUND_COLOR = Color.parseColor("#FFFFFFFF");
    private static final int DEFAULT_COLOR = Color.parseColor("#FF000000");
    private static final float DEFAULT_FLOAT_VALUE = 1.0f;

    private ResourceHelper() {
    }

    /**
     * 设置theme
     *
     * @param resId theme 资源id
     */
    public static void setTheme(int resId) {
        BaseApplication.getContext().setTheme(resId);
    }

    /**
     * 获取当前主题
     *
     * @return 当前主题
     */
    public static Resources.Theme getCurrentTheme() {
        return BaseApplication.getContext().getTheme();
    }

    /**
     * 根据attr属性id获得typeValue对象
     *
     * @param attrId 资源属性对应的id
     * @return attrId对应的TypeValue
     */
    @Nullable
    private static TypedValue obtainTypedValue(int attrId) {
        Resources.Theme theme = getCurrentTheme();
        if (theme == null)
            return null;
        TypedValue typedValue = new TypedValue();
        try {
            theme.resolveAttribute(attrId, typedValue, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return typedValue;
    }

    /**
     * 根据atrId获得Identifier资源id
     *
     * @param attrId 资源属性对应id
     * @return attrId对应的Identifier资源id
     */
    public static int getIdentifierByAttrId(int attrId) {
        TypedValue typedValue = obtainTypedValue(attrId);
        return typedValue == null ? 0 : typedValue.resourceId;
    }

    /**
     * 根据attrId获得typedArray属性集合
     *
     * @param attrId 资源属性对应的id
     * @return attrId对应的typedArray属性集合
     */
    @Nullable
    private static TypedArray innerGetTypedArrayByAttr(int attrId) {
        TypedValue typedValue = obtainTypedValue(attrId);
        if (typedValue == null)
            return null;
        TypedArray ta = null;
        try {
            ta = BaseApplication.getContext().getResources().obtainTypedArray(typedValue.resourceId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ta;
    }

    /**
     * 根据attrId获得typedArray属性集合
     *
     * @param attrId 资源属性对应的id
     * @return attrId对应的typedArray属性集合
     */
    public static TypedArray getTypedArrayByAttr(int attrId) {
        return innerGetTypedArrayByAttr(attrId);
    }

    /**
     * 根据attrId获得color颜色值
     *
     * @param attrId       资源属性对应的id
     * @param defaultColor 默认颜色值
     * @return attrId对应的color颜色值
     */
    private static int innerGetColorByAttr(int attrId, int defaultColor) {
        TypedValue typedValue = obtainTypedValue(attrId);
        if (typedValue == null)
            return defaultColor;
        try {
            defaultColor = ResourcesCompat.getColor(BaseApplication.getContext().getResources(),
                    typedValue.resourceId, getCurrentTheme());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return defaultColor;
    }

    /**
     * 根据attrId获得color颜色值
     *
     * @param attrId       资源属性对应的id
     * @param defaultColor 默认颜色值
     * @return attrId对应的color颜色值
     */
    public static int getColorByAttr(int attrId, int defaultColor) {
        return innerGetColorByAttr(attrId, defaultColor);
    }

    /**
     * 根据attrId获得color颜色值
     *
     * @param attrId 资源属性对应的id
     * @return attrId对应的color颜色值
     */
    public static int getColorByAttr(int attrId) {
        return innerGetColorByAttr(attrId, DEFAULT_COLOR);
    }

    /**
     * 根据attrId获取Drawable对象
     *
     * @param attrId          资源属性对应id
     * @param defaultDrawable 默认drawable
     * @return attrId对应的Drawable对象
     */
    private static Drawable innerGetDrawableByAttr(int attrId, Drawable defaultDrawable) {
        TypedValue typedValue = obtainTypedValue(attrId);
        if (typedValue == null) {
            return defaultDrawable;
        }
        try {
            defaultDrawable =
                    ResourcesCompat.getDrawable(BaseApplication.getContext().getResources(),
                            typedValue.resourceId, getCurrentTheme());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return defaultDrawable;
    }

    /**
     * 根据attrId获取Drawable对象
     *
     * @param attrId          资源属性对应id
     * @param defaultDrawable 默认drawable
     * @return attrId对应的Drawable对象
     */
    public static Drawable getDrawableByAttr(int attrId, Drawable defaultDrawable) {
        return innerGetDrawableByAttr(attrId, defaultDrawable);
    }

    /**
     * 根据attrId获取Drawable对象
     *
     * @param attrId 资源属性对应id
     * @return attrId对应的Drawable对象
     */
    public static Drawable getDrawableByAttr(int attrId) {
        return innerGetDrawableByAttr(attrId, null);
    }

    /**
     * 根据attrId获取ColorStateList对象
     *
     * @param attrId                资源属性对应id
     * @param defaultColorStateList 默认ColorStateList
     * @return attrId对应的ColorStateList对象
     */
    private static ColorStateList innerGetColorStateListByAttr(int attrId, ColorStateList
            defaultColorStateList) {
        TypedValue typedValue = obtainTypedValue(attrId);
        if (typedValue == null)
            return defaultColorStateList;
        try {
            defaultColorStateList =
                    ResourcesCompat.getColorStateList(BaseApplication.getContext().getResources()
                            , typedValue.resourceId, getCurrentTheme());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return defaultColorStateList;
    }

    /**
     * 根据attrId获取ColorStateList对象
     *
     * @param attrId                资源属性对应id
     * @param defaultColorStateList 默认ColorStateList
     * @return attrId对应的ColorStateList对象
     */
    public static ColorStateList getColorStateListByAttr(int attrId, ColorStateList
            defaultColorStateList) {
        return innerGetColorStateListByAttr(attrId, defaultColorStateList);
    }

    /**
     * 根据attrId获取ColorStateList对象
     *
     * @param attrId 资源属性对应id
     * @return attrId对应的ColorStateList对象
     */
    public static ColorStateList getColorStateListByAttr(int attrId) {
        return innerGetColorStateListByAttr(attrId, null);
    }

    /**
     * 根据attrId获取float的值
     *
     * @param attrId       资源属性对应的id
     * @param defaultFloat 默认float值
     * @return attrId对应的float的值
     */
    private static float innerGetFloatByAttrId(int attrId, float defaultFloat) {
        TypedValue typedValue = obtainTypedValue(attrId);
        if (typedValue == null)
            return defaultFloat;
        try {
            defaultFloat = typedValue.getFloat();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return defaultFloat;
    }

    /**
     * 根据attrId获取float的值
     *
     * @param attrId       资源属性对应的id
     * @param defaultFloat 默认float值
     * @return attrId对应的float的值
     */
    public static float getFloatByAttrId(int attrId, float defaultFloat) {
        return innerGetFloatByAttrId(attrId, defaultFloat);
    }

    /**
     * 根据attrId获取float的值
     *
     * @param attrId 资源属性对应的id
     * @return attrId对应的float的值
     */
    public static float getFloatByAttrId(int attrId) {
        return getFloatByAttrId(attrId, DEFAULT_FLOAT_VALUE);
    }

    /**
     * 根据attrId获取dimen值
     *
     * @param attrId       资源属性对应的id
     * @param defaultFloat 默认float值
     * @return attrId对应的dimen的值
     */
    private static float innerGetDimensionByAttrId(int attrId, float defaultFloat) {
        TypedValue typedValue = obtainTypedValue(attrId);
        if (typedValue == null)
            return defaultFloat;
        try {
            defaultFloat =
                    typedValue.getDimension(BaseApplication.getContext().getResources().getDisplayMetrics());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return defaultFloat;
    }

    /**
     * 根据attrId获取dimen值
     *
     * @param attrId       资源属性对应的id
     * @param defaultFloat 默认float值
     * @return attrId对应的dimen的值
     */
    public static float getDimenByAttrId(int attrId, float defaultFloat) {
        return innerGetDimensionByAttrId(attrId, defaultFloat);
    }

    /**
     * 根据attrId获取dimen值
     *
     * @param attrId 资源属性对应的id
     * @return attrId对应的dimen的值
     */
    public static float getDimenByAttrId(int attrId) {
        return getDimenByAttrId(attrId, DEFAULT_FLOAT_VALUE);
    }

    /**
     * 制作着色Drawable
     *
     * @param drawable       需要进行着色的drawable对象
     * @param colorStateList 需要着色的colorStateList值
     * @return 着色drawable
     */
    private static Drawable innerCreateTintDrawable(@NonNull Drawable drawable, ColorStateList
            colorStateList) {
        Drawable tintDrawable = DrawableCompat.wrap(drawable.mutate());
        DrawableCompat.setTintList(tintDrawable, colorStateList);
        return tintDrawable;
    }

    /**
     * 创建着色Drawable
     *
     * @param drawable       需要进行着色的drawable对象
     * @param colorStateList 需要着色的colorStateList值
     * @return 着色drawable
     */
    public static Drawable createTintDrawable(@NonNull Drawable drawable, ColorStateList
            colorStateList) {
        return innerCreateTintDrawable(drawable, colorStateList);
    }

    /**
     * 根据attrId对应的drawable设置View背景
     *
     * @param view   要设置背景的view
     * @param attrId 资源属性对应id
     */
    public static void setBackgroundResourceByAttr(@NonNull View view, int attrId) {
        int identifier = getIdentifierByAttrId(attrId);
        if (identifier == 0)
            return;
        view.setBackgroundResource(identifier);
    }

    /**
     * 根据attrId对应的drawable设置ImageView的前景色
     *
     * @param imageview 要设置前景色的imageview
     * @param attrId    drawable资源属性对应id
     */
    public static void setImageResourceByAttr(@NonNull ImageView imageview, int attrId) {
        int identifier = getIdentifierByAttrId(attrId);
        if (identifier == 0)
            return;
        imageview.setImageResource(identifier);
    }

    /**
     * 为View设置指定着色的Drawable背景
     *
     * @param view           要设置背景的view
     * @param drawable       drawable
     * @param colorStateList 需要着色的颜色值
     */
    public static void setTintBackground(@NonNull View view, @NonNull Drawable drawable,
                                         ColorStateList colorStateList) {
        Drawable tintedDrawable = createTintDrawable(drawable, colorStateList);
        if (tintedDrawable == null)
            return;
        view.setBackgroundDrawable(tintedDrawable);
    }

    /**
     * 为View设置指定着色的Drawable背景
     *
     * @param view                  要设置背景的view
     * @param drawable              drawable
     * @param attrId                资源属性对应id
     * @param defaultColorStateList 需要着色的颜色值
     */
    public static void setTintBackgroundByAttr(@NonNull View view, @NonNull Drawable drawable, int
            attrId, ColorStateList defaultColorStateList) {
        setTintBackground(view, drawable, innerGetColorStateListByAttr(attrId,
                defaultColorStateList));
    }

    /**
     * 为View设置指定着色的Drawable背景
     *
     * @param view     要设置背景的view
     * @param drawable drawable
     * @param attrId   资源属性对应id
     */
    public static void setTintBackgroundByAttr(@NonNull View view, @NonNull Drawable drawable, int
            attrId) {
        setTintBackgroundByAttr(view, drawable, attrId, null);
    }

    /**
     * 根据View当前Background设置着色Drawable并重新设置背景
     *
     * @param view           view
     * @param colorStateList 着色color
     */
    public static void tintBackground(@NonNull View view, ColorStateList colorStateList) {
        Drawable backgroundDrawable = view.getBackground();
        if (backgroundDrawable == null)
            return;
        Drawable tintedDrawable = createTintDrawable(backgroundDrawable, colorStateList);
        if (tintedDrawable == null)
            return;
        view.setBackgroundDrawable(tintedDrawable);
    }

    /**
     * 根据View当前Background设置着色Drawable并重新设置背景
     *
     * @param view                  view
     * @param attrId                资源属性对应id
     * @param defaultColorStateList 默认着色color
     */
    public static void tintBackgroundByAttr(@NonNull View view, int attrId, ColorStateList
            defaultColorStateList) {
        ColorStateList colorStateList = innerGetColorStateListByAttr(attrId, defaultColorStateList);
        tintBackground(view, colorStateList);
    }

    /**
     * 根据View当前Background设置着色Drawable并重新设置背景
     *
     * @param view   view
     * @param attrId 资源属性对应id
     */
    public static void tintBackgroundByAttr(@NonNull View view, int attrId) {
        tintBackgroundByAttr(view, attrId, null);
    }

    /**
     * 为指定ImageView设置指定的着色Drawable
     *
     * @param imageView      要设定着色drawable的imageview
     * @param drawable       着色drawable
     * @param colorStateList 着色color
     */
    public static void setTintImageDrawable(@NonNull ImageView imageView,
                                            @NonNull Drawable drawable,
                                            ColorStateList colorStateList) {
        Drawable tintedDrawable = createTintDrawable(drawable, colorStateList);
        if (tintedDrawable == null)
            return;
        imageView.setImageDrawable(tintedDrawable);
    }

    /**
     * 为指定ImageView设置指定的着色Drawable
     *
     * @param imageView             要设定着色drawable的imageview
     * @param drawable              着色drawable
     * @param attrId                资源属性对应id
     * @param defaultColorStateList 着色color
     */
    public static void setTintImageDrawableByAttr(@NonNull ImageView imageView, @NonNull Drawable
            drawable, int attrId, ColorStateList defaultColorStateList) {
        ColorStateList colorStateList = innerGetColorStateListByAttr(attrId, defaultColorStateList);
        setTintImageDrawable(imageView, drawable, colorStateList);
    }

    /**
     * 为指定ImageView设置指定的着色Drawable
     *
     * @param imageView 要设定着色drawable的imageview
     * @param drawable  着色drawable
     * @param attrId    资源属性对应id
     */
    public static void setTintImageDrawableByAttr(@NonNull ImageView imageView, @NonNull Drawable
            drawable, int attrId) {
        setTintImageDrawableByAttr(imageView, drawable, attrId, null);
    }

    /**
     * 设置ImageView当前的ImageDrawable着色并重新设置ImageDrawable
     *
     * @param imageView      imageView
     * @param colorStateList 着色color
     */
    public static void tintImageDrawable(@NonNull ImageView imageView,
                                         ColorStateList colorStateList) {
        Drawable originDrawable = imageView.getDrawable();
        if (originDrawable == null)
            return;
        Drawable tinedDrawable = createTintDrawable(originDrawable, colorStateList);
        if (tinedDrawable == null)
            return;
        imageView.setImageDrawable(tinedDrawable);
    }

    /**
     * 设置ImageView当前的ImageDrawable着色并重新设置ImageDrawable
     *
     * @param imageView             imageView
     * @param attrId                资源属性对应id
     * @param defaultColorStateList 着色color
     */
    public static void tintImageDrawableByAttr(@NonNull ImageView imageView, int attrId,
                                               ColorStateList
                                                       defaultColorStateList) {
        ColorStateList colorStateList = innerGetColorStateListByAttr(attrId,
                defaultColorStateList);
        tintImageDrawable(imageView, colorStateList);
    }

    /**
     * 设置ImageView当前的ImageDrawable着色并重新设置ImageDrawable
     *
     * @param imageView imageView
     * @param attrId    资源属性对应id
     */
    public static void tintImageDrawableByAttr(@NonNull ImageView imageView, int attrId) {
        tintImageDrawableByAttr(imageView, attrId, null);
    }

    /**
     * 设置View的透明度
     *
     * @param view         要设置透明度的view
     * @param attrId       资源属性对应id
     * @param defaultFloat 默认透明度float值 0.0f-1.0f
     */
    public static void setAlphaByAttr(@NonNull View view, int attrId, float defaultFloat) {
        float alpha = innerGetFloatByAttrId(attrId, defaultFloat);
        view.setAlpha(alpha);
    }

    /**
     * 设置View的透明度
     *
     * @param view   要设置透明度的view
     * @param attrId 资源属性对应id
     */
    public static void setAlphaByAttr(@NonNull View view, int attrId) {
        setAlphaByAttr(view, attrId, DEFAULT_FLOAT_VALUE);
    }

    /**
     * 设置view背景颜色值
     *
     * @param view         要设置背景颜色的view
     * @param attrId       资源属性对应id
     * @param defaultColor 默认颜色
     */
    public static void setBackgroundColorByAttr(@NonNull View view, int attrId, int defaultColor) {
        int backgroundColor = innerGetColorByAttr(attrId, defaultColor);
        view.setBackgroundColor(backgroundColor);
    }

    /**
     * 设置view背景颜色值
     *
     * @param view   要设置背景颜色的view
     * @param attrId 资源属性对应id
     */
    public static void setBackgroundColorByAttr(@NonNull View view, int attrId) {
        setBackgroundColorByAttr(view, attrId, DEFAULT_BACKGROUND_COLOR);
    }

    /**
     * 设置TextView文本颜色
     *
     * @param textView              textView
     * @param attrId                资源属性对应id
     * @param defaultColorStateList 默认着色color
     */
    public static void setTextColorByAttr(@NonNull TextView textView, int attrId, ColorStateList
            defaultColorStateList) {
        ColorStateList colorStateList = innerGetColorStateListByAttr(attrId, defaultColorStateList);
        textView.setTextColor(colorStateList);
    }

    /**
     * 设置TextView文本颜色
     *
     * @param textView textView
     * @param attrId   资源属性对应id
     */
    public static void setTextColorByAttr(@NonNull TextView textView, int attrId) {
        setTextColorByAttr(textView, attrId, null);
    }

    /**
     * 设置TextView提示文本颜色
     *
     * @param textView              textView
     * @param attrId                资源属性对应id
     * @param defaultColorStateList 默认着色color
     */
    public static void setHintTextColorByAttr(@NonNull TextView textView, int attrId, ColorStateList
            defaultColorStateList) {
        ColorStateList colorStateList = innerGetColorStateListByAttr(attrId, defaultColorStateList);
        textView.setHintTextColor(colorStateList);
    }

    /**
     * 设置TextView提示文本颜色
     *
     * @param textView textView
     * @param attrId   资源属性对应id
     */
    public static void setHintTextColorByAttr(@NonNull TextView textView, int attrId) {
        setHintTextColorByAttr(textView, attrId, null);
    }
}
