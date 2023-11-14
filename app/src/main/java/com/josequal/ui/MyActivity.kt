package com.josequal.ui

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.josequal.MyViewModel
import com.josequal.R
import com.josequal.adapter.DrawerAdapter
import com.josequal.databinding.ActivityMainBinding
import com.josequal.model.DrawerItem

class MyActivity : AppCompatActivity(), OnMapReadyCallback {
    private val viewModel: MyViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding
    private lateinit var drawerAdapter: DrawerAdapter
    private var googleMap: GoogleMap? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_xxx)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        // Observe map location changes
        viewModel.mapLocation.observe(this) { location ->
            // Handle map location changes here
            googleMap?.animateCamera(CameraUpdateFactory.newLatLngZoom(location, 12f))
        }

        // Observe drawer items changes
        viewModel.drawerItems.observe(this) { drawerItems ->
            drawerAdapter = DrawerAdapter(drawerItems, object : DrawerAdapter.OnItemClickListener {
                override fun onItemClick(item: DrawerItem) {
                    viewModel.selectItem(item)
                }
            })
            binding.recyclerView.apply {
                layoutManager =
                    LinearLayoutManager(this@MyActivity, LinearLayoutManager.HORIZONTAL, false)
                adapter = drawerAdapter
            }
        }

        viewModel.mapReadyEvent.observe(this, { isMapReady ->
            if (isMapReady) {
                // Perform actions when the map is ready
            }
        })
    }

    override fun onMapReady(map: GoogleMap?) {
        googleMap = map
        viewModel.onMapReady()
        // You can customize the map further if needed
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_abdail_to_mukhtar -> {
                viewModel.loadKMLFromRawResource(R.raw.abdali_to_mukhtar)
                return true
            }

            R.id.menu_istklal_to_abdali -> {
                viewModel.loadKMLFromRawResource(R.raw.istklal_to_abdali)
                return true
            }

            R.id.menu_mukhtar_to_istklal -> {
                viewModel.loadKMLFromRawResource(R.raw.mukhtar_to_istklal)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

}