package `in`.droom.target37.alermWorkManager.workmanager

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream
import java.io.File
import kotlin.math.roundToInt

class WorkManagerWorker(private val context: Context,private val params: WorkerParameters) : CoroutineWorker(context,params){

    companion object{
        const val CONT_URI="content_uri"
        const val KEY_CMP="key_cmp"
        const val RES_PATH="res_path"
    }
    @SuppressLint("SuspiciousIndentation")
    override suspend fun doWork(): Result {
        return withContext(Dispatchers.IO) {
            val strUri = params.inputData.getString(CONT_URI)
            val keyCmp = params.inputData.getLong(KEY_CMP, 0L)
            val uri = Uri.parse(strUri)
            Log.e("TAG","doWork: $strUri")
            val bytes = context.contentResolver.openInputStream(uri)?.use {
                it.readBytes()
            } ?: return@withContext Result.failure()
            val bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.size) ?: return@withContext Result.failure()
            var outputBytes: ByteArray
            var quantity=100
            do {
             val outputStream= ByteArrayOutputStream()
                outputStream.use {
                    bitmap.compress(Bitmap.CompressFormat.JPEG,quantity,outputStream)
                    outputBytes= outputStream.toByteArray()
                    quantity-=(quantity*0.1).roundToInt()
                }
            }while (outputBytes.size>keyCmp && quantity > 5)
            val file = File(context.cacheDir, "${params.id}.jpg")
            file.writeBytes(outputBytes)
            bitmap.recycle()
            Result.success(workDataOf(RES_PATH to file.absolutePath))
        }
    }
}