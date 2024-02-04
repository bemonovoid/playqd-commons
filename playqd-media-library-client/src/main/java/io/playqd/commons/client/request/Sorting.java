package io.playqd.commons.client.request;

import org.springframework.data.domain.Sort;

public final class Sorting {

  public static final class Tracks {

    public static final Sort BY_PLAYBACK_COUNT_DESC = Sort.by("playbackCount").descending();

    public static final Sort BY_FILE_LAST_PLAYED_DATE_DESC =
        Sort.by("fileLastPlaybackDate").descending();

    public static final Sort BY_FILE_ADDED_TO_WATCH_FOLDER_DATE_DESC =
        Sort.by("fileAddedToWatchFolderDate").descending();
  }

  private Sorting() {

  }

}
