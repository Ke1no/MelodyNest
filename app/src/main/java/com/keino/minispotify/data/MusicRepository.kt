package com.keino.minispotify.data
import javax.inject.Inject
import javax.inject.Singleton
@Singleton class MusicRepository @Inject constructor(private val api: MusicApi, private val dao: FavoriteDao) {
    val favorites = dao.observe()
    suspend fun remoteCatalog(): Pair<List<Track>, List<Album>> {
        val tracks = api.tracks(); val albums = api.albums()
        require(validCatalog(tracks)) { "Invalid track catalog" }
        require(albums.map { it.id }.distinct().size == albums.size && tracks.all { t -> albums.any { it.id == t.albumId } }) { "Invalid album catalog" }
        return tracks to albums
    }
    suspend fun setFavorite(track: Track, favorite: Boolean) { if (favorite) dao.save(Favorite.from(track)) else dao.delete(track.id) }
}
