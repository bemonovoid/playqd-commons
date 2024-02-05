package io.playqd.commons.data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.nio.file.Path;

public record Playlist(String id,
                       String title,
                       String fileName,
                       PlaylistFormat format,
                       @JsonProperty("origin")
                       PlaylistProvider provider,
                       @JsonIgnore
                       Path location,
                       long itemsCount) {
}
