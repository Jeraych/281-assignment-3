package nz.ac.auckland.se281;

public class ParentMap {
  private Country parent;
  private Country child;

  public ParentMap(Country parent, Country child) {
    this.parent = parent;
    this.child = child;
  }

  public Country getParent() {
    return parent;
  }

  public Country getChild() {
    return child;
  }

  
}
