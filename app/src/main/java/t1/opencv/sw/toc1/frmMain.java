package t1.opencv.sw.toc1;

import android.app.Activity;
import android.os.Bundle;
import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.CameraBridgeViewBase.CvCameraViewListener;
import org.opencv.android.JavaCameraView;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

public class frmMain extends Activity implements CvCameraViewListener
{
    private static final String  TAG = "sss";
    int w=0;
    int h=0;
    Mat img;
    Button btn1;

    private CameraBridgeViewBase mOpenCvCameraView;

    private BaseLoaderCallback mLoaderCallback = new BaseLoaderCallback(this) {

        @Override
        public void onManagerConnected(int status) {
            switch (status) {
                case LoaderCallbackInterface.SUCCESS:
                {
                    Log.i(TAG, "OpenCV loaded successfully");
                    mOpenCvCameraView.enableView();
                } break;
                default:
                {
                    super.onManagerConnected(status);
                } break;
            }
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        setContentView(R.layout.lay_frm_main);
        btn1=(Button)findViewById(R.id.button1);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(frmMain.this, "嘎嘎!", Toast.LENGTH_SHORT).show();
            }
        });

        Log.d(TAG, "Creating and setting view");
        //mOpenCvCameraView = (CameraBridgeViewBase) new JavaCameraView(this, -1);
        mOpenCvCameraView=(JavaCameraView)findViewById(R.id.opencv_view);
        //setContentView(mOpenCvCameraView);
        mOpenCvCameraView.enableFpsMeter();
        mOpenCvCameraView.setVisibility(CameraBridgeViewBase.VISIBLE);
        mOpenCvCameraView.setCvCameraViewListener(this);
    }

    @Override
    public void onPause()
    {
        super.onPause();
        if (mOpenCvCameraView != null)
            mOpenCvCameraView.disableView();
    }

    @Override
    public void onResume()
    {
        super.onResume();
        if (!OpenCVLoader.initDebug()) {
            Log.d(TAG, "Internal OpenCV library not found. Using OpenCV Manager for initialization");
        } else {
            Log.d(TAG, "OpenCV library found inside package. Using it!");
            mLoaderCallback.onManagerConnected(LoaderCallbackInterface.SUCCESS);
        }
    }

    public void onDestroy() {
        super.onDestroy();
        if (mOpenCvCameraView != null)
            mOpenCvCameraView.disableView();
    }


    public void onCameraViewStarted(int width, int height)
    {
        w=width;
        h=height;
        img=new Mat(h,w,CvType.CV_8UC4);
        l("width="+width+",height="+height);

    }

    public void onCameraViewStopped() {
    }


    void l(String s)
    {
        Log.e("SWW",s);
    }

    public Mat onCameraFrame(Mat mRgba)
    {
        try
        {
            img.setTo(new Scalar(0, 0, 0));

            Rect t1 = new Rect(0, 0, 100, 100);
            Rect tx1 = new Rect(300, 300, 100, 100);
            Rect t2 = new Rect(450, 350, 300, 50);
            Rect tx2 = new Rect(50, 50, 300, 50);

            Imgproc.line(img, new Point(10, 10), new Point(400, 400), new Scalar(255, 0, 0), 5);
            Imgproc.rectangle(img, new Point(50, 50), new Point(300, 300), new Scalar(0, 0, 255), 5);

            img.submat(t2).setTo(new Scalar(0, 200, 100));

            Mat m1 = mRgba.submat(t1);
            Mat mx1 = mRgba.submat(tx1);

            Mat m2 = new Mat(100, 100, CvType.CV_8UC1);
            Mat mx2 = new Mat(100, 100, CvType.CV_8UC1);
            Mat m_gray_diff = new Mat(100, 100, CvType.CV_8UC1);
            Mat m_gray_diff_2z = new Mat(100, 100, CvType.CV_8UC1);
            Mat m3 = new Mat(100, 100, CvType.CV_8UC4);

            Imgproc.cvtColor(m1, m2, Imgproc.COLOR_RGBA2GRAY);

            Imgproc.cvtColor(mx1, mx2, Imgproc.COLOR_RGBA2GRAY);

            Core.absdiff(m2, mx2, m_gray_diff);
            Imgproc.threshold(m_gray_diff, m_gray_diff_2z, 100, 255, Imgproc.THRESH_BINARY);
            Imgproc.cvtColor(m_gray_diff_2z, m3, Imgproc.COLOR_GRAY2BGRA);

            int a = m_gray_diff_2z.rows();
            int b = m_gray_diff_2z.cols();
            int x1 = 0, x2 = 0;
            for (int r1 = 0; r1 < a; r1++) {
                for (int c1 = 0; c1 < b; c1++) {
                    x1++;
                    double[] ds = m_gray_diff_2z.get(r1, c1);
                    if (ds[0] == 255) {
                        x2++;
                    }
                }
            }

            Imgproc.resize(m3, img.submat(t2), t2.size());
            Imgproc.resize(mx1, img.submat(tx2), tx2.size());

            Imgproc.putText(img, x2 + "/" + x1, new Point(200, 200), 0, 2, new Scalar(255, 0, 0), 2);

            //Rect rect=new Rect(0,100,0,100);
            //mRgba.submat(rect).copyTo(img.submat(0,100,0,100));

            mRgba.release();
            m1.release();
            m2.release();
            m3.release();
            mx1.release();
        }
        catch (Exception e)
        {

        }
        return img;
    }
}
