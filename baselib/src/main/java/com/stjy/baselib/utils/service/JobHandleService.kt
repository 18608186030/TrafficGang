package com.stjy.baselib.utils.service

import android.annotation.SuppressLint
import android.app.ActivityManager
import android.app.Service
import android.app.job.JobInfo
import android.app.job.JobParameters
import android.app.job.JobScheduler
import android.app.job.JobService
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast

/**
 * @Author: superman
 * @CreateTime: 2020/7/14
 * @Describe:
 */
@SuppressLint("NewApi")
class JobHandleService : JobService() {
    private var kJobId = 0
    override fun onCreate() {
        super.onCreate()
        Log.i("INFO", "onCreate")
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        Log.i("INFO", "onStartCommand")
        // 在服务启动时，直接将任务推到JobScheduler 的任务队列,然后在设定的时间条件到达时，便会直接吊起我们的服务，走onStartJob()方法
        scheduleJob(jobInfo)
        return Service.START_NOT_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onStartJob(params: JobParameters): Boolean {
        Log.i("INFO", "onStartJob")
        //params.getExtras()
        //scheduleJob(getJobInfo());
        val isLocalServiceWork = isServiceWork(this, "com.xxx.XxxService")
        val isRemoteServiceWork = isServiceWork(this, "com.xxx.XxxService")
        if (!isLocalServiceWork || !isRemoteServiceWork) {
            //this.startService(new Intent(this, LocalService.class));
            //this.startService(new Intent(this, RemoteService.class));
            Toast.makeText(this, "process start", Toast.LENGTH_SHORT).show()
        }
        return true
    }

    override fun onStopJob(params: JobParameters): Boolean {
        Log.i("INFO", "onStopJob")
        scheduleJob(jobInfo) // 当执行完毕时，我们再将任务加入到 JobScheduler 里面就可以了。
        return true
    }

    /**
     * Send job to the JobScheduler.
     */
    fun scheduleJob(t: JobInfo?) {
        Log.i("INFO", "scheduleJob")
        (getSystemService(Context.JOB_SCHEDULER_SERVICE) as JobScheduler)?.schedule(t)
    }

    //间隔时间--周期
    @get:SuppressLint("MissingPermission")
    val jobInfo: JobInfo
        get() {
            val builder = JobInfo.Builder(kJobId++, ComponentName(this, JobHandleService::class.java))
            builder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
            builder.setPersisted(true)
            builder.setRequiresCharging(false)
            builder.setRequiresDeviceIdle(false)
            builder.setPeriodic(1300) //间隔时间--周期
            return builder.build()
        }

    /**
     * 判断某个服务是否正在运行的方法
     *
     * @param mContext
     * @param serviceName 是包名+服务的类名（例如：net.loonggg.testbackstage.TestService）
     * @return true代表正在运行，false代表服务没有正在运行
     */
    private fun isServiceWork(mContext: Context, serviceName: String): Boolean {
        var isWork = false
        val myAM = mContext.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        var myList: List<ActivityManager.RunningServiceInfo> = myAM.getRunningServices(100)
        if (myList.isNullOrEmpty()) {
            return false
        } else {
            for (i in myList.indices) {
                val mName = myList[i].service.className
                if (mName == serviceName) {
                    isWork = true
                    break
                }
            }
        }
        return isWork
    }
}