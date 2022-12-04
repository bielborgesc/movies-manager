package br.edu.ifsp.scl.ads.pdm.moviesmanager.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import androidx.appcompat.app.AppCompatActivity
import br.edu.ifsp.scl.ads.pdm.moviesmanager.databinding.ActivityMovieBinding
import br.edu.ifsp.scl.ads.pdm.moviesmanager.model.Constant.EXTRA_MOVIE
import br.edu.ifsp.scl.ads.pdm.moviesmanager.model.Constant.VIEW_MOVIE
import br.edu.ifsp.scl.ads.pdm.moviesmanager.model.entity.Movie
import java.sql.Time
import kotlin.random.Random

class MovieActivity : AppCompatActivity() {
    private val amb: ActivityMovieBinding by lazy {
        ActivityMovieBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(amb.root)

        val receivedMovie = intent.getParcelableExtra<Movie>(EXTRA_MOVIE)
        receivedMovie?.let { _receivedMovie ->
            with(amb) {
                with(_receivedMovie) {

                    amb.nameEt.isEnabled = false //Is never Edit

                    nameEt.setText(name)
                    releaseYearEt.setText(releaseYear)
                    producerEt.setText(producer)
                    durationEt.setText(duration.toString())
                    if (flag) {
                        amb.flagCk.isChecked = true
                    }
                    gradeEt.setText(grade)

                    val viewMovie = intent.getBooleanExtra(VIEW_MOVIE, false)

                    if (viewMovie) {
                        amb.releaseYearEt.isEnabled = false
                        amb.producerEt.isEnabled = false
                        amb.durationEt.isEnabled = false
                        amb.gradeEt.isEnabled = false
                        amb.flagCk.isEnabled = false
                        amb.genreSp.isEnabled = false
                        amb.saveBt.visibility = View.GONE
                        amb.genreSp.visibility = View.GONE
                        amb.genreEt.visibility = View.VISIBLE
                        amb.genreEt.setText(genre)
                    }
                }
            }
        }

        var selectedGenre: String = ""

        amb.genreSp.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                selectedGenre = parent?.getItemAtPosition(position).toString()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {}
        }



        amb.saveBt.setOnClickListener {
            amb.saveBt.setOnClickListener {
                var grade: Int?
                if (amb.gradeEt.text.toString() != "") {
                    grade = amb.gradeEt.text.toString().toInt()
                } else {
                    grade = null
                }

                val movie = grade?.let { it1 ->
                    Movie(
                        id = receivedMovie?.id ?: Random(System.currentTimeMillis()).nextInt(),
                        name = amb.nameEt.text.toString(),
                        releaseYear = amb.releaseYearEt.text.toString().toInt(),
                        producer = amb.producerEt.text.toString(),
                        duration = Time(amb.durationEt.text.toString().toLong()),
                        flag = amb.flagCk.isChecked,
                        grade = it1,
                        genre = selectedGenre
                    )
                }
                val resultIntent = Intent()
                resultIntent.putExtra(EXTRA_MOVIE, movie)
                setResult(RESULT_OK, resultIntent)
                finish()
            }
        }
    }
}