package eu.builderscoffee.expresso.buildbattle.notation;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class Notation {
    private UUID UUID;
    private int baute, crea, amenagement, folklore, fun;


    public boolean addCrea(int crea) {
        if ((this.crea = this.crea + crea) > 22) {
            return false;
        } else {
            this.crea = this.crea + crea;
            return true;
        }
    }

    public boolean addBaute(int baute) {
        if ((this.baute = this.baute + baute) > 30) {
            return false;
        } else {
            this.baute = this.baute + baute;
            return true;
        }
    }

    public boolean addAmenagement(int amenagement) {
        if ((this.amenagement = this.amenagement + amenagement) > 22) {
            return false;
        } else {
            this.amenagement = this.amenagement + amenagement;
            return true;
        }
    }

    public boolean addFolklore(int folklore) {
        if ((this.folklore = this.folklore + folklore) > 22) {
            return false;
        } else {
            this.folklore = this.folklore + folklore;
            return true;
        }
    }

    public boolean addFun(int fun) {
        if ((this.fun = this.fun + fun) > 4) {
            return false;
        } else {
            this.fun = this.fun + fun;
            return true;
        }
    }

}
