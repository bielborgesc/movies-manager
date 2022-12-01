package br.edu.ifsp.scl.ads.pdm.moviesmanager.view

import androidx.appcompat.app.AppCompatActivity
import br.edu.ifsp.scl.ads.pdm.moviesmanager.databinding.ActivityMainBinding
import br.edu.ifsp.scl.ads.pdm.moviesmanager.model.entity.Movie


class MainActivity : AppCompatActivity() {
    private val amb: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val contactList: MutableList<Movie> = mutableListOf()

}