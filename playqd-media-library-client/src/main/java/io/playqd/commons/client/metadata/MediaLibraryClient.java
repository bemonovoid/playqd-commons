package io.playqd.commons.client.metadata;

import io.playqd.commons.data.Album;
import io.playqd.commons.data.Artist;
import io.playqd.commons.data.Genre;
import io.playqd.commons.data.MusicDirectory;
import io.playqd.commons.data.MusicDirectoryAction;
import io.playqd.commons.data.MusicDirectoryContentInfo;
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
import java.util.Set;

@FeignClient(path = "/api/v1", name = "playqd-media-library")
public interface MediaLibraryClient {

    @GetMapping(value = "/metadata/artists")
    Page<Artist> artists(@PageableDefault(size = 100, sort = "name") Pageable page);

    @GetMapping("/metadata/albums")
    Page<Album> albums(@PageableDefault(size = 100, sort = "name") Pageable page,
                       @RequestParam(name = "artistId", required = false, defaultValue = "") String artistId,
                       @RequestParam(name = "genreId", required = false, defaultValue = "") String genreId);

    @GetMapping("/metadata/genres")
    Page<Genre> getGenres(@PageableDefault(size = 100, sort = "name") Pageable page);

    @GetMapping("/metadata/tracks")
    Page<Track> tracks(@PageableDefault(size = 100, sort = "name") Pageable page,
                       @RequestParam(name = "filter", required = false, defaultValue = "") String filter,
                       @RequestParam(name = "artistId", required = false, defaultValue = "") String artistId,
                       @RequestParam(name = "albumId", required = false, defaultValue = "") String albumId,
                       @RequestParam(name = "locations", required = false) Set<String> locations);

    @GetMapping("/directories/{id}")
    MusicDirectory get(@PathVariable(name = "id") long id);

    @GetMapping
    List<MusicDirectory> getAll();

    @GetMapping("/directories/{id}/info")
    MusicDirectoryContentInfo info(@PathVariable(name = "id") long id);

    @PostMapping("/directories/actions")
    @ResponseStatus(HttpStatus.ACCEPTED)
    void scan(@RequestBody MusicDirectoryAction action);

}
