package io.playqd.commons.data;

public interface MusicDirectoryActionVisitor {

  void visit(MusicDirectoryAction action);

  void visit(ScanAction action);
}
