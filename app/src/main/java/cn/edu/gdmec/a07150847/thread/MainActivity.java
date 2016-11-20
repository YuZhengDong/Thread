package cn.edu.gdmec.a07150847.thread;

import android.app.Activity;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import java.util.Date;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    private TextView tv1;
    private int seconds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv1= (TextView) findViewById(R.id.tv1);
        Date theLastDay = new Date(117,5,23);
        Date toDay= new Date();
        seconds=(int)(theLastDay.getTime()-toDay.getTime())/1000;


    }

    public void anr(View v){
        for(int i=0;i<100000;i++){
            BitmapFactory.decodeResource(getResources(),R.drawable.android);
        }
    }

    public void threadclass(View v){
        class ThreadSample extends Thread{
            Random rm;
            public ThreadSample(String tname){
                super(tname);
                rm=new Random();
            }
            public void run(){
                for(int i=0;i<10;i++){
                    System.out.println(i+""+getName());

                    try {
                        sleep(rm.nextInt(1000));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println("完成");
            }


        }
        ThreadSample thread1 = new ThreadSample("线程1");
        thread1.start();
        ThreadSample thread2 = new ThreadSample("线程2");
        thread2.start();
    }


    public void runable(View v){
        class Runablee implements Runnable{
            Random rm;
            String name;

            public Runablee(String tname) {
                this.name = tname;
                rm=new Random();
            }

            @Override
            public void run() {
                for (int i=0;i<10;i++){
                    System.out.println(i+""+name);
                    try {
                        Thread.sleep(rm.nextInt(1000));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println("完成");

            }

        }

        Thread thred1 = new Thread(new Runablee("线程一"));
        thred1.start();
        Thread thred2 = new Thread(new Runablee("线程二"));
        thred2.start();
    }

    public void timer(View v){
        class MyThread extends TimerTask{
            Random rm;
            String name;

            public MyThread(String tname) {
                this.name=tname;
                rm= new Random();
            }

            @Override
            public void run() {
                for(int i=0;i<10;i++){
                    System.out.println(i+""+name);
                    try {
                        Thread.sleep(rm.nextInt(1000));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println("完成");
            }
        }

        Timer timer1 = new Timer();
        Timer timer2 = new Timer();
        MyThread thread1 = new MyThread("线程一");
        MyThread thread2 = new MyThread("线程二");
        timer1.schedule(thread1,0);
        timer2.schedule(thread2,0);
    }


    public void hander(View v){
            final Handler myHander =new Handler(){
                @Override
                public void handleMessage(Message msg) {
                    super.handleMessage(msg);
                    switch (msg.what){
                        case 1:
                            showmsg(String.valueOf(msg.arg1+msg.getData().get("attach").toString()));
                            break;
                    }
                }



            };
        class MyTask extends TimerTask{
            int countdom;
            double achievem=1,achienem2=1;

            public MyTask(int seconds) {
                this.countdom = seconds;
            }

            @Override
            public void run() {
                Message msg = Message.obtain();
                msg.what=1;
                msg.arg1=countdom--;
                achievem =achievem*1.01;
                achienem2=achienem2*1.02;
                Bundle budle = new Bundle();
                budle.putString("attach","\n努力多1%："+achievem+"\n努力多2% :"+achienem2);
                msg.setData(budle);
                myHander.sendMessage(msg);
            }
        }

        Timer timer = new Timer();
        timer.schedule(new MyTask(seconds),1,1000);



    }
    public void showmsg(String msg){
        tv1.setText(msg);
    }



    public void AsyncTask(View v){
        class LnearHard extends AsyncTask<Long,String,String>{
            private Context context;
            final int duration=10;
            int count=0;
            public LnearHard(Activity context) {
                this.context=context;
            }

            @Override
            protected String doInBackground(Long... params) {
                long num = params[0].longValue();
                while (count<duration){
                    num--;
                    count++;
                    String status="离毕业还有"+num+"秒,努力学习"+count+"秒.";
                    publishProgress(status);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                return "这"+duration+"秒有收获，没虚度";
            }

            @Override
            protected void onProgressUpdate(String... values) {
                ((MainActivity)context).tv1.setText(values[0]);
                super.onProgressUpdate(values);
            }

            @Override
            protected void onPostExecute(String s) {
                showmsg(s);
                super.onPostExecute(s);
            }


        }
        LnearHard linerHard = new LnearHard(this);
        linerHard.execute((long)seconds);



    }
}
