package br.edu.ifsp.scl.ads.pdm.moviesmanager.view

import android.content.Intent
import android.os.Bundle
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import br.edu.ifsp.scl.ads.pdm.moviesmanager.R
import br.edu.ifsp.scl.ads.pdm.moviesmanager.adapter.MovieAdapter
import br.edu.ifsp.scl.ads.pdm.moviesmanager.controller.MovieRoomController
import br.edu.ifsp.scl.ads.pdm.moviesmanager.databinding.ActivityMainBinding
import br.edu.ifsp.scl.ads.pdm.moviesmanager.model.Constant.EXTRA_MOVIE
import br.edu.ifsp.scl.ads.pdm.moviesmanager.model.Constant.VIEW_MOVIE
import br.edu.ifsp.scl.ads.pdm.moviesmanager.model.entity.Movie
import java.sql.Time


class MainActivity : AppCompatActivity() {
    private val amb: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val movieList: MutableList<Movie> = mutableListOf()

    private lateinit var movieAdapter: MovieAdapter

    private lateinit var carl: ActivityResultLauncher<Intent>

    private val movieController: MovieRoomController by lazy {
        MovieRoomController(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(amb.root)
        movieAdapter = MovieAdapter(this, movieList)
        amb.moviesLv.adapter = movieAdapter
        setMovieList()

        carl = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult(),
        ) { result ->
            if (result.resultCode == RESULT_OK) {
                val movie = result.data?.getParcelableExtra<Movie>(EXTRA_MOVIE)

                movie?.let { _movie->
                    if (_movie.id != null) {
                        val position = movieList.indexOfFirst { it.id == _movie.id }
                        if (position != -1) {
                            movieController.editMovie(_movie)
                        }
                    }
                    else {
                        movieController.insertMovie(_movie)
                    }
                }
            }
        }

        registerForContextMenu(amb.moviesLv)

        amb.moviesLv.onItemClickListener =
            AdapterView.OnItemClickListener { _, _, position, _ ->
                val movie = movieList[position]
                val movieIntent = Intent(this@MainActivity, MovieActivity::class.java)
                movieIntent.putExtra(EXTRA_MOVIE, movie)
                movieIntent.putExtra(VIEW_MOVIE, true)
                startActivity(movieIntent)
            }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.addMovieMi -> {
                carl.launch(Intent(this, MovieActivity::class.java))
                true
            }
            else -> { false }
        }
    }


    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        menuInflater.inflate(R.menu.context_menu_main, menu)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val position = (item.menuInfo as AdapterView.AdapterContextMenuInfo).position
        val movie = movieList[position]
        return when(item.itemId) {
            R.id.removeMovieMi -> {
                movieController.removeMovie(movie)
                true
            }
            R.id.editMovieMi -> {
                val movieIntent = Intent(this, MovieActivity::class.java)
                movieIntent.putExtra(EXTRA_MOVIE, movie)
                movieIntent.putExtra(VIEW_MOVIE, false)
                carl.launch(movieIntent)
                true
            }
            else -> { false }
        }
    }

    fun updateMovieList(_movieList: MutableList<Movie>) {
        movieList.clear()
        movieList.addAll(_movieList)
        movieAdapter.notifyDataSetChanged()
    }

    private fun setMovieList(){
        movieList.add(
            Movie(1, "Alice no País das Maravilhas", 2010, "America Latina Hollywood", Time(15000), true, 9, "Adventure")
        )
    }

}