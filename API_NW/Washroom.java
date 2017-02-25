public class Washroom{
  String name, category, address, neighbourhood, hours, source, accessible;
  LatLng latlng;

  public Washroom(String name, String category, String address, String, neighbourhood,
    String hours, String source, String accessible, LatLng latlng){
      this.name = name;
      this.category = category;
      this.address = address;
      this.neighbourhood = neighbourhood;
      this.hours = hours;
      this.source = source;
      this.accessible = accessible;
      this.latlng = latlng;
  }



  public String getName() {
        return name;
  }

  public String getCategory() {
        return category;
  }

  public String getAddress() {
        return address;
  }

  public String getNeighbourhood() {
        return neighbourhood;
  }

  public String getHours() {
        return hours;
  }

  public String getSource() {
        return source;
  }

  public String getaccessible() {
        return accessible;
  }

  public LatLng getLatlng() {
        return latlng;
  }
}
