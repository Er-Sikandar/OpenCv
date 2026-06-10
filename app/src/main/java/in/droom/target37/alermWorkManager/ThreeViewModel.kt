package `in`.droom.target37.alermWorkManager

import android.graphics.Bitmap
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.util.UUID

class ThreeViewModel : ViewModel() {
    private val _uri = MutableLiveData<Uri?>()
    val uri: LiveData<Uri?> = _uri

    private val _bitmap = MutableLiveData<Bitmap?>()
    val bitmap: LiveData<Bitmap?> = _bitmap

    private val _workId = MutableLiveData<UUID?>()
    val workId: LiveData<UUID?> = _workId

    fun updateUnCompressUri(unUri: Uri){
      _uri.value=unUri
    }
    fun updateCompressBitmap(cmpBitmap: Bitmap){
        _bitmap.value=cmpBitmap
    }
    fun updateWorkId(uuid: UUID){
        _workId.value=uuid
    }
}