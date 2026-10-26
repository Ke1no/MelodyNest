package com.keino.minispotify
import com.keino.minispotify.data.*
import org.junit.Assert.*
import org.junit.Test
class CatalogTest {
    @Test fun demoHasUniqueTracksAndValidAlbumReferences() {
        assertTrue(validCatalog(DemoCatalog.tracks))
        assertTrue(DemoCatalog.tracks.all { t -> DemoCatalog.albums.any { it.id == t.albumId } })
    }
    @Test fun duplicateIdsAreRejected() { assertFalse(validCatalog(listOf(DemoCatalog.tracks.first(), DemoCatalog.tracks.first()))) }
    @Test fun favoriteRoundTripPreservesMetadata() { val track = DemoCatalog.tracks.first(); assertEquals(track, Favorite.from(track).track()) }
}
