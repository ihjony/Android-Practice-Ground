package com.example.asynctaskex;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.lang.ref.WeakReference;

public class MainActivity extends AppCompatActivity {

    private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressBar=findViewById(R.id.progress_bar);
    }

    public void startAsyncTask(View view) {
        ExampleAsyncTask task = new ExampleAsyncTask(this);
        task.execute(10);
    }

    private static class ExampleAsyncTask extends AsyncTask <Integer,Integer,String>{

        private WeakReference<MainActivity> weakReference;
        ExampleAsyncTask(MainActivity activity){
            weakReference =new WeakReference<MainActivity>(activity);
        }
        @Override
        protected String doInBackground(Integer... integers) {
            for(int i=0;i<integers[0];i++){
                publishProgress((i*100)/integers[0]);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return "finish";
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            MainActivity activity = weakReference.get();
            if(activity==null || activity.isFinishing()){
                return;
            }
            activity.progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            MainActivity activity = weakReference.get();
            if(activity==null || activity.isFinishing()){
                return;
            }
            activity.progressBar.setProgress(values[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            MainActivity activity = weakReference.get();
            if(activity==null || activity.isFinishing()){
                return;
            }
            activity.progressBar.setProgress(0);
            activity.progressBar.setVisibility(View.VISIBLE);
            Toast.makeText(activity,s,Toast.LENGTH_SHORT).show();

        }



    }

}
