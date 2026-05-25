package oikos.domain.interfaces;

public interface Persistivel {

    String toJson();

    void fromJson(String json);
}
