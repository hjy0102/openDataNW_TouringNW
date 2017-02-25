//Name,StructureId,StructureType,InstallYear,TCACode,BrandModel,SupplierName,ContractorName,DesignerName,Cost,Comments,ParkName,DedicationContact,DedicationSuite,DedicationCivicNumber,DedicationStreetName,DedicationCity,DedicationProvince,DedicationPostalCode,DedicationPhone,DedicationInscription,DedicationDate,DedicationComments,OBJECTID,X,Y
public class DrinkingFountain{
  int structureId, objectID;
  String comments, parkName;
  LatLng latlng;

  public DrinkingFountain(int structureId, int objectID, String comments, String parkName, LatLng latlng){
      this.structureId = structureId;
      this.objectID = objectID;
      this.comments = comments;
      this.parkName = parkName;
      this.latlng = latlng;
  }

  public int getStructureId() {
        return structureId;
  }

  public int getObjectID() {
        return objectID;
  }

  public String getComments() {
        return comments;
  }

  public String getParkName() {
        return parkName;
  }

  public LatLng getLatlng() {
        return latlng;
  }
}
