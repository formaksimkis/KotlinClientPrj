

package com.company.ipcclient.service

import android.content.Intent
import android.os.IBinder
import android.util.Log
import androidx.lifecycle.LifecycleService
import com.company.ipcclient.infrastruture.clients.interfaces.IServerClient
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class IPCService: LifecycleService() {

    companion object {
        private const val LOG_TAG = "IPCClient"
    }

    @Inject
    lateinit var mServerClient: IServerClient

    override fun onCreate() {
        super.onCreate()
        Log.d(LOG_TAG, "onCreate");
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d(LOG_TAG, "onStartCommand")
        mServerClient.startReading()
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(LOG_TAG, "onDestroy");
    }

    override fun onBind(intent: Intent): IBinder? {
        super.onBind(intent)
        Log.d(LOG_TAG, "onBind");
        return null
    }
}