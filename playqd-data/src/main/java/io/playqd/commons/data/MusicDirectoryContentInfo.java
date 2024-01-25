package io.playqd.commons.data;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Map;

public record MusicDirectoryContentInfo(@JsonIgnore MusicDirectory musicDirectory, long totalCount,
                                        Map<String, Long> formats) {
}
