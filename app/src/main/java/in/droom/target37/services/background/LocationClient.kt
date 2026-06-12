package `in`.droom.target37.services.background

import android.location.Location
import kotlinx.coroutines.flow.Flow

interface LocationClient {
    fun getLocationUpdates(interval:Long): Flow<Location>
    class LocationException(massage: String): Exception()
}