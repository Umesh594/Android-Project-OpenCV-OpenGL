package com.example.edgeviewer;
import android.opengl.GLSurfaceView;
import android.opengl.GLES20;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
public class GLRenderer implements GLSurfaceView.Renderer{
    private byte[] frameData;
    private int width,height;
    public void updateFrame(byte[] data,int w,int h){
        this.frameData=data;
        this.width=w;
        this.height=h;
    }
    @Override
    public void onSurfaceCreated(GL10 gl,EGLConfig config){
          GLES20.glClearColor(0,0,0,1);
    }
    @Override
    public void onSurfaceChanged(GL10 gl,int width,int height){
        GLES20.glViewport(0,0,width,height);
    }
    @Override
    public void onDrawFrame(GL10 gl){
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
          if(frameData!=null){
              processFrame(frameData,width,height);
          }
    }
    private native void processFrame(byte[] data,int width,int height);
}
