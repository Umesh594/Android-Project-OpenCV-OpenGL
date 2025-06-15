package com.example.edgeviewer;
import androidx.appcompat.app.AppCompatActivity;
import android.hardware.Camera;
import android.graphics.SurfaceTexture;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.view.TextureView;
import android.view.TextureView.SurfaceTextureListener;
import android.hardware.Camera.PreviewCallback;
import android.widget.FrameLayout;
public class MainActivity extends AppCompatActivity implements SurfaceTextureListener, PreviewCallback{
    static{
        System.loadLibrary("native-lib");
        System.loadLibrary("opencv_java4");
    }
    private Camera camera;
    private TextureView textureView;
    private GLSurfaceView glSurfaceView;
    private GLRenderer glRenderer;
    private int width=640;
    private int height=480;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        textureView = new TextureView(this);
        textureView.setSurfaceTextureListener(this);
        glSurfaceView = new GLSurfaceView(this);
        glSurfaceView.setEGLContextClientVersion(2);
        glRenderer = new GLRenderer();
        glSurfaceView.setRenderer(glRenderer);
        setContentView(textureView);
        addContentView(glSurfaceView, new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT));
    }
    @Override
    public void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture,int w,int h){
        camera=Camera.open();
        try{
            camera.setPreviewTexture(surfaceTexture);
            camera.setPreviewCallback(this);
            camera.startPreview();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height){
    }
    @Override
      public void onPreviewFrame(byte[] data, Camera camera){
        glRenderer.updateFrame(data,width,height);
      }
      @Override
      public boolean onSurfaceTextureDestroyed(SurfaceTexture surface){
        if(camera!=null){
            camera.stopPreview();
            camera.release();
            camera=null;
        }
        return true;
      }
      @Override
       public void onSurfaceTextureUpdated(SurfaceTexture surface) {}
    }