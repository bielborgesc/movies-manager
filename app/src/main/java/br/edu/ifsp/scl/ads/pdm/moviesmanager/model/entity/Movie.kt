package br.edu.ifsp.scl.ads.pdm.moviesmanager.model.entity

import android.os.Parcelable
import androidx.annotation.NonNull
import kotlinx.parcelize.Parcelize
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.sql.Time


@Parcelize
@Entity
data class Movie (
    @PrimaryKey(autoGenerate = false)
    var name: String,

    @NonNull
    var releaseYear: Int,

    @NonNull
    var producer: String,

    @NonNull
    var duration: Time,

    @NonNull
    var flag: Boolean,

    var grade: Int,

    var gender: String




): Parcelable