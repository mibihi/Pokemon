package com.example.mibihi.pokemon

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.widget.Toast

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        checkPermission()
    }
    var ACCESSLOCATION = 123
    fun checkPermission(){
        if(Build.VERSION.SDK_INT>=23){
        if(ActivityCompat.checkSelfPermission(this,android.Manifest.permission.ACCESS_FINE_LOCATION)
                !=PackageManager.PERMISSION_GRANTED){
            requestPermissions(arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),ACCESSLOCATION)
            return
        }
        }
        getUserLocation()
    }
    @SuppressLint("MissingPermission")
    fun getUserLocation(){
        Toast.makeText(this,"User Location Access is on",Toast.LENGTH_LONG).show()
        var mylocation=MylocationListener()
        var locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,3,3f,mylocation)
        var mythread = myThread()
        mythread.start()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when(requestCode){
            ACCESSLOCATION->{if(grantResults[0]==PackageManager.PERMISSION_GRANTED){getUserLocation()}
            else{
                Toast.makeText(this,"We can not get access to your location",Toast.LENGTH_LONG).show()
            }

            }
        }


        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }
    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Add a marker in Sydney and move the camera

    }
    //get user location
    var location:Location?=null
    inner class MylocationListener:LocationListener{

        constructor(){
            location= Location("START")
            location!!.longitude=0.0
            location!!.latitude=0.0
        }
        override fun onLocationChanged(p0: Location?) {
            location=p0
           // TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun onStatusChanged(p0: String?, p1: Int, p2: Bundle?) {
           // TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun onProviderEnabled(p0: String?) {
          //  TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun onProviderDisabled(p0: String?) {
         //   TODO("not implemented") //To change body of created functions use File | Settings | File Templates.

        }

    }
    //var oldLocation:Location?=null
    inner class myThread:Thread{
        constructor():super(){
            location= Location("START")
            location!!.longitude=0.0
            location!!.latitude=0.0

        }

        override fun run() {
           while(true){
               try {
//                   if(oldLocation!!.distanceTo(location)==0f){continue}
//                   oldLocation=location

                   runOnUiThread {
                       mMap!!.clear()
                       //show me
                       val sydney = LatLng(37.0, -122.0)
                       mMap.addMarker(MarkerOptions()
                               .position(sydney)
                               .title("Me")
                               .snippet("Here is my Location:")
                               .icon(BitmapDescriptorFactory.fromResource(R.drawable.mario)))
                       mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 5f))
                       //show pokemons
                       for(i in 0..listPokemons.size-1)
                       {
                           var newPokemon = listPokemons[i]
                           if(newPokemon.IsCatch==false)
                           { val pokemonLoc = LatLng(newPokemon.lat!!, newPokemon.long!!)
                               mMap.addMarker(MarkerOptions()
                                       .position(pokemonLoc)
                                       .title(newPokemon.name)
                                       .snippet(newPokemon.des)
                                       .icon(BitmapDescriptorFactory.fromResource(newPokemon.image!!)))
                           }
                           println("added  :  "+newPokemon.name)
                       }//loop

                   }

                    Thread.sleep(1000)
               }catch(ex:Exception){}

           }
        }

    }

    var listPokemons=ArrayList<Pokemon>()
    fun loadPokemons(){
        listPokemons.add(Pokemon(R.drawable.charmander,"charmande","lives in japan",55.0,37.33,-122.20))
        listPokemons.add(Pokemon(R.drawable.squirtle,"squirtle","lives in india",30.0,37.15,-122.34))
        listPokemons.add(Pokemon(R.drawable.bulbasaur,"bulbasaur","lives in europe",23.0,37.22,-122.34))
    }

}