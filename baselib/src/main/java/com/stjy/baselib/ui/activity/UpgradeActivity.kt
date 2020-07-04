package com.stjy.baselib.ui.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.blankj.utilcode.util.ToastUtils
import com.stjy.baselib.R
import com.tencent.bugly.beta.Beta
import com.tencent.bugly.beta.UpgradeInfo
import com.tencent.bugly.beta.download.DownloadListener
import com.tencent.bugly.beta.download.DownloadTask
import kotlinx.android.synthetic.main.activity_upgrade.*
import java.text.DecimalFormat

class UpgradeActivity : AppCompatActivity() {

    private var upgradeInfo: UpgradeInfo? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upgrade)
        init()
    }

    override fun onDestroy() {
        super.onDestroy()
        Beta.unregisterDownloadListener()
    }

    override fun onBackPressed() {
        if (upgradeInfo?.upgradeType != 2) {
            super.onBackPressed()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun init() {
        upgradeInfo = Beta.getUpgradeInfo()
        if (upgradeInfo == null) {
            ToastUtils.showShort("暂无升级信息")
            finish()
        }
        betaTitle.text = upgradeInfo?.title
        betaUpgradeInfo.text = "版本:${upgradeInfo?.versionName}\n" +
                "安装文件大小:${getNetFileSizeDescription(upgradeInfo!!.fileSize)}\n" +
                "更新时间:${upgradeInfo!!.publishTime}"
        betaUpgradeFeature.text = upgradeInfo?.newFeature
        // 1:建议 2:强制 3:手工
        when (upgradeInfo?.upgradeType) {
            1 -> betaCancelButton.visibility = View.VISIBLE
            2 -> betaCancelButton.visibility = View.GONE
            3 -> betaCancelButton.visibility = View.VISIBLE
            else -> {
            }
        }
        updateStartButton(Beta.getStrategyTask())
        betaCancelButton.setOnClickListener {
            Beta.cancelDownload()
            finish()
        }
        betaConfirmButton.setOnClickListener {
            val task = Beta.startDownload()
            updateStartButton(task)
            if (task.status == DownloadTask.DOWNLOADING) {
                progressBar.visibility = View.VISIBLE
            }
        }
        Beta.registerDownloadListener(object : DownloadListener {
            /**
             * 下载中
             * @param task 下载任务
             */
            override fun onReceive(task: DownloadTask) {
                updateStartButton(task)
                val fl = task.savedLength.toFloat() / task.totalLength.toFloat() * 100
                progressBar.progress = fl.toInt()
            }

            /**
             * 下载完成
             * @param task 下载任务
             */
            override fun onCompleted(task: DownloadTask) {
                progressBar.visibility = View.GONE
                updateStartButton(task)
            }

            /**
             * 下载失败
             * @param task 下载任务
             * @param code 错误码
             * @param extMsg 错误信息
             */
            override fun onFailed(task: DownloadTask, code: Int, extMsg: String) {
                updateStartButton(task)
                ToastUtils.showShort(extMsg)
            }
        })
    }

    /**
     * 更新开始按钮状态
     */
    private fun updateStartButton(task: DownloadTask) {
        when (task.status) {
            DownloadTask.INIT, DownloadTask.DELETED, DownloadTask.FAILED -> betaConfirmButton.text = "开始下载"
            DownloadTask.COMPLETE -> betaConfirmButton.text = "安装"
            DownloadTask.DOWNLOADING -> betaConfirmButton.text = "暂停"
            DownloadTask.PAUSED ->  betaConfirmButton.text = "继续下载"
            else -> {
            }
        }
    }

    private fun getNetFileSizeDescription(size: Long): String {
        val bytes = StringBuffer()
        val format = DecimalFormat("###.0")
        if (size >= 1024 * 1024 * 1024) {
            val i = (size / (1024.0 * 1024.0 * 1024.0))
            bytes.append(format.format(i)).append("GB")
        } else if (size >= 1024 * 1024) {
            val i = (size / (1024.0 * 1024.0))
            bytes.append(format.format(i)).append("MB")
        } else if (size >= 1024) {
            val i = (size / (1024.0))
            bytes.append(format.format(i)).append("KB")
        } else if (size < 1024) {
            if (size <= 0) bytes.append("0B")else bytes.append(size.toInt()).append("B")
        }
        return bytes.toString()
    }
}
