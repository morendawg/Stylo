package hwk2.cis350.upenn.edu.stylo;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

/**
 * Created by parthchopra on 3/14/16
 *
 * The purpose of this class is create the cardView layout for each image along with its associated
 * like button. It extends a relative
 */
public class CustomImage extends RelativeLayout {

    int img;
    ImageView icon;
    ImageView upvote;

    /**
     * Constructor class which takes the context that the image was created in.
     * @param c
     */
    public CustomImage(Context c) {
        super(c);
        init();
    }

    /**
     * Secondary constructor that also sets the icon of the image
     * @param c
     * @param icon
     */
    public CustomImage(Context c, int icon) {
        super(c);
        img = icon;
        init();
    }

    /**
     * Another default constructor to create the CustomImage
     */
    public CustomImage(Context context, AttributeSet attr) {
        super(context, attr);
    }

    /**
     * Inflates and initiates the CustomImage view and sets all corresponding associations
     */
    public void init() {
        inflate(getContext(), R.layout.outfit_layout, this);

        icon = (ImageView) findViewById(R.id.person_photo);
        icon.setImageResource(img);

        upvote = (ImageView) findViewById(R.id.upvote);

        upvote.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v("LIKE", "LIKE BUTTON CLICKED");
            }
        });
    }

    @Override
    /**
     * Draws the associated image. Overriden from parent class.
     */
    protected void onDraw(Canvas canvas) {
        Log.v("Drawing", "Did it at least get hre?");

        super.onDraw(canvas);
    }
}
