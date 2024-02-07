package io.playqd.commons.data;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Map;

public record DirectoryItem(long sourceDirId,
                            String name,
                            String path,
                            ItemType itemType,
                            @JsonInclude(JsonInclude.Include.NON_EMPTY)
                            Map<ItemType, Long> itemTypeCount) {
}
