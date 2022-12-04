package br.edu.ifsp.scl.ads.pdm.moviesmanager.model.entity

import android.os.Parcelable
import androidx.annotation.NonNull
import kotlinx.parcelize.Parcelize
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull
import java.sql.Time


@Parcelize
@Entity(indices = [Index(value = ["first_name"], unique = true)])
data class Movie (
    @PrimaryKey(autoGenerate = false)
    var id: Int,

    @NotNull
    var name: String,

    @NonNull
    var releaseYear: Int,

    @NonNull
    var producer: String,

    @NonNull
    var duration: Time,

    @NonNull
    var flag: Boolean,

    @NonNull
    var grade: Int,

    @NonNull
    var genre: String

): Parcelable