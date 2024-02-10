package io.playqd.commons.client;

import io.playqd.commons.data.Album;
import io.playqd.commons.data.Artist;
import io.playqd.commons.data.DirectoryItem;
import io.playqd.commons.data.Genre;
import io.playqd.commons.data.MediaItemsCount;
import io.playqd.commons.data.MusicDirectory;
import io.playqd.commons.data.MusicDirectoryAction;
import io.playqd.commons.data.MusicDirectoryContentInfo;
import io.playqd.commons.data.Playlist;
import io.playqd.commons.data.Track;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

@FeignClient(path = "/api/v1", name = MediaLibraryClient.CLIENT_NAME)
public interface MediaLibraryClient {

  String CLIENT_NAME = "playqd-media-library";

  String API_PATH_AUDIO_STREAM = "/api/v1/resources/audio";

  String API_PATH_IMAGE_STREAM = "/api/v1/resources/image";

  @GetMapping("/metadata/counts/artists")
  MediaItemsCount artistsCount();

  @GetMapping("/metadata/counts/albums")
  MediaItemsCount albumsCount();

  @GetMapping("/metadata/counts/genres")
  MediaItemsCount genresCount();

  @GetMapping("/metadata/counts/tracks/played")
  MediaItemsCount tracksPlayedCount();

  @GetMapping("/metadata/counts/tracks/liked")
  MediaItemsCount tracksLikedCount();

  @GetMapping("/metadata/counts/tracks/lastRecentlyAdded")
  MediaItemsCount tracksLastRecentlyAddedCount();

  @GetMapping(value = "/metadata/artists")
  Page<Artist> artists(@PageableDefault(size = 100, sort = "name") Pageable page);

  @GetMapping(value = "/metadata/artists")
  Page<Artist> artistsByGenre(@PageableDefault(size = 100, sort = "name") Pageable page,
                              @RequestParam(name = "genreId") String genreId);

  @GetMapping("/metadata/albums")
  Page<Album> albums(@PageableDefault(size = 100, sort = "name") Pageable page);

  @GetMapping("/metadata/albums")
  Page<Album> albumsByGenreId(@PageableDefault(size = 100, sort = "name") Pageable page,
                              @RequestParam(name = "genreId") String genreId);

  @GetMapping("/metadata/albums")
  Page<Album> albumsByArtistId(@PageableDefault(size = 100, sort = "name") Pageable page,
                               @RequestParam(name = "artistId") String artistId);

  @GetMapping("/metadata/genres")
  Page<Genre> getGenres(@PageableDefault(size = 100, sort = "name") Pageable page);

  @GetMapping("/metadata/tracks")
  Page<Track> tracks(@PageableDefault(size = 100, sort = "name") Pageable page,
                     @RequestParam(name = "artistId", required = false) String artistId,
                     @RequestParam(name = "albumId", required = false) String albumId,
                     @RequestParam(name = "genreId", required = false) String genreId,
                     @RequestParam(name = "playlistId", required = false) String playlistId,
                     @RequestParam(name = "sourceDirId", required = false, defaultValue = "0") long sourceDirId,
                     @RequestParam(name = "title", required = false) String title,
                     @RequestParam(name = "played", required = false) Boolean played,
                     @RequestParam(name = "lastRecentlyAdded", required = false) boolean lastRecentlyAdded,
                     @RequestParam(name = "recentlyAddedSinceDuration", required = false) String recentlyAddedSinceDuration,
                     @RequestParam(name = "location", required = false, defaultValue = "") String location);

  default Page<Track> tracksByArtistId(Pageable page, String artistId) {
    return tracks(page, artistId, "", "", "", 0, "", null, false, null, null);
  }

  default Page<Track> tracksByAlbumId(Pageable page, String albumId) {
    return tracks(page, "", albumId, "", "", 0, "", null, false, null, null);
  }

  default Page<Track> tracksByGenreId(Pageable page, String genreId) {
    return tracks(page, "", "", genreId, "", 0, "", null, false, null, null);
  }

  default Page<Track> tracksByPlaylistId(Pageable page, String playlistId) {
    return tracks(page, "", "", "", playlistId, 0, "", null, false, null, null);
  }

  default Page<Track> tracksLastRecentlyAdded(Pageable page) {
    return tracks(page, "", "", "", "", 0, "", null, true, null, null);
  }

  default Page<Track> tracksRecentlyPlayed(Pageable page) {
    return tracks(page, "", "", "", "", 0, "", true, false, null, null);
  }

  default Page<Track> tracksByLocation(Pageable page, long sourceDirId, String location) {
    return tracks(page, "", "", "", "", sourceDirId, "", null, false, null, location);
  }

  @GetMapping("/playlists")
  List<Playlist> getAllPlaylists();

  @GetMapping("/directories")
  List<MusicDirectory> musicDirectories();

  @GetMapping("/directories/{id}")
  MusicDirectory get(@PathVariable(name = "id") long id);

  @GetMapping("/directories/{id}/info")
  MusicDirectoryContentInfo directoryInfo(@PathVariable(name = "id") long id);

  @PostMapping("/directories/actions")
  @ResponseStatus(HttpStatus.ACCEPTED)
  void submitMusicDirectoryAction(@RequestBody MusicDirectoryAction action);

  @GetMapping("/directories/tree")
  Page<DirectoryItem> tree(@PageableDefault(size = 1000) Pageable page);

  @GetMapping("/directories/tree/{id}")
  Page<DirectoryItem> tree(@PathVariable("id") long id,
                           @RequestParam(name = "path", required = false) String pathBase64Encoded,
                           @PageableDefault(size = 1000) Pageable page);

  default Page<DirectoryItem> musicDirectoryTree(long id) {
    return tree(id, "", Pageable.unpaged());
  }

  default Page<DirectoryItem> musicDirectoryTree(long id, String pathBase64Encoded) {
    return tree(id, pathBase64Encoded, Pageable.unpaged());
  }
}
