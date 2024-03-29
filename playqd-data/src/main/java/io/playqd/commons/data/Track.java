package io.playqd.commons.data;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record Track(long id,
                    String uuid,
                    String title,
                    String number,
                    Artist artist,
                    Album album,
                    Length length,
                    Playback playback,
                    AudioFormat audioFormat,
                    FileAttributes fileAttributes,
                    AdditionalInfo additionalInfo) {

  public record Artist(String id, String name) {

  }

  public record Album(String id, String name, String genreId, String genre) {

  }

  public record Length(int inSeconds, double precise, String inTimeFormat) {

  }

  public record Playback(int count, @JsonFormat(pattern = "HH:mm MMM-dd-yyyy") LocalDateTime lastPlayedDate) {

  }

  public record FileAttributes(String name, String location, String extension, String size) {

  }

  public record AudioFormat(String mimeType,
                            String bitRate,
                            String sampleRate,
                            int bitsPerSample) {
  }

  public record AdditionalInfo(@JsonFormat(pattern = "dd-MM-yyyy") LocalDate addedToWatchFolderDate) {

  }
}