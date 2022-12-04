package br.edu.ifsp.scl.ads.pdm.moviesmanager.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import br.edu.ifsp.scl.ads.pdm.moviesmanager.databinding.ActivityMovieBinding
import br.edu.ifsp.scl.ads.pdm.moviesmanager.model.Constant.EXTRA_MOVIE
import br.edu.ifsp.scl.ads.pdm.moviesmanager.model.Constant.VIEW_MOVIE
import br.edu.ifsp.scl.ads.pdm.moviesmanager.model.entity.Movie
import java.sql.Time
import java.text.SimpleDateFormat
import java.util.*
import kotlin.random.Random

class MovieActivity : AppCompatActivity() {
    private val amb: ActivityMovieBinding by lazy {
        ActivityMovieBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(amb.root)

        if(amb.flagCk.isChecked) amb.gradeEt.visibility = View.VISIBLE
        amb.flagCk.setOnClickListener() {
            if (amb.flagCk.isChecked) {
                amb.gradeEt.visibility = View.VISIBLE
            }else{
                amb.gradeEt.visibility = View.GONE
            }
        }

        val viewMovie = intent.getBooleanExtra(VIEW_MOVIE, false)
        val receivedMovie = intent.getParcelableExtra<Movie>(EXTRA_MOVIE)
        receivedMovie?.let { _receivedMovie ->
            with(amb) {
                with(_receivedMovie) {

                    amb.nameEt.isEnabled = false //Is never Edit
                    nameEt.setText(name)
                    releaseYearEt.setText(releaseYear.toString())
                    producerEt.setText(producer)
                    durationEt.setText(duration.toString())
                    if (flag) {
                        amb.flagCk.isChecked = true
                        amb.gradeEt.visibility = View.VISIBLE
                    }

                    gradeEt.setText(grade.toString())

                    if (viewMovie) {
                        amb.releaseYearEt.isEnabled = false
                        amb.producerEt.isEnabled = false
                        amb.durationEt.isEnabled = false
                        amb.gradeEt.isEnabled = false
                        amb.gradeEt.visibility = View.VISIBLE
                        amb.flagCk.isEnabled = false
                        amb.genreSp.isEnabled = false
                        amb.genreEt.isEnabled = false
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
                 if(
                    amb.flagCk.isChecked &&
                    (amb.gradeEt.text.toString() == ""
                    || amb.gradeEt.text.toString().toInt() > 10)){
                    Toast.makeText(this, "Grade is invalid !!!", Toast.LENGTH_LONG).show()
                }
                 else if (amb.nameEt.text.toString() == ""){
                    Toast.makeText(this, "Name is null !!!", Toast.LENGTH_LONG).show()
                }
                 else if (amb.releaseYearEt.text.toString().toInt() < 1000) {
                    Toast.makeText(this, "Release year must be greater than 1000 !!!", Toast.LENGTH_LONG).show()
                }
                 else {
                     val selectedGrade: Int = if(!amb.flagCk.isChecked ){
                          0
                      } else {
                          amb.gradeEt.text.toString().toInt()
                      }

                     val movie = Movie(
                        id = receivedMovie?.id ?: Random(System.currentTimeMillis()).nextInt(),
                        name = amb.nameEt.text.toString(),
                        releaseYear = amb.releaseYearEt.text.toString().toInt(),
                        producer = amb.producerEt.text.toString(),
                        duration = amb.durationEt.text.toString().toInt(),
                        flag = amb.flagCk.isChecked,
                        grade = selectedGrade,
                        genre = selectedGenre
                    )

                    val resultIntent = Intent()
                    resultIntent.putExtra(EXTRA_MOVIE, movie)
                    setResult(RESULT_OK, resultIntent)
                    finish()
                }
            }
        }
    }
}