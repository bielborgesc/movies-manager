package br.edu.ifsp.scl.ads.pdm.moviesmanager.model.database

import androidx.room.Database
import androidx.room.RoomDatabase
import br.edu.ifsp.scl.ads.pdm.moviesmanager.model.dao.MovieRoomDao
import br.edu.ifsp.scl.ads.pdm.moviesmanager.model.entity.Movie

@Database(entities = [Movie::class], version = 1)
abstract class MovieRoomDaoDatabase: RoomDatabase()  {
    abstract fun getMovieRoomDao(): MovieRoomDao
}