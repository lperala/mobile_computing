package com.example.homework1

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters



// https://towardsdatascience.com/working-with-android-workmanager-using-kotlin-36167a143579 GUIDE

class Worker(appContext: Context, workerParameters: WorkerParameters) : Worker(appContext, workerParameters) {
    override fun doWork(): Result {
        // val userId = inputData.getInt("userId,
        val message = inputData.getString("message")
        val msgId = inputData.getInt("msgId",0)
        println("Message WORKER: " + message)
        println("User id: " + msgId)
        NewMessageActivity.showNofitication(applicationContext, message!!, msgId!!)
        return Result.success()
    }
}

