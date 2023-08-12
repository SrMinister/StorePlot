package net.hyren.stores.data.store;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
public class Stores {

    private int stars;
    private int visits;
    private String location;

    public void addStars(int stars){
        this.stars += stars;
    }

    public void addVisits(int visits){
        this.visits += visits;
    }
}
