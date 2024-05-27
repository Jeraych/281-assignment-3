package nz.ac.auckland.se281;

public class InvalidCountry extends Exception {

  public InvalidCountry(String name) {
    super(MessageCli.INVALID_COUNTRY.getMessage(name));
  }
}
