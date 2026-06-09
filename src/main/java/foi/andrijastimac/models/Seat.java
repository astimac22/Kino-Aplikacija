package foi.andrijastimac.models;

public class Seat {
    private String number;
    private boolean reserved;

    public Seat(String number, boolean reserved) {
        this.number = number;
        this.reserved = reserved;
    }

    public String getNumber() {
        return number;
    }

    public boolean isReserved() {
        return reserved;
    }

    public void setReserved(boolean reserved) {
        this.reserved = reserved;
    }
}
