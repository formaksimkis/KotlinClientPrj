
package com.company.ipcclient.infrastruture.clients

//TODO: use HLog instead of Log
import android.util.Log
import java.io.*
import java.net.Socket

/**
 * A Connector class.
 *
 * This class is used for reading and writing to IPC Server via socket connection.
 *
 * @param mServerName the server name or IP.
 * @param mServerPort the port number.
 * @constructor Creates a connector object with server and port values.
 * @throws IOException
 * @author Maksim Kiselev
 */

class Connector(private val mServerName: String, private val mServerPort: Int) : Closeable {
    private lateinit var mReader: BufferedReader
    private lateinit var mWriter: BufferedWriter
    private lateinit var mSocket: Socket

    @get:Synchronized
    @Volatile
    var isConnected = false
        private set

    //TODO: do the same for all logs, if HLog is available in product partition
    // if (!PRODUCTION) {
    //      throw new RuntimeException("Fail to create socket or socket IO stream: " + e.message);
    // } else {
    //      HLog.e(TAG_IPCCLIENT, "Fail to create socket or socket IO stream: " + e.message);
    //      return;
    //   }

    @Synchronized
    fun connect(): Boolean {
        try {
            mSocket = Socket(mServerName, mServerPort)
            mReader = createReader()
            mWriter = createWriter()
            isConnected = true
            return true
        } catch (e: IOException) {
            Log.e(LOG_TAG, "Fail to create socket or socket IO stream: " + e.message)
        }
        return false
    }

    @Throws(IOException::class)
    private fun createWriter(): BufferedWriter {
        return BufferedWriter(OutputStreamWriter(mSocket.getOutputStream()))
    }

    @Throws(IOException::class)
    private fun createReader(): BufferedReader {
        return BufferedReader(InputStreamReader(mSocket.getInputStream()))
    }

    @Throws(IOException::class)
    fun writeLine(message: String?) {
        mWriter.write(message)
        mWriter.newLine()
        mWriter.flush()
    }

    @Throws(IOException::class)
    fun bufferIsNotEmpty(): Boolean {
        return mReader.ready()
    }

    @Throws(IOException::class)
    fun readLine(): String {
        return mReader.readLine()
    }

    @Throws(IOException::class)
    override fun close() {
        mReader.close()
        mWriter.close()
        mSocket.close()
    }

    companion object {
        private const val LOG_TAG = "IPCClient"
    }
}