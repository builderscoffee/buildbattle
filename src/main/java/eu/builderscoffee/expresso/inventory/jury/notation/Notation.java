package eu.builderscoffee.expresso.inventory.jury.notation;

public class Notation {
    private String UUIDP;
    private int baute;
    private int crea;
    private int amenagement;
    private int folklore;
    private int fun;

    public Notation(String UUIDP) {
        this.UUIDP = UUIDP;
    }

    public void setUUIDP(String UUIDP) {
        this.UUIDP = UUIDP;
    }

    public String getUUIDP() {
        return UUIDP;
    }

    public int getBaute() {
        return baute;
    }

    public int getCrea() {
        return crea;
    }

    public int getAmenagement() {
        return amenagement;
    }

    public int getFolklore() {
        return folklore;
    }

    public int getFun() {
        return fun;
    }

    public boolean addCrea(int crea) {
        if ((this.crea = this.crea + crea) > 22){
            return false;
        } else {
            this.crea = this.crea + crea;
            return true;
        }
    }

    public boolean addBaute(int baute) {
        if ((this.baute = this.baute + baute) > 30){
            return false;
        } else {
            this.baute = this.baute + baute;
            return true;
        }
    }

    public boolean addAmenagement(int amenagement) {
        if ((this.amenagement = this.amenagement + amenagement) > 22){
            return false;
        } else {
            this.amenagement = this.amenagement + amenagement;
            return true;
        }
    }

    public boolean addFolklore(int folklore) {
        if ((this.folklore = this.folklore + folklore) > 22){
            return false;
        } else {
            this.folklore = this.folklore + folklore;
            return true;
        }
    }
    public boolean addFun(int fun) {
        if ((this.fun = this.fun + fun) > 4){
            return false;
        } else {
            this.fun = this.fun + fun;
            return true;
        }
    }


}
