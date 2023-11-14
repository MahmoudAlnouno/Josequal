package com.josequal

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.maps.android.data.kml.KmlLayer
import com.josequal.model.DrawerItem
import java.io.IOException

class MyViewModel(application: Application) : AndroidViewModel(application) {
    private val _mapReadyEvent = MutableLiveData<Boolean>()
    val mapReadyEvent: LiveData<Boolean>
        get() = _mapReadyEvent
    private val _drawerItems = MutableLiveData<List<DrawerItem>>()
    val drawerItems: LiveData<List<DrawerItem>>
        get() = _drawerItems

    private val _selectedItem = MutableLiveData<DrawerItem>()
    val selectedItem: LiveData<DrawerItem>
        get() = _selectedItem

    private val _mapLocation = MutableLiveData<LatLng>()
    val mapLocation: LiveData<LatLng>
        get() = _mapLocation

    private var _kmlLayer: KmlLayer? = null
    val kmlLayer: KmlLayer?
        get() = _kmlLayer

    private val _marker: MutableLiveData<Marker> = MutableLiveData()
    val marker: LiveData<Marker>
        get() = _marker

    init {
        _drawerItems.value = DrawerItem.fillDumpData()
        _selectedItem.value = null
        _mapLocation.value = LatLng(0.0, 0.0) // Default location
    }

    fun selectItem(item: DrawerItem) {
        _selectedItem.value = item
        _mapLocation.value = item.location
    }

    fun setKmlLayer(kmlLayer: KmlLayer) {
        _kmlLayer = kmlLayer
    }

    fun setMarker(marker: Marker) {
        _marker.value = marker
    }

    // Clear KML layer and marker
    fun clearMapObjects() {
        _kmlLayer?.removeLayerFromMap()
        _kmlLayer = null
        _marker.value?.remove()
        _marker.value = null
    }

    // Handle cleanup when ViewModel is no longer used
    override fun onCleared() {
        super.onCleared()
        clearMapObjects()
    }

    fun onMapReady() {
        _mapReadyEvent.value = true
    }

    fun loadKMLFromRawResource(resourceId: Int) {
        try {
            val inputStream = getApplication<Application>().resources.openRawResource(resourceId)
            val kmlLayer = KmlLayer(null, inputStream, getApplication())
            _kmlLayer = kmlLayer
            // Notify observers that the KML layer is ready
        } catch (e: IOException) {
            // Handle the exception (e.g., show an error message to the user)
        }
    }

    // Clear KML layer
    fun clearKMLLayer() {
        _kmlLayer?.removeLayerFromMap()
        _kmlLayer = null
    }
}