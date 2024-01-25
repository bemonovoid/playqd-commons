package io.playqd.commons.data;

@FunctionalInterface
public interface VisitableMusicDirectoryAction {

  void accept(MusicDirectoryActionVisitor visitor);

}
