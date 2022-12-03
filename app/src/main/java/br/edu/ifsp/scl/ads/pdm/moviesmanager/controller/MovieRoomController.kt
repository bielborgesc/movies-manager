package br.edu.ifsp.scl.ads.pdm.moviesmanager.controller

import br.edu.ifsp.scl.ads.pdm.moviesmanager.model.dao.MovieRoomDao
import br.edu.ifsp.scl.ads.pdm.moviesmanager.view.MainActivity
import androidx.room.Room
import br.edu.ifsp.scl.ads.pdm.moviesmanager.model.dao.MovieRoomDao.Constant.MOVIE_DATABASE_FILE
import br.edu.ifsp.scl.ads.pdm.moviesmanager.model.database.MovieRoomDaoDatabase


class MovieRoomController(private val mainActivity: MainActivity) {
    private val movieDaoImpl: MovieRoomDao by lazy {
        Room.databaseBuilder(
            mainActivity,
            MovieRoomDaoDatabase::class.java,
            MOVIE_DATABASE_FILE
        ).build().getMovieRoomDao()
    }
}
