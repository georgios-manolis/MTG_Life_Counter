public class Player {
    private int lifepoints = 20;
    private int x = 1;
    private String name = "Player " + x;
    public void increase() {
        lifepoints++;
    }
    public void decrease() {
        lifepoints--;
    }
    public int getALife() {
        return lifepoints;
    }
    public String getName(){
       return name;
    }
    public void setName(String newName){
        name = newName;
    }
}
