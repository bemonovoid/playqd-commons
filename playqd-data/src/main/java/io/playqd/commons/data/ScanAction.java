package io.playqd.commons.data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName(ScanAction.TYPE_NAME)
public class ScanAction extends MusicDirectoryAction {

  static final String TYPE_NAME = "scan";

  boolean deleteAllBeforeScan;

  @Override
  @JsonIgnore
  public void accept(MusicDirectoryActionVisitor visitor) {
    visitor.visit(this);
  }
}
