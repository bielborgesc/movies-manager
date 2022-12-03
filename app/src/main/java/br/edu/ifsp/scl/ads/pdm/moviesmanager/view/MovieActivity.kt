package br.edu.ifsp.scl.ads.pdm.moviesmanager.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import br.edu.ifsp.scl.ads.pdm.moviesmanager.databinding.ActivityMovieBinding
import br.edu.ifsp.scl.ads.pdm.moviesmanager.model.Constant.EXTRA_MOVIE
import br.edu.ifsp.scl.ads.pdm.moviesmanager.model.Constant.VIEW_MOVIE
import br.edu.ifsp.scl.ads.pdm.moviesmanager.model.entity.Movie
import java.sql.Time

class MovieActivity {
    class MovieActivity : AppCompatActivity() {
        private val acb: ActivityMovieBinding by lazy {
            ActivityMovieBinding.inflate(layoutInflater)
        }

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(acb.root)

            val receivedMovie = intent.getParcelableExtra<Movie>(EXTRA_MOVIE)
            receivedMovie?.let { _receivedMovie ->
                with(acb) {
                    with(_receivedMovie) {
                        nameEt.setText(name)
                        releaseYearEt.setText(releaseYear)
                        producerEt.setText(producer)
                        durationEt.setText(duration.toString())
                        flagEt.setText(flag.toString())
                        gradeEt.setText(grade)
//                        genreSp
                    }
                }
            }
            val viewMovie = intent.getBooleanExtra(VIEW_MOVIE, false)
            if (viewMovie) {
                acb.nameEt.isEnabled = false
                acb.releaseYearEt.isEnabled = false
                acb.producerEt.isEnabled = false
                acb.durationEt.isEnabled = false
                acb.flagEt.isEnabled = false
                acb.saveBt.visibility = View.GONE
            }

            acb.saveBt.setOnClickListener {
                val movie = Movie(
                    id = 1,
                    name = acb.nameEt.text.toString(),
                    releaseYear = 2010,
                    producer = acb.producerEt.text.toString(),
                    duration = Time(1200),
                    flag = acb.flagEt.isEnabled,
                    grade = 2,
                    gender = "Female"
                )
                val resultIntent = Intent()
                resultIntent.putExtra(EXTRA_MOVIE, movie)
                setResult(RESULT_OK, resultIntent)
                finish()
            }
        }
    }
}