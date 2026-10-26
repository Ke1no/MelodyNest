package com.keino.minispotify.data
import androidx.room.*
import kotlinx.coroutines.flow.Flow
@Entity(tableName = "favorites")
data class Favorite(@PrimaryKey val id: String, val albumId: String, val title: String, val artist: String, val durationSeconds: Int, val audioUrl: String?) {
    fun track() = Track(id, albumId, title, artist, durationSeconds, audioUrl)
    companion object { fun from(track: Track) = Favorite(track.id, track.albumId, track.title, track.artist, track.durationSeconds, track.audioUrl) }
}
@Dao interface FavoriteDao {
    @Query("SELECT * FROM favorites ORDER BY title") fun observe(): Flow<List<Favorite>>
    @Insert(onConflict = OnConflictStrategy.REPLACE) suspend fun save(favorite: Favorite)
    @Query("DELETE FROM favorites WHERE id = :id") suspend fun delete(id: String)
}
@Database(entities = [Favorite::class], version = 1, exportSchema = true)
abstract class MusicDatabase : RoomDatabase() { abstract fun favorites(): FavoriteDao }
