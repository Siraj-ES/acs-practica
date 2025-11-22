package baseNoStates;

public interface Visitor {
  void visitPartition(Partition partition);

  void visitSpace(Space space);
}
