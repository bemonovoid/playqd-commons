package io.playqd.commons.data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Map;

public record DirectoryItem(long sourceDirId,
                            String name,
                            String path,
                            String mimeType,
                            Long fileSize,
                            ItemType itemType,
                            @JsonInclude(JsonInclude.Include.NON_EMPTY)
                            Map<ItemType, Long> itemTypeCount) {

  @JsonIgnore
  public boolean hasChildren() {
    return itemTypeCount != null && !itemTypeCount.isEmpty();
  }

  @JsonIgnore
  public long totalChildItemsCount() {
    if (hasChildren()) {
      return itemTypeCount.values().stream().mapToLong(Long::longValue).sum();
    } else {
      return 0;
    }
  }

  @JsonIgnore
  public long childFoldersCount() {
    if (hasChildren()) {
      return itemTypeCount.getOrDefault(ItemType.folder, 0L);
    } else {
      return 0;
    }
  }
}