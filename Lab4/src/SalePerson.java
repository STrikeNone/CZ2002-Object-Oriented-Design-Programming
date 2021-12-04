public class SalePerson implements Comparable<SalePerson>{
    private String firstName;
    private String lastName;
    private Integer totalSales;

    public SalePerson(String fN, String lN, int tS){
        this.firstName = fN;
        this.lastName = lN;
        this.totalSales = tS;
    }

    public String toString(){
        return this.lastName + ", " + this.firstName + ": " + this.totalSales;
    }

    public boolean equals(Object object){
        if(object instanceof SalePerson){
            return this.firstName.equals(((SalePerson) object).firstName) && this.lastName.equals(((SalePerson) object).lastName);
        }
        return false;
    }

    @Override
    public int compareTo(SalePerson o){
        int result = this.totalSales.compareTo(((SalePerson) o).totalSales);
        if(result == 0){
            return o.lastName.compareTo(this.lastName);
        }
        return result;
    }

    public String getFirstName(){
        return this.firstName;
    }

    public String getLastName(){
        return this.lastName;
    }

    public int getTotalSales(){
        return this.totalSales;
    }
}
